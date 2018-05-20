package com.company.Miners;

import com.company.Client.JsonFormat.ClientJson.MiningConfiguration.ClientConfiguration.ClientConfiguration;
import com.company.CommandsExecutor.CommandOutputMonitoring.CommandOutputMonitor;

import java.util.List;

public class JointMiner extends Miner {

    @Override
    protected CommandOutputMonitor getOutputMonitoring() {
        return null;
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
