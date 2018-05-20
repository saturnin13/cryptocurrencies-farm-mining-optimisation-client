package com.company.MachineInformation;

import com.company.Client.JsonFormat.ClientJson.MiningConfiguration.ClientConfiguration.ClientConfiguration;
import com.company.Client.JsonFormat.ClientJson.MiningConfiguration.ClientConfiguration.OS.OS;
import org.apache.log4j.Logger;

public class MachineConfigurationRetriever {

    private final static Logger logger = Logger.getLogger(MachineConfigurationRetriever.class);

    private static ClientConfiguration clientConfiguration;

    public static ClientConfiguration getMachineCharacteristics() {
        if(clientConfiguration == null) {
            logger.info("Gathering client configurations");
            OS os = OSInformationRetriever.retrieveOs();
            clientConfiguration = ClientConfiguration.builder()
                    .os(os)
                    .gpus(GpuInformationRetriever.retrieveGpus(os))
                    .build();
        }

        return clientConfiguration;
    }
}
