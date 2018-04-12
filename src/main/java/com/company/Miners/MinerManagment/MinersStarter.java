package com.company.Miners.MinerManagment;

import com.company.MachineInformation.MachineConfigurationRetriever;
import com.company.Miners.MinedCurrencyShortName;
import com.company.Miners.Miner;

import static com.company.Miners.Protocol.TERTIARY;

public class MinersStarter {
    private static Miner currentMiner = null;

    public static void startMiner(MinedCurrencyShortName currencyShortName) {
        if(currentMiner != null) {
            currentMiner.stopMining();
        }
        if(currencyShortName != null) {
            currentMiner = MinersFactory.getMiner(currencyShortName);
            currentMiner.startMining(MachineConfigurationRetriever.getMachineCharacteristics().getOs().getOsType(), TERTIARY);
        }
    }
}
