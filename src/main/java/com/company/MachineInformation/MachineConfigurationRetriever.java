package com.company.MachineInformation;

import com.company.MachineInformation.Configuration.ClientConfiguration;
import com.company.MachineInformation.Configuration.OS;

public class MachineConfigurationRetriever {

    public static ClientConfiguration getMachineCharacteristics() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
            .os(new OS())
            .build();

        return clientConfiguration;
    }
}
