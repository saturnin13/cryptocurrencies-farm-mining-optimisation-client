package com.company.Client.JsonFormat.ClientJson.MiningConfiguration;

import com.company.Client.JsonFormat.ClientJson.MiningConfiguration.ClientConfiguration.ClientConfiguration;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MiningConfigurationRequestData {
    private ClientConfiguration clientConfiguration;
}
