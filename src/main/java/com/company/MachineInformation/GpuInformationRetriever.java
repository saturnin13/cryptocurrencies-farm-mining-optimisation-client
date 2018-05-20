package com.company.MachineInformation;

import com.company.Client.JsonFormat.ClientJson.MiningConfiguration.ClientConfiguration.OS.OS;
import com.company.Client.JsonFormat.ClientJson.MiningConfiguration.ClientConfiguration.OS.OSType;
import com.company.Client.JsonFormat.General.GPU.GPU;
import com.company.Client.JsonFormat.General.GPU.GPUType;
import com.company.Client.JsonFormat.General.GPU.GraphicCard;
import com.company.CommandsExecutor.CommandExecutor;
import com.company.Util.RegexPatternMatcher;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

public class GpuInformationRetriever {

    private final static Logger logger = Logger.getLogger(GpuInformationRetriever.class);


    public static List<GPU> retrieveGpus(OS os) {
        List<GPU> result = new LinkedList<>();

        String[] gpuDescription = CommandExecutor.builder().commands(Arrays.asList((commandGetDescription(os.getOsType()))))
                .build()
                .runAndReturn()
                .split("\\r?\\n");
        String[] gpuMemorySize = CommandExecutor.builder().commands(Arrays.asList((commandGetAdapterRAM(os.getOsType()))))
                .build()
                .runAndReturn()
                .split("\\r?\\n");

        int numberOfCuda = 0;

        for (int i = 0; i < gpuDescription.length; i++) {
            GPU currentGpu = new GPU();
            if (RegexPatternMatcher.patternMatch("(NVIDIA)", CASE_INSENSITIVE, gpuDescription[i])) {
                currentGpu.setGpuType(GPUType.CUDA);
                currentGpu.setCudaId(numberOfCuda);
                numberOfCuda++;
            } else if (RegexPatternMatcher.patternMatch("(AMD)", CASE_INSENSITIVE, gpuDescription[i])) {
                currentGpu.setGpuType(GPUType.OPEN_CL);
            } else if (RegexPatternMatcher.patternMatch("(Intel)", CASE_INSENSITIVE, gpuDescription[i])) {
                continue;
            } else {
                logger.error("Gpu type could not be determined");
                continue;
            }
            currentGpu.setMemorySize(Long.parseLong(gpuMemorySize[i]));
            currentGpu.setGraphicCard(determineGraphicCard(gpuDescription[i]));
            currentGpu.setId(i);
            result.add(currentGpu);
        }
        return result;
    }

    private static GraphicCard determineGraphicCard(String gpuModel) {
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
    private static String commandGetDescription(OSType osType) {
        if(osType == OSType.MAC) {
            logger.error("TODO: implement");
            return null;
        } else if(osType == OSType.LINUX) {
            logger.error("TODO: implement");
            return null;
        } else if(osType == OSType.WINDOWS) {
            return "(Get-WmiObject Win32_VideoController).description"; // //TODO: Use maybe (Get-WmiObject Win32_VideoController) only and then regex for more efficiency
        } else {
            logger.info("Could not determine the os type");
            return null;
        }
    }

    private static String commandGetAdapterRAM(OSType osType) {
        if(osType == OSType.MAC) {
            logger.error("TODO: implement");
            return null;
        } else if(osType == OSType.LINUX) {
            logger.error("TODO: implement");
            return null;
        } else if(osType == OSType.WINDOWS) {
            return "(Get-WmiObject Win32_VideoController).AdapterRAM";
        } else {
            logger.info("Could not determine the os type");
            return null;
        }
    }
}
