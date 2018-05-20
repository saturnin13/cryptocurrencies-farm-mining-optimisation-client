package com.company.Miners.MiningAlgorithm.ASCIMining.SHA256Currencies;

import com.company.Client.JsonFormat.ClientJson.MiningConfiguration.ClientConfiguration.ClientConfiguration;
import com.company.CommandsExecutor.CommandOutputMonitoring.CommandOutputMonitor;
import com.company.Miners.MiningAlgorithm.ASCIMining.SHA256;
import org.apache.log4j.Logger;

import java.util.List;

import static com.company.Client.JsonFormat.General.MinedCurrencyShortName.BTC;

public class Bitcoin extends SHA256 {

    private final static Logger logger = Logger.getLogger(Bitcoin.class);

    public Bitcoin() {
        minedCurrencyShortName = BTC;
    }

    @Override
    protected CommandOutputMonitor getOutputMonitoring() {
        return null;
    }
    @Override
    public boolean isInstalled() {
        return true;
    }


    @Override
    protected List<String> getExecuteMiningCommandsWindows() {
        return null;
    }

    @Override
    protected List<String> getExecuteMiningCommandsLinux() {
        return null;
    }

    @Override
    protected List<String> getExecuteMiningCommandsMac() {
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

    @Override
    public boolean canMineOnMachine(ClientConfiguration clientConfiguration) {
        return false;
    }
}
