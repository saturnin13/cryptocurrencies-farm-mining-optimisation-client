package com.company.Miners.MiningAlgorithm.GpuMining.EthashCurrencies;

import com.company.CommandExecutor.CommandExecutor;
import com.company.Miners.MiningAlgorithm.GpuMining.Ethash;
import com.google.common.collect.ImmutableList;
import org.apache.log4j.Logger;

import java.util.List;

import static com.company.CommandExecutor.CommandExecutionEnvironment.POWERSHELL;
import static com.company.Miners.MinedCurrencyShortName.ETH;
import static com.company.Variables.*;

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
            .add("./ethminer -G -F http://eth-eu.dwarfpool.com:80/" + KEY_ETHEREUM + "/" + WORKER_NAME)
            .build();
        CommandExecutor.executeCommands(commands, POWERSHELL, true, MINING_TIMEOUT_MILLIS);
    }

    @Override
    // TODO : check that the duplication of installation of the ethminer could not be removed
    protected void installWindows() {
        List<String> commands = new ImmutableList.Builder<String>()
                .add("cd ~/Downloads/")
                .add("[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12") // by default TLS 1.0 is used, 1.2 is set
                .add("Invoke-WebRequest -Uri \"https://github.com/ethereum-mining/ethminer/releases/download/v0.12.0/ethminer-0.12.0-Windows.zip\" -OutFile ./ethminer-0.12.0-Windows.zip")
                .add("cd ~/")
                .add("mkdir -Force " + LOCATION_MAIN_FOLDER)
                .add("cd mining-optimisation")
                .add("mkdir -Force "+ minedCurrencyShortName)
                .add("cd " + minedCurrencyShortName)
                .add("Expand-Archive -Force ~/Downloads/ethminer-0.12.0-Windows.zip")
                .add("mv ./ethminer-0.12.0-Windows/bin/ ./")
                .add("rm -r ethminer-0.12.0-Windows")
                .build();
        CommandExecutor.executeCommands(commands, POWERSHELL, true);
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
            .add("mkdir -p "+ minedCurrencyShortName)
            .add("cd " + minedCurrencyShortName + "")
            .add("gunzip -c ~/Downloads/ethminer-0.13.0rc6-Darwin.tar.gz | tar xopf -")
            .build();
        CommandExecutor.executeCommands(commands, POWERSHELL, true);
    }
}
