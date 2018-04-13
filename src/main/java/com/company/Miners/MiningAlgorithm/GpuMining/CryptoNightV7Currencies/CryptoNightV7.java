package com.company.Miners.MiningAlgorithm.GpuMining.CryptoNightV7Currencies;

import com.company.CommandsExecutor.CommandOutputMonitoring.CommandOutputMonitor;
import com.company.MachineInformation.Configuration.ClientConfiguration;
import com.company.Miners.Miner;
import com.google.common.collect.ImmutableList;

import java.util.Arrays;
import java.util.List;

import static com.company.Variables.KEY_MONERO;
import static com.company.Variables.WORKER_NAME;

public abstract class CryptoNightV7 extends Miner {

    public CryptoNightV7() {
        processName = "xmrig-nvidia";
        downloadLinksWindows = Arrays.asList("https://github.com/xmrig/xmrig-nvidia/releases/download/v2.6.0-beta1/xmrig-nvidia-2.6.0-beta1-cuda8-win64.zip");
    }

    // TODO: a command generator exist to create customised command line config

    @Override
    protected List<String> getExecuteMiningCommandsWindows() {
        return new ImmutableList.Builder<String>()
                .add("./xmrig-nvidia-2.6.0-beta1-cuda8-win64/xmrig-nvidia.exe --print-time 1 --donate-level 1 -o "
                        + poolAddressProtocol1 + ":" + poolPortProtocol1 + " -u " + KEY_MONERO + " -p " + WORKER_NAME + " -k")
                .build();
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
    public boolean canMineOnMachine(ClientConfiguration clientConfiguration) {
        return true; // TODO: look into to implement
    }

    @Override
    protected CommandOutputMonitor getOutputMonitoring() {
        return new XmrigNvidiaOutputMonitor(minedCurrencyShortName);
    }
}
