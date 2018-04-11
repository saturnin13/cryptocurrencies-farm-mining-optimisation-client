package com.company.MachineInformation.Configuration;

import com.company.MachineInformation.Configuration.OS.OS;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientConfiguration {
    private OS os;
}
