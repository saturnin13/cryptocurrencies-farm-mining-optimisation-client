package com.company.Miners.MiningAlgorithm.ASCIMining.SHA256Currencies;

import com.company.CommandExecutor.CommandOutputMonitoring.CommandOutputMonitor;
import com.company.Miners.MiningAlgorithm.ASCIMining.SHA256;
import org.apache.log4j.Logger;

import java.util.List;

import static com.company.Miners.MinedCurrencyShortName.BTC;

public class Bitcoin extends SHA256 {

    private final static Logger logger = Logger.getLogger(Bitcoin.class);

    public Bitcoin() {
        minedCurrencyShortName = BTC;
    }


    @Override
    protected List<String> getMiningCommandsWindows() {
        return null;
    }

    @Override
    protected List<String> getMiningCleanUpCommandsWindows() {
        return null;
    }

    @Override
    protected List<String> getMiningCommandsLinux() {
        return null;
    }

    @Override
    protected List<String> getMiningCleanUpCommandsLinux() {
        return null;
    }

    @Override
    protected List<String> getMiningCommandsMac() {
        return null;
    }

    @Override
    protected List<String> getMiningCleanUpCommandsMac() {
        return null;
    }

    @Override
    protected CommandOutputMonitor getOutputMonitoring() {
        return null;
    }

    @Override
    protected List<String> getInstallCommandsWindows() {
        return null;
    }

    @Override
    protected List<String> getInstallCommandsLinux() {
        return null;
    }

    @Override
    protected List<String> getInstallCommandsMac() {
        return null;
    }
}
