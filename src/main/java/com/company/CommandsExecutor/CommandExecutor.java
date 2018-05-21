package com.company.CommandsExecutor;

import com.company.CommandsExecutor.CommandOutputMonitoring.CommandOutputMonitor;
import com.company.Client.JsonFormat.ClientJson.MiningConfiguration.ClientConfiguration.OS.CommandExecutionEnvironment;
import com.company.Client.JsonFormat.ClientJson.MiningConfiguration.ClientConfiguration.OS.OS;
import com.company.MachineInformation.OSInformationRetriever;
import lombok.Builder;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import static com.company.Client.JsonFormat.ClientJson.MiningConfiguration.ClientConfiguration.OS.CommandExecutionEnvironment.BASH;
import static com.company.Client.JsonFormat.ClientJson.MiningConfiguration.ClientConfiguration.OS.CommandExecutionEnvironment.POWERSHELL;

@Builder
public class CommandExecutor extends Thread {

    private volatile boolean exit = false;

    private final static Logger logger = Logger.getLogger(CommandExecutor.class);
    private List<String> commands;
    private List<String> cleanUpCommands;
    private CommandOutputMonitor outputMonitor;
    private boolean verbose;
    @Builder.Default private boolean doReadlines = true;

    @Override
    public void run() {
        runAndReturn();
    }

    public String runAndReturn() {
        if(commands == null) {
            return null;
        }

        String delimiter;
        String shellUsed;
        String commandOption;
        // Exceptionally OS class is accessed directly instead of the MachineConfigurationRetriever class to avoid mutual dependencies and stack overflow
        OS os = OSInformationRetriever.retrieveOs();
        CommandExecutionEnvironment environment = os.getEnvironment();
        if(environment == POWERSHELL) {
            delimiter = ";";
            shellUsed = "C:/Windows/system32/WindowsPowerShell/v1.0/powershell.exe";
            commandOption = "-Command";
        } else if (environment == BASH){
            delimiter = "&&";
            shellUsed = getBashhellUsed();
            commandOption = "-c";
        } else {
            logger.error("Unknow command execution environment: " + environment);
            return null;
        }

        String commandsString = commands.stream().collect(Collectors.joining(" " + delimiter + " "));
        logger.trace("executing command " + commandsString + " in environment " + environment);

        String line;
        String previousLine = null;
        StringBuilder output = new StringBuilder();
        long startingTimeMillis = System.currentTimeMillis();

        try {
            ProcessBuilder builder = new ProcessBuilder(shellUsed, commandOption, commandsString);
            builder.redirectErrorStream(true);

            Process p = builder.start();

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));

            if(doReadlines) {
                while ((line = stdInput.readLine()) != null) {
                    output.append(line).append("\n");
                    if(verbose) {
                        logger.debug(line);
                    }
                    if(outputMonitor != null) {
                        outputMonitor.monitorOutput(line, previousLine);
                    }
                    if(exit) {
                        break;
                    }
                    previousLine = line;
                }
            } else {
                while(!exit) {
                    sleep(1000);
                }
            }

            p.getOutputStream().close();
            p.getErrorStream().close();
            stdInput.close();
            p.destroy();
        } catch (IOException e) {
            logger.error("IOException while executing a command");
            e.printStackTrace();
            System.exit(-1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(cleanUpCommands != null) {
            logger.info("Cleaning up with the following commands: " + cleanUpCommands);
            this.commands = cleanUpCommands;
            this.cleanUpCommands = null;
            this.verbose = false;
            this.doReadlines = true;
            run();
        }
        return output.toString();
    }

    public void exit() {
        exit = true;
    }

    private static String getBashhellUsed() {
        String command = "echo $SHELL";

        StringBuilder output = new StringBuilder();

        Process p;
        try {
            ProcessBuilder builder = new ProcessBuilder("echo", System.getenv("SHELL"));
            p = builder.start();

            p.waitFor();
            BufferedReader reader =
                new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //TODO: more command line shell
        if(output.toString().contains("/bin/bash")) {
            return "bash";
        } else {
            logger.error("could not find shell used, unknown");
            return "";
        }
    }
}
