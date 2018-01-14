package com.company.MachineInformation.Configuration;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientConfiguration {
    private OS os;
}
