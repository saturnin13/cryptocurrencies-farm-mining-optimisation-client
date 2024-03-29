package com.company.Client.JsonFormat.General.GPU;

import lombok.Data;

@Data
public class GPU {
    private GPUType gpuType;
    private long memorySize;
    private GraphicCard graphicCard;
    private String graphicCardName;
    private int id;
    private int cudaId;
}
