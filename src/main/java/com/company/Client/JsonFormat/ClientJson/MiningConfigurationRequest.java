package com.company.Client.JsonFormat.ClientJson;


import com.company.Client.JsonFormat.ClientJson.MiningConfiguration.MiningConfigurationRequestData;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MiningConfigurationRequest {
    private String userEmail;
    private String workerName;
    private MiningConfigurationRequestData data;
}
