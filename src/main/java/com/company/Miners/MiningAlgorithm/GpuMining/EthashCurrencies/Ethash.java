package com.company.Miners.MiningAlgorithm.GpuMining.EthashCurrencies;

import com.company.CommandsExecutor.CommandExecutor;
import com.company.CommandsExecutor.CommandOutputMonitoring.CommandOutputMonitor;
import com.company.MachineInformation.Configuration.ClientConfiguration;
import com.company.Miners.Miner;
import com.company.Miners.Protocol;
import com.google.common.collect.ImmutableList;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.company.Miners.Protocol.MAIN;
import static com.company.Miners.Protocol.SECONDARY;
import static com.company.Variables.*;

// Mining Ethhash is done using ethminer, claymore and stratum can be used as well
public abstract class Ethash extends Miner {

    private CommandExecutor proxyThread;
    protected long necessaryDagSize;

    @Override
    protected List<String> getMiningCommandsWindows() {
        String ethminerCommand;
        if(protocol == MAIN) { // stratum protocol
            launchProxyWindows();
            ethminerCommand = "./ethminer --farm-recheck 200 -U -F http://127.0.0.1:8080/";
        } else if(protocol == SECONDARY){ // Getwork
            ethminerCommand = "./ethminer --farm-recheck 200 -U -F " + poolAddressProtocol2 + ":" + poolPortProtocol2 + "/" + KEY_ETHEREUM + "/" + WORKER_NAME;
        } else { // Genoils Miner using stratum mode
            //TODO the SP parameter must be changed depending on the pool
            ethminerCommand = "./ethminer --farm-recheck 200 -U -S " + poolAddressProtocol1 + ":" + poolPortProtocol1 + " -SP 1 -O " + KEY_ETHEREUM + ":" + WORKER_NAME;
        }
        return new ImmutableList.Builder<String>()
                .add("cd " + LOCATION_MAIN_FOLDER + "/Ethash/ethminer-0.12.0-Windows/bin")
                .add("setx GPU_FORCE_64BIT_PTR 0")
                .add("setx GPU_MAX_HEAP_SIZE 100")
                .add("setx GPU_USE_SYNC_OBJECTS 1")
                .add("setx GPU_MAX_ALLOC_PERCENT 100")
                .add("setx GPU_SINGLE_ALLOC_PERCENT 100") // All for 2gb mining
                .add(ethminerCommand)
                .build();
    }

    //TODO: Add failover addresses
    private void launchProxyWindows() {
        List<String> commands = new ImmutableList.Builder<String>()
                .add("cd " + LOCATION_MAIN_FOLDER + "/Ethash/eth-proxy-win/eth-proxy")
                .add("(Get-Content ./eth-proxy.conf) -replace 'POOL_HOST = \\\".*\\\"', 'POOL_HOST = \\\"" + poolAddressProtocol1 + "\\\"' | Set-Content ./eth-proxy.conf")
                .add("(Get-Content ./eth-proxy.conf) -replace 'POOL_PORT = [0-9]+', 'POOL_PORT = " + poolPortProtocol1 + "' | Set-Content ./eth-proxy.conf")
                .add("(Get-Content ./eth-proxy.conf) -replace 'POOL_HOST_FAILOVER1 = \\\".*\\\"', 'POOL_HOST_FAILOVER1 = \\\"" + poolAddressProtocol1 + "\\\"' | Set-Content ./eth-proxy.conf")
                .add("(Get-Content ./eth-proxy.conf) -replace 'POOL_PORT_FAILOVER1 = [0-9]+', 'POOL_PORT_FAILOVER1 = " + poolPortProtocol1 + "' | Set-Content ./eth-proxy.conf")
                .add("(Get-Content ./eth-proxy.conf) -replace 'POOL_HOST_FAILOVER2 = \\\".*\\\"', 'POOL_HOST_FAILOVER2 = \\\"" + poolAddressProtocol1 + "\\\"' | Set-Content ./eth-proxy.conf")
                .add("(Get-Content ./eth-proxy.conf) -replace 'POOL_PORT_FAILOVER2 = [0-9]+', 'POOL_PORT_FAILOVER2 = " + poolPortProtocol1 + "' | Set-Content ./eth-proxy.conf")
                .add("(Get-Content ./eth-proxy.conf) -replace 'POOL_HOST_FAILOVER3 = \\\".*\\\"', 'POOL_HOST_FAILOVER3 = \\\"" + poolAddressProtocol1 + "\\\"' | Set-Content ./eth-proxy.conf")
                .add("(Get-Content ./eth-proxy.conf) -replace 'POOL_PORT_FAILOVER3 = [0-9]+', 'POOL_PORT_FAILOVER3 = " + poolPortProtocol1 + "' | Set-Content ./eth-proxy.conf")
                .add("./eth-proxy")
                .build();
        List<String> cleanUpCommands = new ImmutableList.Builder<String>()
                .add("Stop-Process -Name eth-proxy")
                .build();
        proxyThread = CommandExecutor.builder()
                .commands(commands)
                .cleanUpCommands(cleanUpCommands)
                .verbose(true)
                .build();
        proxyThread.start();
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

    // TODO: create an abstraction to generate the commands for the current machine
    // TODO: change the location to variables
    // TODO: mine with -G for openCl and -U for cuda
    // TODO: error "Insufficient CUDA driver: 8000" if driver cuda not up to date
    @Override
    protected List<String> getMiningCommandsMac() {
        String ethminerCommand;
        if(protocol == MAIN) { // stratum protocol
            launchProxyMac();
            ethminerCommand = "./ethminer --farm-recheck 200 -U -F http://127.0.0.1:8080/";
        } else if(protocol == SECONDARY){ // Getwork
            ethminerCommand = "./ethminer --farm-recheck 200 -U -F " + poolAddressProtocol2 + ":" + poolPortProtocol2 + "/" + KEY_ETHEREUM + "/" + WORKER_NAME;
        } else { // Genoils Miner using stratum mode
            //TODO the SP parameter must be changed depending on the pool
            ethminerCommand = "./ethminer --farm-recheck 200 -U -S " + poolAddressProtocol1 + ":" + poolPortProtocol1 + " -SP 1 -O " + KEY_ETHEREUM + ":" + WORKER_NAME;
        }
        return new ImmutableList.Builder<String>()
                .add("cd " + LOCATION_MAIN_FOLDER + "/Ethash/ethminer-0.12.0-Windows/bin")
                .add("export GPU_FORCE_64BIT_PTR=0")
                .add("export GPU_MAX_HEAP_SIZE=100")
                .add("export GPU_USE_SYNC_OBJECTS=1")
                .add("export GPU_MAX_ALLOC_PERCENT=100")
                .add("export GPU_SINGLE_ALLOC_PERCENT=100")
                .add(ethminerCommand)
                .build();
    }

    private void launchProxyMac(){

    }

    @Override
    public void stopMining() {
        super.stopMining();
        if(proxyThread != null && proxyThread.isAlive()) {
            proxyThread.exit();
        }
    }

    @Override
    protected CommandOutputMonitor getOutputMonitoring() {
        return new EthMinerOutputMonitor(minedCurrencyShortName);
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
                .add("Invoke-WebRequest -Uri \"https://github.com/Atrides/eth-proxy/releases/download/0.0.5/eth-proxy-win.zip\" -OutFile ./eth-proxy-win.zip")
                .add("cd ~/")
                .add("mkdir -Force " + LOCATION_MAIN_FOLDER)
                .add("cd mining-optimisation")
                .add("mkdir -Force Ethash")
                .add("cd Ethash")
                .add("Expand-Archive -Force ~/Downloads/ethminer-0.12.0-Windows.zip")
                .add("Expand-Archive -Force ~/Downloads/eth-proxy-win.zip")
                .add("cd eth-proxy-win/eth-proxy")
                .add("(Get-Content ./eth-proxy.conf) -replace 'HOST = \\\"0.0.0.0\\\"', 'HOST = \\\"127.0.0.1\\\"' | Set-Content ./eth-proxy.conf")
                .add("(Get-Content ./eth-proxy.conf) -replace 'WALLET = \\\"XXXXXX\\\"', 'WALLET = \\\"" + KEY_ETHEREUM + "\\\"' | Set-Content ./eth-proxy.conf")
                .build();
    }

    @Override
    protected List<String> getInstallCommandsLinux() {
        return null;
    }

    // TODO: redo with stratum
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
        return Files.isDirectory(Paths.get(LOCATION_MAIN_FOLDER + "/Ethash"));
    }

    @Override
    public boolean canMineOnMachine(ClientConfiguration clientConfiguration) {
        return clientConfiguration.getGpus().getGpus().stream().anyMatch(gpu -> gpu.getMemorySize() >= necessaryDagSize);
    }
}
