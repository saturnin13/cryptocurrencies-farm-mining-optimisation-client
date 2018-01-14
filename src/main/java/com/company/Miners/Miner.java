package com.company.Miners;

import com.company.MachineInformation.Configuration.OS;
import com.company.MachineInformation.Configuration.OSType;
import com.company.Miners.MiningAlgorithm.GpuMining.EthashCurrencies.Ethereum;
import org.apache.log4j.Logger;

import static com.company.MachineInformation.Configuration.OSType.linux;
import static com.company.MachineInformation.Configuration.OSType.mac;
import static com.company.MachineInformation.Configuration.OSType.windows;
import static com.company.MachineInformation.MachineConfigurationRetriever.getMachineCharacteristics;

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
    }

    protected abstract void startMiningWindows();

    protected abstract void startMiningLinux();

    protected abstract void startMiningMac();

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
