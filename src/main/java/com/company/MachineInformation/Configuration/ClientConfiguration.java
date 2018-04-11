package com.company.MachineInformation.Configuration;

import com.company.MachineInformation.Configuration.GPU.GPUs;
import com.company.MachineInformation.Configuration.OS.OS;
import com.company.MachineInformation.Configuration.GPU.GPU;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientConfiguration {
    private OS os;
    private GPUs gpus;
}
