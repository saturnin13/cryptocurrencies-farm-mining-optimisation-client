package com.company.MachineInformation;

import com.company.MachineInformation.Configuration.ClientConfiguration;
import com.company.MachineInformation.Configuration.GPU.GPUs;
import com.company.MachineInformation.Configuration.OS.OS;
import org.apache.log4j.Logger;

public class MachineConfigurationRetriever {

    private final static Logger logger = Logger.getLogger(MachineConfigurationRetriever.class);

    private static ClientConfiguration clientConfiguration;

    public static ClientConfiguration getMachineCharacteristics() {
        if(clientConfiguration == null) {
            logger.info("Gathering client configurations");
            clientConfiguration = ClientConfiguration.builder()
                    .os(new OS())
                    .gpus(new GPUs())
                    .build();
        }

        return clientConfiguration;
    }
}
