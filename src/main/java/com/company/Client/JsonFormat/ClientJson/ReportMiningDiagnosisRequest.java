package com.company.Client.JsonFormat.ClientJson;

import com.company.Client.JsonFormat.ClientJson.ReportMiningDiagnosis.ReportMiningDiagnosisRequestData;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportMiningDiagnosisRequest {
    private String userEmail;
    private String workerName;
    private ReportMiningDiagnosisRequestData data;
}
