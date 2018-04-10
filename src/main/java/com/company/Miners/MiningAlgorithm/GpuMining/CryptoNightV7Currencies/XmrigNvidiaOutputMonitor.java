package com.company.Miners.MiningAlgorithm.GpuMining.CryptoNightV7Currencies;

import com.company.CommandExecutor.CommandOutputMonitoring.MiningCommandOutputMonitor;
import com.company.Miners.MinedCurrencyShortName;

public class XmrigNvidiaOutputMonitor extends MiningCommandOutputMonitor {
    public XmrigNvidiaOutputMonitor(MinedCurrencyShortName minedCurrencyShortName) {
        super(minedCurrencyShortName);
    }

    @Override
    protected String getHashrateRegex() {
        return "max:( )*(.*?) H/s";
    }

    @Override
    protected Float getScaling() {
        return 1F;
    }
}