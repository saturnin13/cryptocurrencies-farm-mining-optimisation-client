package com.company.Miners;

import com.company.CommandExecutor.CommandExecutor;
import com.company.MachineInformation.Configuration.OSType;
import com.google.common.collect.ImmutableList;
import org.apache.log4j.Logger;

import java.util.List;

import static com.company.CommandExecutor.CommandExecutionEnvironment.POWERSHELL;
import static com.company.MachineInformation.Configuration.OSType.linux;
import static com.company.MachineInformation.Configuration.OSType.mac;
import static com.company.MachineInformation.Configuration.OSType.windows;
import static com.company.MachineInformation.MachineConfigurationRetriever.getMachineCharacteristics;
import static com.company.Variables.LOCATION_MAIN_FOLDER;

public abstract class Miner {

    private final static Logger logger = Logger.getLogger(Miner.class);
    protected MinedCurrencyShortName minedCurrencyShortName;

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

    protected abstract void startMiningWindows();

    protected abstract void startMiningLinux();

    protected abstract void startMiningMac();

    public boolean isInstalled() {
        List<String> commands = new ImmutableList.Builder<String>()
                .add("cd " + LOCATION_MAIN_FOLDER + "/" + minedCurrencyShortName + "/bin")
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
        return true;
    }

    protected abstract void installWindows();

    protected abstract void installLinux();

    protected abstract void installMac();
}
