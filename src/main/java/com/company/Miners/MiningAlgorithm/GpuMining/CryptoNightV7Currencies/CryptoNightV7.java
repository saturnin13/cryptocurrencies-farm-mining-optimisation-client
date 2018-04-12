package com.company.Miners.MiningAlgorithm.GpuMining.CryptoNightV7Currencies;

import com.company.CommandsExecutor.CommandOutputMonitoring.CommandOutputMonitor;
import com.company.Miners.Miner;
import com.google.common.collect.ImmutableList;

import java.util.List;

import static com.company.Variables.KEY_MONERO;
import static com.company.Variables.LOCATION_MAIN_FOLDER;
import static com.company.Variables.WORKER_NAME;

public abstract class CryptoNightV7 extends Miner {

    // TODO: a command generator exist to create customised command line config
    @Override
    protected List<String> getMiningCommandsWindows() {
        return new ImmutableList.Builder<String>()
                .add("cd " + LOCATION_MAIN_FOLDER + "/" + minedCurrencyShortName + "/" + "xmrig-nvidia-2.6.0-beta1-cuda8-win64")
                .add("ls")
                .add("./xmrig-nvidia.exe --print-time 1 --donate-level 1 -o " + poolAddressProtocol1 + ":" + poolPortProtocol1 + " -u " + KEY_MONERO + " -p " + WORKER_NAME + " -k")
                .build();
    }

    @Override
    protected List<String> getMiningCleanUpCommandsWindows() {
        return new ImmutableList.Builder<String>()
                .add("Stop-Process -Name xmrig-nvidiag")
                .build();
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
        return new XmrigNvidiaOutputMonitor(minedCurrencyShortName);
    }

    @Override
    protected List<String> getInstallCommandsWindows() {
        return new ImmutableList.Builder<String>()
                .add("cd ~/Downloads/")
                .add("[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12") // by default TLS 1.0 is used, 1.2 is set
                // TODO: 2 version exist for nvidia and another miner is required for amd so that need to be done
                .add("Invoke-WebRequest -Uri \"https://github.com/xmrig/xmrig-nvidia/releases/download/v2.6.0-beta1/xmrig-nvidia-2.6.0-beta1-cuda8-win64.zip\" -OutFile ./xmrig-nvidia-2.6.0-beta1-cuda8-win64.zip")
                .add("cd ~/")
                .add("mkdir -Force " + LOCATION_MAIN_FOLDER)
                .add("cd mining-optimisation")
                .add("mkdir -Force " + minedCurrencyShortName)
                .add("cd " + minedCurrencyShortName)
                .add("Expand-Archive -Force ~/Downloads/xmrig-nvidia-2.6.0-beta1-cuda8-win64.zip")
                .build();
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
