package com.company.Miners.MiningAlgorithm.GpuMining.CryptoNightV7Currencies;

import com.company.CommandsExecutor.CommandOutputMonitoring.MiningCommandOutputMonitor;
import com.company.Miners.MinedCurrencyShortName;

public class XmrigNvidiaOutputMonitor extends MiningCommandOutputMonitor {
    public XmrigNvidiaOutputMonitor(MinedCurrencyShortName minedCurrencyShortName) {
        super(minedCurrencyShortName);
    }

    @Override
    protected String getHashrateRegex() {
        return "max:( )*(.*?) " + getHashrateUnitRegex();
    }

}
