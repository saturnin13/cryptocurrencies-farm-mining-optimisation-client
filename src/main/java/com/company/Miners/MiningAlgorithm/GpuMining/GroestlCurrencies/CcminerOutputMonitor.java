package com.company.Miners.MiningAlgorithm.GpuMining.GroestlCurrencies;

import com.company.CommandsExecutor.CommandOutputMonitoring.MiningCommandOutputMonitor;
import com.company.Miners.MinedCurrencyShortName;

public class CcminerOutputMonitor extends MiningCommandOutputMonitor {

    private String hashrateUnitRegex;

    public CcminerOutputMonitor(MinedCurrencyShortName currencyShortName) {
        super(currencyShortName);
    }

    @Override
    protected String getHashrateRegex() {
        return ",( )(.*?) " + getHashrateUnitRegex();
    }
}
