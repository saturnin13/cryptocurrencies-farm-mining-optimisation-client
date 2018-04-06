package com.company.Miners.MiningAlgorithm.GpuMining.EthashCurrencies;

import com.company.CommandExecutor.CommandExecutor;
import com.company.Miners.MiningAlgorithm.GpuMining.Ethash;
import com.company.Timeout.TimeoutManager;
import com.google.common.collect.ImmutableList;
import org.apache.log4j.Logger;

import java.util.List;

import static com.company.CommandExecutor.CommandExecutionEnvironment.POWERSHELL;
import static com.company.Miners.MinedCurrencyShortName.UBQ;
import static com.company.Variables.*;

public class Ubiq extends Ethash {

    private final static Logger logger = Logger.getLogger(Ubiq.class);
    private static final Ubiq instance = new Ubiq();

    //TODO: add a stratum proxy option
    private Ubiq() {
        minedCurrencyShortName = UBQ;
    }

    public static Ubiq getInstance(){
        return instance;
    }

    @Override
    protected void startMiningWindows() {
        // TODO: change the location to variables
        // TODO: mine with -G for amd and -U for nvidia
        // TODO: error "Insufficient CUDA driver: 8000" if driver nvidia not up to date
        // TODO: DAG file too big for memory check
        List<String> commands = new ImmutableList.Builder<String>()
                .add("cd " + LOCATION_MAIN_FOLDER + "/" + minedCurrencyShortName + "/bin")
                .add("setx GPU_FORCE_64BIT_PTR 0")
                .add("setx GPU_MAX_HEAP_SIZE 100")
                .add("setx GPU_USE_SYNC_OBJECTS 1")
                .add("setx GPU_MAX_ALLOC_PERCENT 100")
                .add("setx GPU_SINGLE_ALLOC_PERCENT 100") // all for 2gb mining
                .add("./ethminer -U -F http://lb.geo.ubiqpool.org:8881/" + KEY_ETHEREUM + "/" + WORKER_NAME)
                .build();
        CommandExecutor.executeCommands(commands, POWERSHELL, true, TimeoutManager.timeout(minedCurrencyShortName));
    }

    @Override
    protected void startMiningLinux() {

    }

    @Override
    protected void startMiningMac() {

    }

    @Override
    protected void installWindows() {
        // TODO: for some bug using an earlier version of ethminer fixes it
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

    }
}
