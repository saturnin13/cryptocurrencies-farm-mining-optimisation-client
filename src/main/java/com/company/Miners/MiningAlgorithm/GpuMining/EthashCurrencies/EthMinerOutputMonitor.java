package com.company.Miners.MiningAlgorithm.GpuMining.EthashCurrencies;

import com.company.CommandsExecutor.CommandOutputMonitoring.MiningCommandOutputMonitor;
import com.company.Miners.MinedCurrencyShortName;

import static com.company.Variables.MEGA;

public class EthMinerOutputMonitor extends MiningCommandOutputMonitor {

    public EthMinerOutputMonitor(MinedCurrencyShortName currencyShortName) {
        super(currencyShortName);
    }

    @Override
    public String getHashrateRegex() {
        return "Speed( )*(.*?) "+ getHashrateUnitRegex();
    }
}
