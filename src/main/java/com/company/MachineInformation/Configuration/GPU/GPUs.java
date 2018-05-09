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

import static com.company.MachineInformation.Configuration.GPU.GPUType.OPEN_CL;
import static com.company.MachineInformation.Configuration.GPU.GPUType.CUDA;
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

        String[] gpuDescription = CommandExecutor.builder().commands(Arrays.asList((commandGetDescription())))
                .build()
                .runAndReturn()
                .split("\\r?\\n");
        String[] gpuMemorySize = CommandExecutor.builder().commands(Arrays.asList((commandGetAdapterRAM())))
                .build()
                .runAndReturn()
                .split("\\r?\\n");

        for (int i = 0; i < gpuDescription.length; i++) {
            GPU currentGpu = new GPU();
            if (RegexPatternMatcher.patternMatch("(NVIDIA)", CASE_INSENSITIVE, gpuDescription[i])) {
                currentGpu.setGpuType(CUDA);
            } else if (RegexPatternMatcher.patternMatch("(AMD)", CASE_INSENSITIVE, gpuDescription[i])) {
                currentGpu.setGpuType(OPEN_CL);
            } else if (RegexPatternMatcher.patternMatch("(Intel)", CASE_INSENSITIVE, gpuDescription[i])) {
                currentGpu.setGpuType(OPEN_CL);
            } else {
                logger.error("Gpu type could not be determined, defaulting to Nvidia");
                currentGpu.setGpuType(CUDA);
            }
            currentGpu.setMemorySize(Long.parseLong(gpuMemorySize[i]));
            currentGpu.setGraphicCard(determineGraphicCard(gpuDescription[i]));
            result.add(currentGpu);
        }
        return result;
    }

    private GraphicCard determineGraphicCard(String gpuModel) {
        List<GraphicCard> graphicCards = Arrays.asList(GraphicCard.values());
        graphicCards.sort((v1, v2) -> v2.toString().length() - v1.toString().length()); // To avoid GTX 1080 TI to be confused with GTX 1080
        for (GraphicCard graphicCard : GraphicCard.values()) {
            String regexGraphicCard = graphicCard.toString().replaceAll("_", ".*");
            if(RegexPatternMatcher.patternMatch("(" + regexGraphicCard + ")", CASE_INSENSITIVE, gpuModel)) {
                return graphicCard;
            }
        }
        return null;
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
