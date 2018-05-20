package com.company.Miners.MiningAlgorithm.GpuMining.CryptoNightV7Currencies;

import com.company.Client.JsonFormat.General.GPU.GPU;
import com.company.Client.JsonFormat.General.MinedCurrencyShortName;
import com.company.CommandsExecutor.CommandOutputMonitoring.MiningCommandOutputMonitor;

public class XmrigNvidiaOutputMonitor extends MiningCommandOutputMonitor {
    public XmrigNvidiaOutputMonitor(MinedCurrencyShortName minedCurrencyShortName, GPU gpu) {
        super(minedCurrencyShortName, gpu);
    }

    @Override
    protected String getHashrateRegex() {
        return "max:( )*(.*?) " + getHashrateUnitRegex();
    }

}
