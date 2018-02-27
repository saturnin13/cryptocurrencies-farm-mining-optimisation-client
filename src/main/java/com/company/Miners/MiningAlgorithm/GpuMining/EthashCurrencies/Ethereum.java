package com.company.Miners.MiningAlgorithm.GpuMining.EthashCurrencies;

import com.company.CommandExecutor.ShellCommandExecutor;
import com.company.Miners.MiningAlgorithm.GpuMining.Ethash;
import com.google.common.collect.ImmutableList;
import jdk.nashorn.internal.ir.annotations.Immutable;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

import static com.company.Miners.MinedCurrencyShortName.ETH;
import static com.company.Variables.KEY_ETHEREUM;
import static com.company.Variables.LOCATION_MAIN_FOLDER;
import static com.company.Variables.WORKER_NAME;

public class Ethereum extends Ethash {

    private final static Logger logger = Logger.getLogger(Ethereum.class);
    private static final Ethereum instance = new Ethereum();

    //TODO: add a stratum proxy option
    private Ethereum() {
        minedCurrencyShortName = ETH;
    }

    public static Ethereum getInstance(){
        return instance;
    }

    @Override
    protected void startMiningWindows() {

    }

    @Override
    protected void startMiningLinux() {

    }

    // TODO : add stratum
    @Override
    protected void startMiningMac() {
        // TODO: change the location to variables
        // TODO: mine with -G for amd and -U for nvidia
        List<String> commands = new ImmutableList.Builder<String>()
            .add("cd " + LOCATION_MAIN_FOLDER + "/ethereum/bin")
            .add("export GPU_FORCE_64BIT_PTR=0")
            .add("export GPU_MAX_HEAP_SIZE=100")
            .add("export GPU_USE_SYNC_OBJECTS=1")
            .add("export GPU_MAX_ALLOC_PERCENT=100")
            .add("export GPU_SINGLE_ALLOC_PERCENT=100")
            .add("printenv")
            .add("./ethminer -G -F http://eth-eu.dwarfpool.com:80/" + KEY_ETHEREUM + "/" + WORKER_NAME)
            .build();
        ShellCommandExecutor.executeCommands(commands);
    }

    @Override
    protected void installWindows() {

    }

    @Override
    protected void installLinux() {

    }

    @Override
    protected void installMac() {
        List<String> commands = new ImmutableList.Builder<String>()
            .add("cd ~/Downloads/")
            .add("curl -L -O https://github.com/ethereum-mining/ethminer/releases/download/v0.13.0rc6/ethminer-0.13.0rc6-Darwin.tar.gz")
            .add("cd ~/")
            .add("mkdir -p " + LOCATION_MAIN_FOLDER)
            .add("cd mining-optimisation")
            .add("mkdir -p ethereum")
            .add("cd ethereum")
            .add("gunzip -c ~/Downloads/ethminer-0.13.0rc6-Darwin.tar.gz | tar xopf -")
            .build();
        ShellCommandExecutor.executeCommands(commands);
    }
}
