package com.company.Miners;

import com.company.CommandsExecutor.CommandExecutor;
import com.company.CommandsExecutor.CommandOutputMonitoring.CommandOutputMonitor;
import com.company.MachineInformation.Configuration.OS.OSType;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.List;

import static com.company.CommandsExecutor.CommandExecutionEnvironment.BASH;
import static com.company.CommandsExecutor.CommandExecutionEnvironment.POWERSHELL;
import static com.company.MachineInformation.Configuration.OS.OSType.*;
import static com.company.MachineInformation.MachineConfigurationRetriever.getMachineCharacteristics;
import static com.company.Variables.LOCATION_MAIN_FOLDER;

public abstract class Miner {

    private final static Logger logger = Logger.getLogger(Miner.class);
    protected MinedCurrencyShortName minedCurrencyShortName;
    protected String poolAddress;
    private CommandExecutor miningThread;

    public void startMining() {
        logger.info("Starting to mine " + minedCurrencyShortName + " miner");
        OSType currentOsType = getMachineCharacteristics().getOs().getOsType();
        if(currentOsType == mac) {
            miningThread = getMiningThreadMac();
        } else if(currentOsType == linux) {
            miningThread = getMiningThreadLinux();
        } else if(currentOsType == windows) {
            miningThread = getMiningThreadWindows();
        } else {
            logger.info("Could not determine the os type for starting to mine: " + minedCurrencyShortName);
        }
        miningThread.start();
        logger.info("Mining thread launched for " + minedCurrencyShortName);
    }

    public void stopMining() {
        miningThread.exit();
    }

    private CommandExecutor getMiningThreadWindows() {
        return CommandExecutor.builder()
                .commands(getMiningCommandsWindows())
                .cleanUpCommands(getMiningCleanUpCommandsWindows())
                .outputMonitor(getOutputMonitoring())
                .verbose(true)
                .build();
    }

    protected abstract List<String> getMiningCommandsWindows();
    protected abstract List<String> getMiningCleanUpCommandsWindows();

    private CommandExecutor getMiningThreadLinux() {
        // TODO: check that bash works on linux
        return CommandExecutor.builder()
                .commands(getMiningCommandsLinux())
                .cleanUpCommands(getMiningCleanUpCommandsLinux())
                .outputMonitor(getOutputMonitoring())
                .verbose(true)
                .build();
    }

    protected abstract List<String> getMiningCommandsLinux();
    protected abstract List<String> getMiningCleanUpCommandsLinux();

    private CommandExecutor getMiningThreadMac() {
        return CommandExecutor.builder()
                .commands(getMiningCommandsMac())
                .cleanUpCommands(getMiningCleanUpCommandsMac())
                .outputMonitor(getOutputMonitoring())
                .verbose(true)
                .build();
    }

    protected abstract List<String> getMiningCommandsMac();
    protected abstract List<String> getMiningCleanUpCommandsMac();

    public boolean isInstalled() {
        return new File(LOCATION_MAIN_FOLDER + "/" + minedCurrencyShortName).exists();
    }

    protected abstract CommandOutputMonitor getOutputMonitoring();

    public boolean install() {
        logger.info("installing " + minedCurrencyShortName + " miner");
        OSType currentOsType = getMachineCharacteristics().getOs().getOsType();
        if(currentOsType == mac) {
            installMac();
        } else if(currentOsType == linux) {
            installLinux();
        } else if(currentOsType == windows) {
            installWindows();
        } else {
            logger.info("Could not determine the os type for installation: " + minedCurrencyShortName);
            return false;
        }
        logger.info("Installation completed successfully for " + minedCurrencyShortName + " miner");
        return true;
    }

    private void installWindows() {
        CommandExecutor commandExecutor = CommandExecutor.builder()
                .commands(getInstallCommandsWindows())
                .verbose(true)
                .build();
        commandExecutor.run();
    }

    protected abstract List<String> getInstallCommandsWindows();

    private void installLinux() {
        // TODO: check that bash works on linux
        CommandExecutor commandExecutor = CommandExecutor.builder()
                .commands(getInstallCommandsLinux())
                .verbose(true)
                .build();
        commandExecutor.run();
    }

    protected abstract List<String> getInstallCommandsLinux();

    private void installMac() {
        CommandExecutor commandExecutor = CommandExecutor.builder()
                .commands(getInstallCommandsMac())
                .verbose(true)
                .build();
        commandExecutor.run();
    }

    protected abstract List<String> getInstallCommandsMac();
}
