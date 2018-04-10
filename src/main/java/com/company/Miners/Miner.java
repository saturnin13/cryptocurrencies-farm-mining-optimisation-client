package com.company.Miners;

import com.company.CommandExecutor.CommandExecutor;
import com.company.CommandExecutor.CommandOutputMonitoring.CommandOutputMonitor;
import com.company.MachineInformation.Configuration.OSType;
import com.company.Timeout.TimeoutManager;
import com.google.common.collect.ImmutableList;
import org.apache.log4j.Logger;

import java.util.List;

import static com.company.CommandExecutor.CommandExecutionEnvironment.BASH;
import static com.company.CommandExecutor.CommandExecutionEnvironment.POWERSHELL;
import static com.company.MachineInformation.Configuration.OSType.*;
import static com.company.MachineInformation.MachineConfigurationRetriever.getMachineCharacteristics;
import static com.company.Variables.LOCATION_MAIN_FOLDER;

public abstract class Miner {

    private final static Logger logger = Logger.getLogger(Miner.class);
    protected MinedCurrencyShortName minedCurrencyShortName;
    protected String poolAddress;

    public void startMining() {
        logger.info("Starting to mine " + minedCurrencyShortName + " miner");
        OSType currentOsType = getMachineCharacteristics().getOs().getOsType();
        if(currentOsType == mac) {
            startMiningMac();
        } else if(currentOsType == linux) {
            startMiningLinux();
        } else if(currentOsType == windows) {
            startMiningWindows();
        } else {
            logger.info("Could not determine the os type for starting to mine: " + minedCurrencyShortName);
        }
        logger.info("Finish mining " + minedCurrencyShortName);
    }

    protected void startMiningWindows() {
        CommandExecutor.executeCommands(getMiningCommandsWindows(), getMiningCleanUpCommandsWindows(), POWERSHELL, getOutputMonitoring(),
                true, TimeoutManager.timeout(minedCurrencyShortName));
    }

    protected abstract List<String> getMiningCommandsWindows();
    protected abstract List<String> getMiningCleanUpCommandsWindows();

    protected void startMiningLinux() {
        // TODO: check that bash works on linux
        CommandExecutor.executeCommands(getMiningCommandsLinux(), getMiningCleanUpCommandsLinux(), BASH, getOutputMonitoring(),
                true, TimeoutManager.timeout(minedCurrencyShortName));
    }

    protected abstract List<String> getMiningCommandsLinux();
    protected abstract List<String> getMiningCleanUpCommandsLinux();

    protected void startMiningMac() {
        CommandExecutor.executeCommands(getMiningCommandsMac(), getMiningCleanUpCommandsMac(), BASH, getOutputMonitoring(),
                true, TimeoutManager.timeout(minedCurrencyShortName));
    }

    protected abstract List<String> getMiningCommandsMac();
    protected abstract List<String> getMiningCleanUpCommandsMac();

    public boolean isInstalled() {
        return existLocation(LOCATION_MAIN_FOLDER + "/" + minedCurrencyShortName);
    }

    protected abstract CommandOutputMonitor getOutputMonitoring();

    protected boolean existLocation(String location) {
        List<String> commands = new ImmutableList.Builder<String>()
                .add("cd " + location)
                .build();
        String output = CommandExecutor.executeCommands(commands, POWERSHELL, false);
        return output.isEmpty();
    }

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
        logger.info("Installetion completed successfully for " + minedCurrencyShortName + " miner");
        return true;
    }

    protected void installWindows() {
        CommandExecutor.executeCommands(getInstallCommandsWindows(), POWERSHELL, true);
    }

    protected abstract List<String> getInstallCommandsWindows();

    protected void installLinux() {
        // TODO: check that bash works on linux
        CommandExecutor.executeCommands(getInstallCommandsLinux(), BASH, true);
    }

    protected abstract List<String> getInstallCommandsLinux();

    protected void installMac() {
        CommandExecutor.executeCommands(getInstallCommandsMac(), BASH, true);
    }

    protected abstract List<String> getInstallCommandsMac();
}
