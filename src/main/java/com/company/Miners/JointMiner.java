package com.company.Miners;

import com.company.CommandsExecutor.CommandOutputMonitoring.CommandOutputMonitor;

import java.util.List;

public class JointMiner extends Miner {

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
