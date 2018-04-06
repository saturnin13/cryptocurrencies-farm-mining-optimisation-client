package com.company.Miners.MiningAlgorithm.GpuMining.EthashCurrencies;

import com.company.Miners.Miner;
import com.google.common.collect.ImmutableList;

import java.util.List;

import static com.company.Variables.*;

public abstract class Ethash extends Miner {
    protected String poolAddress;

    @Override
    protected List<String> getMiningCommandsWindows() {
        return new ImmutableList.Builder<String>()
                .add("cd " + LOCATION_MAIN_FOLDER + "/Ethash/bin")
                .add("setx GPU_FORCE_64BIT_PTR 0")
                .add("setx GPU_MAX_HEAP_SIZE 100")
                .add("setx GPU_USE_SYNC_OBJECTS 1")
                .add("setx GPU_MAX_ALLOC_PERCENT 100")
                .add("setx GPU_SINGLE_ALLOC_PERCENT 100") // all for 2gb mining
                .add("./ethminer -U -F " + poolAddress + "/" + KEY_ETHEREUM + "/" + WORKER_NAME)
                .build();
    }

    @Override
    protected List<String> getMiningCleanUpCommandsWindows() {
        return new ImmutableList.Builder<String>()
                .add("Stop-Process -Name ethminer")
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

    // TODO : add stratum
    // TODO: change the location to variables
    // TODO: mine with -G for amd and -U for nvidia
    // TODO: error "Insufficient CUDA driver: 8000" if driver nvidia not up to date
    // TODO: DAG file too big for memory check
    @Override
    protected List<String> getMiningCommandsMac() {
        return new ImmutableList.Builder<String>()
                .add("cd " + LOCATION_MAIN_FOLDER + "/Ethash/bin")
                .add("export GPU_FORCE_64BIT_PTR=0")
                .add("export GPU_MAX_HEAP_SIZE=100")
                .add("export GPU_USE_SYNC_OBJECTS=1")
                .add("export GPU_MAX_ALLOC_PERCENT=100")
                .add("export GPU_SINGLE_ALLOC_PERCENT=100")
                .add("./ethminer -G -F " + poolAddress + "/" + KEY_ETHEREUM + "/" + WORKER_NAME)
                .build();
    }

    @Override
    protected List<String> getMiningCleanUpCommandsMac() {
        return new ImmutableList.Builder<String>()
                //TODO: test this line on mac
                .add("pkill -f ethminer")
                .build();
    }

    @Override
    protected List<String> getInstallCommandsWindows() {
        // TODO: for some bug using an earlier version of ethminer fixes it
        return new ImmutableList.Builder<String>()
                .add("cd ~/Downloads/")
                .add("[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12") // by default TLS 1.0 is used, 1.2 is set
                .add("Invoke-WebRequest -Uri \"https://github.com/ethereum-mining/ethminer/releases/download/v0.12.0/ethminer-0.12.0-Windows.zip\" -OutFile ./ethminer-0.12.0-Windows.zip")
                .add("cd ~/")
                .add("mkdir -Force " + LOCATION_MAIN_FOLDER)
                .add("cd mining-optimisation")
                .add("mkdir -Force Ethash")
                .add("cd Ethash")
                .add("Expand-Archive -Force ~/Downloads/ethminer-0.12.0-Windows.zip")
                .add("mv ./ethminer-0.12.0-Windows/bin/ ./")
                .add("rm -r ethminer-0.12.0-Windows")
                .build();
    }

    @Override
    protected List<String> getInstallCommandsLinux() {
        return null;
    }

    @Override
    protected List<String> getInstallCommandsMac() {
        return new ImmutableList.Builder<String>()
                .add("cd ~/Downloads/")
                .add("curl -L -O https://github.com/ethereum-mining/ethminer/releases/download/v0.13.0rc6/ethminer-0.13.0rc6-Darwin.tar.gz")
                .add("cd ~/")
                .add("mkdir -p " + LOCATION_MAIN_FOLDER)
                .add("cd mining-optimisation")
                .add("mkdir -p Ethash")
                .add("cd Ethash")
                .add("gunzip -c ~/Downloads/ethminer-0.13.0rc6-Darwin.tar.gz | tar xopf -")
                .build();
    }

    @Override
    public boolean isInstalled() {
        return existLocation(LOCATION_MAIN_FOLDER + "/Ethash/bin");
    }
}
