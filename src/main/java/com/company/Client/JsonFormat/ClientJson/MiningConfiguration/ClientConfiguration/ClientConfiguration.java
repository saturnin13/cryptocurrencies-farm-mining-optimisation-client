package com.company.Client.JsonFormat.ClientJson.MiningConfiguration.ClientConfiguration;

import com.company.Client.JsonFormat.General.GPU.GPU;
import com.company.Client.JsonFormat.ClientJson.MiningConfiguration.ClientConfiguration.OS.OS;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ClientConfiguration {
    private OS os;
    private List<GPU> gpus;
}
