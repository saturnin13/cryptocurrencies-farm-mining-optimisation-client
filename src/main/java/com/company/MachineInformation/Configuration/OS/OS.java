package com.company.MachineInformation.Configuration.OS;

import com.company.CommandsExecutor.CommandExecutionEnvironment;
import com.company.Main;
import com.company.Util.RegexPatternMatcher;
import lombok.Data;
import org.apache.log4j.Logger;

import static com.company.CommandsExecutor.CommandExecutionEnvironment.BASH;
import static com.company.CommandsExecutor.CommandExecutionEnvironment.POWERSHELL;
import static com.company.MachineInformation.Configuration.OS.OSType.linux;
import static com.company.MachineInformation.Configuration.OS.OSType.mac;
import static com.company.MachineInformation.Configuration.OS.OSType.windows;

@Data
public class OS {
    private OSType osType;
    private DataModel dataModel;
    private String version;
    private CommandExecutionEnvironment environment;

    private final static Logger logger = Logger.getLogger(OS.class);

    public OS() {
        this.osType = findType();
        this.dataModel = findDataModel();
        this.version = findVersion();
        this.environment = findEnvironment();
    }

    private String findVersion() {
        return System.getProperty("os.version");
    }

    private DataModel findDataModel() {
        String model = System.getProperty("sun.arch.data.model");
        if(RegexPatternMatcher.patternMatch("(32)", model, 1)) {
            return DataModel.x32;
        } else if(RegexPatternMatcher.patternMatch("(64)", model, 1)) {
            return DataModel.x64;
        } else {
            logger.error("Data model could not be determined, defaulting to x64");
            return DataModel.x64;
        }
    }

    private OSType findType() {
        String name = System.getProperty("os.name");
        if(RegexPatternMatcher.patternMatch("(Windows)", name, 1)) {
            return OSType.windows;
        } else if(RegexPatternMatcher.patternMatch("(Mac)", name, 1)) {
            return OSType.mac;
        } else if(RegexPatternMatcher.patternMatch("(Linux)", name, 1)) {
            return OSType.linux;
        } else {
            logger.error("Os type could not be determined, defaulting to Linux");
            return OSType.linux;
        }
    }

    private CommandExecutionEnvironment findEnvironment() {
        if(osType == mac) {
            return BASH;
        } else if(osType == linux) {
            return BASH;
        } else if(osType == windows) {
            return POWERSHELL;
        } else {
            logger.error("Environment could not be determined the , defaulting to Bash");
            return BASH;
        }
    }
}
