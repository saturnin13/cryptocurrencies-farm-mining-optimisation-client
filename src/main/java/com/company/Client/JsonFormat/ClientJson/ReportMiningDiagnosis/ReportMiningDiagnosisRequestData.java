package com.company.Client.JsonFormat.ClientJson.ReportMiningDiagnosis;

import com.company.Client.JsonFormat.General.GPU.GPU;
import com.company.Client.JsonFormat.General.MinedCurrencyShortName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportMiningDiagnosisRequestData {
    private GPU gpu;
    private MinedCurrencyShortName currency;
    private float hashRate;
}
