package com.company.Miners.MiningAlgorithm.GpuMining.EthashCurrencies;

import com.company.Client.JsonFormat.General.GPU.GPU;
import com.company.Client.JsonFormat.General.MinedCurrencyShortName;
import com.company.CommandsExecutor.CommandOutputMonitoring.MiningCommandOutputMonitor;

public class EthMinerOutputMonitor extends MiningCommandOutputMonitor {

    public EthMinerOutputMonitor(MinedCurrencyShortName currencyShortName, GPU gpu) {
        super(currencyShortName, gpu);
    }

    @Override
    public String getHashrateRegex() {
        return "Speed( )*(.*?) "+ getHashrateUnitRegex();
    }
}
