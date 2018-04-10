package com.company.CommandExecutor;

import com.company.CommandExecutor.CommandOutputMonitoring.CommandOutputMonitor;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import static com.company.CommandExecutor.CommandExecutionEnvironment.POWERSHELL;
import static com.company.CommandExecutor.CommandExecutionEnvironment.BASH;

public class CommandExecutor {

    private final static Logger logger = Logger.getLogger(CommandExecutor.class);

    public static String executeCommands(List<String> commands, CommandExecutionEnvironment environment, boolean verbose) {
        return executeCommands(commands, null, environment, null, verbose, -1);
    }

    public static String executeCommands(List<String> commands,  List<String> cleanUpCommands, CommandExecutionEnvironment environment, boolean verbose) {
        return executeCommands(commands, cleanUpCommands, environment, null, verbose, -1);
    }


    public static String executeCommands(List<String> commands, List<String> cleanUpCommands, CommandExecutionEnvironment environment, CommandOutputMonitor outputMonitor, boolean verbose, long timeoutMillis) {
        if(commands == null) {
            return null;
        }

        String delimiter;
        String shellUsed;
        String commandOption;
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

            while ((line = stdInput.readLine()) != null) {
                output.append(line).append("\n");
                if(verbose) {
                    logger.debug(line);
                }
                if(outputMonitor != null) {
                    outputMonitor.monitorOutput(line, previousLine);
                }
                if(System.currentTimeMillis() > startingTimeMillis + timeoutMillis && timeoutMillis != -1) {
                    break;
                }
                previousLine = line;
            }

            p.getOutputStream().close();
            p.getErrorStream().close();
            stdInput.close();
            p.destroy();
        } catch (IOException e) {
            logger.error("IOException while executing a command");
            e.printStackTrace();
            System.exit(-1);
        }

        if(cleanUpCommands != null) {
            executeCommands(cleanUpCommands, environment, false);
        }

        return output.toString();
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
