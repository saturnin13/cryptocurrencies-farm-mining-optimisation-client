package com.company.CommandsExecutor.CommandOutputMonitoring;

public interface CommandOutputMonitor {
    public void monitorOutput(String line, String previousLine);
}
