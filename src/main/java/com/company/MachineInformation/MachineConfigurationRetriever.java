package com.company.MachineInformation;

import com.company.MachineInformation.Configuration.ClientConfiguration;
import com.company.MachineInformation.Configuration.OS.OS;

public class MachineConfigurationRetriever {

    private static ClientConfiguration clientConfiguration;

    public static ClientConfiguration getMachineCharacteristics() {
        if(clientConfiguration == null) {
            clientConfiguration = ClientConfiguration.builder()
                    .os(new OS())
                    .build();
        }

        return clientConfiguration;
    }
}
