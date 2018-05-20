package com.company.Miners.MinerManagment;

import com.company.Client.JsonFormat.General.GPU.GPU;
import com.company.Client.JsonFormat.General.MinedCurrencyShortName;
import com.company.MachineInformation.MachineConfigurationRetriever;
import com.company.Miners.Miner;

import static com.company.Miners.Protocol.MAIN;

public class MinersStarter {
    private static Miner currentMiner = null;

    public static void startMiner(MinedCurrencyShortName currencyShortName, GPU gpu) {
        if(currentMiner != null) {
            currentMiner.stopMining();
        }
        if(currencyShortName != null) {
            currentMiner = MinersFactory.getMiner(currencyShortName);
            currentMiner.startMining(MachineConfigurationRetriever.getMachineCharacteristics().getOs().getOsType(), MAIN, gpu);
        }
    }
}
