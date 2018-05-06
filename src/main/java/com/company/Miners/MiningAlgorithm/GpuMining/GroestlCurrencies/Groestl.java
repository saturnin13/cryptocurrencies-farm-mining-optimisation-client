package com.company.Miners.MiningAlgorithm.GpuMining.GroestlCurrencies;

import com.company.CommandsExecutor.CommandOutputMonitoring.CommandOutputMonitor;
import com.company.MachineInformation.Configuration.ClientConfiguration;
import com.company.Miners.KeyManager;
import com.company.Miners.Miner;
import com.google.common.collect.ImmutableList;

import java.util.Arrays;
import java.util.List;

import static com.company.Variables.*;

public abstract class Groestl extends Miner {
    // TODO move some stuff in parent class

    public Groestl() {
        processName = "ccminer-x64";
        downloadLinksWindows = Arrays.asList("https://github.com/tpruvot/ccminer/releases/download/2.2.5-tpruvot/ccminer-x64-2.2.5-cuda9.7z");
    }

    @Override
    protected List<String> getExecuteMiningCommandsWindows() {
        return new ImmutableList.Builder<String>()
                .add("./ccminer-x64 -a groestl -o \"" + poolAddressProtocol1 + ":" + poolPortProtocol1 + "\" -u " + KeyManager.getKey(minedCurrencyShortName) + "." + WORKER_NAME + " -p 1")
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
    protected CommandOutputMonitor getOutputMonitoring() {
        return new CcminerOutputMonitor(minedCurrencyShortName);
    }


    @Override
    public boolean canMineOnMachine(ClientConfiguration clientConfiguration) {
        return true;
    }
}
