package com.company.MachineInformation.Configuration.GPU;

import com.company.CommandsExecutor.CommandExecutor;
import com.company.MachineInformation.Configuration.OS.OS;
import com.company.MachineInformation.Configuration.OS.OSType;
import com.company.Util.RegexPatternMatcher;
import lombok.Data;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.company.MachineInformation.Configuration.GPU.GPUType.openCl;
import static com.company.MachineInformation.Configuration.GPU.GPUType.cuda;
import static com.company.MachineInformation.Configuration.OS.OSType.linux;
import static com.company.MachineInformation.Configuration.OS.OSType.mac;
import static com.company.MachineInformation.Configuration.OS.OSType.windows;
import static java.util.regex.Pattern.CASE_INSENSITIVE;

@Data
public class GPUs {

    private final static Logger logger = Logger.getLogger(GPUs.class);

    private List<GPU> gpus;

    public GPUs() {
        this.gpus = findGpus();
    }

    private List<GPU> findGpus() {
        List<GPU> result = new LinkedList<>();

        String[] gpuTypes = CommandExecutor.builder().commands(Arrays.asList((commandGetDescription())))
                .build()
                .runAndReturn()
                .split("\\r?\\n");
        String[] gpuMemorySize = CommandExecutor.builder().commands(Arrays.asList((commandGetAdapterRAM())))
                .build()
                .runAndReturn()
                .split("\\r?\\n");

        for (int i = 0; i < gpuTypes.length; i++) {
            GPU currentGpu = new GPU();
            if (RegexPatternMatcher.patternMatch("(NVIDIA)", CASE_INSENSITIVE, gpuTypes[i], 1)) {
                currentGpu.setGpuType(cuda);
            } else if (RegexPatternMatcher.patternMatch("(AMD)", CASE_INSENSITIVE, gpuTypes[i], 1)) {
                currentGpu.setGpuType(openCl);
            } else if (RegexPatternMatcher.patternMatch("(Intel)", CASE_INSENSITIVE, gpuTypes[i], 1)) {
                currentGpu.setGpuType(openCl);
            } else {
                logger.error("Gpu type could not be determined, defaulting to Nvidia");
                currentGpu.setGpuType(cuda);
            }
            currentGpu.setMemorySize(Long.parseLong(gpuMemorySize[i]));
            result.add(currentGpu);
        }
        return result;
    }

    // TODO: remove duplication
    private String commandGetDescription() {
        OS os = new OS();
        OSType osType = os.getOsType();
        if(osType == mac) {
            logger.error("TODO: implement");
            return null;
        } else if(osType == linux) {
            logger.error("TODO: implement");
            return null;
        } else if(osType == windows) {
            return "(Get-WmiObject Win32_VideoController).description"; // //TODO: Use maybe (Get-WmiObject Win32_VideoController) only and then regex for more efficiency
        } else {
            logger.info("Could not determine the os type");
            return null;
        }
    }

    private String commandGetAdapterRAM() {
        OS os = new OS();
        OSType osType = os.getOsType();
        if(osType == mac) {
            logger.error("TODO: implement");
            return null;
        } else if(osType == linux) {
            logger.error("TODO: implement");
            return null;
        } else if(osType == windows) {
            return "(Get-WmiObject Win32_VideoController).AdapterRAM";
        } else {
            logger.info("Could not determine the os type");
            return null;
        }
    }
}
