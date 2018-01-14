package com.company.MachineInformation.Configuration;

import com.company.Main;
import lombok.Builder;
import lombok.Data;
import org.apache.log4j.Logger;

@Data
public class OS {
    OSType osType;
    DataModel dataModel;
    String version;

    private final static Logger logger = Logger.getLogger(Main.class);

    public OS() {
        osType = findType();
        dataModel = findDataModel();
        version = findVersion();
    }

    private String findVersion() {
        return System.getProperty("os.version");
    }

    private DataModel findDataModel() {
        String model = System.getProperty("sun.arch.data.model");
        if(model.contains("32")) {
            return DataModel.x32;
        } else if(model.contains("64")) {
            return DataModel.x64;
        } else {
            logger.error("Data model could not be determine, defaulting to x64");
            return DataModel.x64;
        }
    }

    private OSType findType() {
        String name = System.getProperty("os.name");
        if(name.contains("Windows")) {
            return OSType.windows;
        } else if(name.contains("Mac")) {
            return OSType.mac;
        } else if(name.contains("Linux")) {
            return OSType.linux;
        } else {
            logger.error("Os type could not be determine, defaulting to Linux");
            return OSType.linux;
        }
    }
}
