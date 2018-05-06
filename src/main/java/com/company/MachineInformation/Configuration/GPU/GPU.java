package com.company.MachineInformation.Configuration.GPU;

import lombok.Data;

@Data
public class GPU {
    private GPUType gpuType;
    private long memorySize;
}
