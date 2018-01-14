package com.company.CommandExecutor;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class ShellCommandExecutor {

    private static String shellUsed;

    final static Logger logger = Logger.getLogger(ShellCommandExecutor.class);

    public static String executeCommands(List<String> commands) {
        String commandsString = commands.stream().collect(Collectors.joining(" && "));
        logger.info("executing command " + commandsString);

        String line1, line2 = null;
        String shellUsed = getShellUsed();
        String[] finalCommand = {shellUsed, "-c", commandsString};
        StringBuffer output = new StringBuffer();
        System.out.println(finalCommand[0] + " " + finalCommand[1] + " " + finalCommand[2]);

        try {
            ProcessBuilder builder = new ProcessBuilder(shellUsed, "-c", commandsString);
            builder.redirectErrorStream(true);

            Process p = builder.start();

            BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                InputStreamReader(p.getErrorStream()));

            while ((line1 = stdInput.readLine()) != null) {
                output.append(line1).append("\n");
                logger.debug("stdInput of command currently executed : " + line1);
            }
            p.destroy();
        }
        catch (IOException e) {
            logger.error("Error executing a shell command");
            e.printStackTrace();
            System.exit(-1);
        }
        return output.toString();
    }

    public static String getShellUsed() {
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
