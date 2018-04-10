package com.company.Miners.MiningAlgorithm.GpuMining.EthashCurrencies;

import com.company.CommandExecutor.CommandOutputMonitoring.MiningCommandOutputMonitor;
import com.company.Miners.MinedCurrencyShortName;

public class EthMinerOutputMonitor extends MiningCommandOutputMonitor {

    public EthMinerOutputMonitor(MinedCurrencyShortName currencyShortName) {
        super(currencyShortName);
    }

    @Override
    public String getHashrateRegex() {
        return "Speed   (.*?) Mh/s";
    }


}
