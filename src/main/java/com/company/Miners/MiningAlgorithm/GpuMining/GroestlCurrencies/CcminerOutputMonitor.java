package com.company.Miners.MiningAlgorithm.GpuMining.GroestlCurrencies;

import com.company.Client.JsonFormat.General.GPU.GPU;
import com.company.Client.JsonFormat.General.MinedCurrencyShortName;
import com.company.CommandsExecutor.CommandOutputMonitoring.MiningCommandOutputMonitor;

public class CcminerOutputMonitor extends MiningCommandOutputMonitor {

    private String hashrateUnitRegex;

    public CcminerOutputMonitor(MinedCurrencyShortName currencyShortName, GPU gpu) {
        super(currencyShortName, gpu);
    }

    @Override
    protected String getHashrateRegex() {
        return ",( )(.*?) " + getHashrateUnitRegex();
    }
}
