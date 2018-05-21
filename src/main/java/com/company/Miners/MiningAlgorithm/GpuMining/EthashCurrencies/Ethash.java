package com.company.Miners.MiningAlgorithm.GpuMining.EthashCurrencies;

import com.company.Client.JsonFormat.ClientJson.MiningConfiguration.ClientConfiguration.ClientConfiguration;
import com.company.CommandsExecutor.CommandExecutor;
import com.company.CommandsExecutor.CommandOutputMonitoring.CommandOutputMonitor;
import com.company.Miners.KeyManager;
import com.company.Miners.Miner;
import com.google.common.collect.ImmutableList;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.company.Client.JsonFormat.General.GPU.GPUType.CUDA;
import static com.company.Miners.Protocol.MAIN;
import static com.company.Miners.Protocol.SECONDARY;
import static com.company.Variables.*;

// Mining Ethhash is done using ethminer, claymore and stratum can be used as well
public abstract class Ethash extends Miner {

    private final static Logger logger = Logger.getLogger(Ethash.class);

    private CommandExecutor proxyThread;
    protected long necessaryDagSize;

    public Ethash() {
        processName = "ethminer";
        // TODO: for some bug using an earlier version of ethminer fixes it
        downloadLinksWindows = Arrays.asList(
                "https://github.com/ethereum-mining/ethminer/releases/download/v0.12.0/ethminer-0.12.0-Windows.zip",
                "https://github.com/Atrides/eth-proxy/releases/download/0.0.5/eth-proxy-win.zip");
        downloadLinksMac = Arrays.asList("https://github.com/ethereum-mining/ethminer/releases/download/v0.13.0rc6/ethminer-0.13.0rc6-Darwin.tar.gz");

    }

    @Override
    protected void miningSetUpActionsWindows() {
        if(protocol == MAIN) { // stratum protocol
            launchProxyWindows();
        }
    }

    //TODO: Add failover addresses
    private void launchProxyWindows() {
        List<String> commands = new ImmutableList.Builder<String>()
                .add("cd " + LOCATION_MAIN_FOLDER + "/" + processName + "/eth-proxy-win/eth-proxy")
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
                .doReadlines(false)
                .build();
        proxyThread.start();
    }

    @Override
    protected List<String> getExecuteMiningCommandsWindows() {
        if(protocol == MAIN) { // stratum protocol
            return  Arrays.asList("./ethminer-0.12.0-Windows/bin/ethminer --farm-recheck 200 " + (gpu.getGpuType() == CUDA ? "-U":"-G") + " -F http://127.0.0.1:8080/ "
                    + (gpu.getGpuType() == CUDA ? "--cuda-devices":"--opencl-device") + " " + gpu.getId());
        } else if(protocol == SECONDARY){ // Getwork
            return Arrays.asList("./ethminer-0.12.0-Windows/bin/ethminer --farm-recheck 200 " + (gpu.getGpuType() == CUDA ? "-U":"-G") + " -F " + poolAddressProtocol2 + ":" + poolPortProtocol2 + "/" + KeyManager.getKey(minedCurrencyShortName) + "/" + WORKER_NAME + " "
                    + (gpu.getGpuType() == CUDA ? "--cuda-devices":"--opencl-device") + " " + gpu.getId());
        } else { // Genoils Miner using stratum mode
            //TODO the SP parameter must be changed depending on the pool
            return Arrays.asList("./ethminer-0.12.0-Windows/bin/ethminer --farm-recheck 200 " + (gpu.getGpuType() == CUDA ? "-U":"-G") + " -S " + poolAddressProtocol1 + ":" + poolPortProtocol1 + " -SP 1 -O " + KeyManager.getKey(minedCurrencyShortName) + ":" + WORKER_NAME + " "
                    + (gpu.getGpuType() == CUDA ? "--cuda-devices":"--opencl-device") + " " + gpu.getId());
        }
    }

    @Override
    protected void miningSetUpActionsLinux() {
        if(protocol == MAIN) { // stratum protocol
            launchProxyLinux();
        }
    }

    private void launchProxyLinux() {

    }

    @Override
    protected List<String> getExecuteMiningCommandsLinux() {
        return null;
    }

    // TODO put les export de partout
    // TODO: create an abstraction to generate the commands for the current machine
    // TODO: change the location to variables
    // TODO: mine with -G for OPEN_CL and -U for CUDA
    // TODO: error "Insufficient CUDA driver: 8000" if driver CUDA not up to date
    @Override
    protected void miningSetUpActionsMac() {
        if(protocol == MAIN) { // stratum protocol
            launchProxyMac();
        }
    }

    //TODO: Add failover addresses
    private void launchProxyMac() {
        logger.error("TODO Implement launchProxyMac()");
    }

    // TODO change path to command to work
    @Override
    protected List<String> getExecuteMiningCommandsMac() {
        if(protocol == MAIN) { // stratum protocol
            return  Arrays.asList("./ethminer-0.12.0-Windows/bin/ethminer --farm-recheck 200 " + (gpu.getGpuType() == CUDA ? "-U":"-G") + " -F http://127.0.0.1:8080/ "
                    + (gpu.getGpuType() == CUDA ? "--cuda-devices":"--opencl-device") + " " + gpu.getId());
        } else if(protocol == SECONDARY){ // Getwork
            return Arrays.asList("./ethminer-0.12.0-Windows/bin/ethminer --farm-recheck 200 " + (gpu.getGpuType() == CUDA ? "-U":"-G") + " -F " + poolAddressProtocol2 + ":" + poolPortProtocol2 + "/" + KeyManager.getKey(minedCurrencyShortName) + "/" + WORKER_NAME + " "
                    + (gpu.getGpuType() == CUDA ? "--cuda-devices":"--opencl-device") + " " + gpu.getId());
        } else { // Genoils Miner using stratum mode
            //TODO the SP parameter must be changed depending on the pool
            return Arrays.asList("./ethminer-0.12.0-Windows/bin/ethminer --farm-recheck 200 " + (gpu.getGpuType() == CUDA ? "-U":"-G") + " -S " + poolAddressProtocol1 + ":" + poolPortProtocol1 + " -SP 1 -O " + KeyManager.getKey(minedCurrencyShortName) + ":" + WORKER_NAME + " "
                    + (gpu.getGpuType() == CUDA ? "--cuda-devices":"--opencl-device") + " " + gpu.getId());
        }
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
        return new EthMinerOutputMonitor(minedCurrencyShortName, gpu);
    }

    @Override
    protected List<String> getInstallConfigCommandsWindows() {
        return new ImmutableList.Builder<String>()
                .add("cd eth-proxy-win/eth-proxy")
                .add("(Get-Content ./eth-proxy.conf) -replace 'HOST = \\\"0.0.0.0\\\"', 'HOST = \\\"127.0.0.1\\\"' | Set-Content ./eth-proxy.conf")
                .add("(Get-Content ./eth-proxy.conf) -replace 'WALLET = \\\"XXXXXX\\\"', 'WALLET = \\\"" + KeyManager.getKey(minedCurrencyShortName) + "\\\"' | Set-Content ./eth-proxy.conf")
                .build();
    }

    // TODO: add stratum config
    @Override
    protected List<String> getInstallConfigCommandsLinux() {
        return new LinkedList<>();
    }

    // TODO: add stratum config
    @Override
    protected List<String> getInstallConfigCommandsMac() {
        return new LinkedList<>();
    }

    @Override
    public boolean canMineOnMachine(ClientConfiguration clientConfiguration) {
        return clientConfiguration.getGpus().stream().anyMatch(gpu -> gpu.getMemorySize() >= necessaryDagSize);
    }
}
