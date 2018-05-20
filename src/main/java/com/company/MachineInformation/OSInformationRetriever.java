package com.company.MachineInformation;

import com.company.Client.JsonFormat.ClientJson.MiningConfiguration.ClientConfiguration.OS.CommandExecutionEnvironment;
import com.company.Client.JsonFormat.ClientJson.MiningConfiguration.ClientConfiguration.OS.DataModel;
import com.company.Client.JsonFormat.ClientJson.MiningConfiguration.ClientConfiguration.OS.OS;
import com.company.Client.JsonFormat.ClientJson.MiningConfiguration.ClientConfiguration.OS.OSType;
import com.company.Util.RegexPatternMatcher;
import org.apache.log4j.Logger;

import static com.company.Client.JsonFormat.ClientJson.MiningConfiguration.ClientConfiguration.OS.CommandExecutionEnvironment.BASH;
import static com.company.Client.JsonFormat.ClientJson.MiningConfiguration.ClientConfiguration.OS.CommandExecutionEnvironment.POWERSHELL;
import static com.company.Client.JsonFormat.ClientJson.MiningConfiguration.ClientConfiguration.OS.OSType.LINUX;
import static com.company.Client.JsonFormat.ClientJson.MiningConfiguration.ClientConfiguration.OS.OSType.MAC;
import static com.company.Client.JsonFormat.ClientJson.MiningConfiguration.ClientConfiguration.OS.OSType.WINDOWS;

public class OSInformationRetriever {
    private final static Logger logger = Logger.getLogger(OSInformationRetriever.class);

    public static OS retrieveOs() {
        OSType osType = retrieveType();
        return OS.builder()
                .osType(osType)
                .dataModel(findDataModel())
                .version(findVersion())
                .environment(findEnvironment(osType))
                .build();
    }

    private static String findVersion() {
        return System.getProperty("os.version");
    }

    private static DataModel findDataModel() {
        String model = System.getProperty("sun.arch.data.model");
        if(RegexPatternMatcher.patternMatch("(32)", model)) {
            return DataModel.x32;
        } else if(RegexPatternMatcher.patternMatch("(64)", model)) {
            return DataModel.x64;
        } else {
            logger.error("Data model could not be determined, defaulting to x64");
            return DataModel.x64;
        }
    }

    private static OSType retrieveType() {
        String name = System.getProperty("os.name");
        if(RegexPatternMatcher.patternMatch("(Windows)", name)) {
            return OSType.WINDOWS;
        } else if(RegexPatternMatcher.patternMatch("(Mac)", name)) {
            return OSType.MAC;
        } else if(RegexPatternMatcher.patternMatch("(Linux)", name)) {
            return OSType.LINUX;
        } else {
            logger.error("Os type could not be determined, defaulting to Linux");
            return OSType.LINUX;
        }
    }

    private static CommandExecutionEnvironment findEnvironment(OSType osType) {
        if(osType == MAC) {
            return BASH;
        } else if(osType == LINUX) {
            return BASH;
        } else if(osType == WINDOWS) {
            return POWERSHELL;
        } else {
            logger.error("Environment could not be determined the , defaulting to Bash");
            return BASH;
        }
    }
}
