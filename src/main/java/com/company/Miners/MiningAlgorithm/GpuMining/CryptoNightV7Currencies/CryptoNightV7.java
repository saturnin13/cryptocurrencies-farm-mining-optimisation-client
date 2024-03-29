package com.company.Miners.MiningAlgorithm.GpuMining.CryptoNightV7Currencies;

import com.company.Client.JsonFormat.ClientJson.MiningConfiguration.ClientConfiguration.ClientConfiguration;
import com.company.CommandsExecutor.CommandOutputMonitoring.CommandOutputMonitor;
import com.company.Miners.KeyManager;
import com.company.Miners.Miner;
import com.google.common.collect.ImmutableList;

import java.util.Arrays;
import java.util.List;

import static com.company.Client.JsonFormat.General.GPU.GPUType.CUDA;
import static com.company.Variables.WORKER_NAME;

public abstract class CryptoNightV7 extends Miner {

    public CryptoNightV7() {
        processName = "xmrig-nvidia";
        downloadLinksWindows = Arrays.asList("https://github.com/xmrig/xmrig-nvidia/releases/download/v2.6.0-beta1/xmrig-nvidia-2.6.0-beta1-cuda8-win64.zip"
                ,"https://github.com/xmrig/xmrig-amd/releases/download/v2.6.1/xmrig-amd-2.6.1-win64.zip");
    }

    // TODO: a command generator exist to create customised command line config
    @Override
    protected List<String> getExecuteMiningCommandsWindows() {
        return new ImmutableList.Builder<String>()
                .add("./" + (gpu.getGpuType() == CUDA ? "xmrig-nvidia-2.6.0-beta1-cuda8-win64/xmrig-nvidia.exe":"xmrig-amd-2.6.1-win64/xmrig-amd.exe") + " --print-time 1 --donate-level 1 -o "
                        + poolAddressProtocol1 + ":" + poolPortProtocol1 + " -u " + KeyManager.getKey(minedCurrencyShortName) + " -p " + WORKER_NAME + " -k "
                        + (gpu.getGpuType() == CUDA ? "--cuda-devices=":"--opencl-devices=") + gpu.getId())
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
        return new XmrigNvidiaOutputMonitor(minedCurrencyShortName, gpu);
    }
}
