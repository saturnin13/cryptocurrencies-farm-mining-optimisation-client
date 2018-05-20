package com.company.CommandsExecutor.CommandOutputMonitoring;

import com.company.Client.HttpRequestHandling;
import com.company.Client.JsonFormat.ClientJson.ReportMiningDiagnosis.ReportMiningDiagnosisRequestData;
import com.company.Client.JsonFormat.General.GPU.GPU;
import com.company.Client.JsonFormat.General.MinedCurrencyShortName;
import com.company.Util.RegexPatternMatcher;

import static com.company.Variables.*;
import static java.util.regex.Pattern.CASE_INSENSITIVE;

public abstract class MiningCommandOutputMonitor implements CommandOutputMonitor {
    protected long lastUpdateTime;
    protected MinedCurrencyShortName currencyShortName;
    protected GPU gpu;

    public MiningCommandOutputMonitor(MinedCurrencyShortName currencyShortName, GPU gpu) {
        this.lastUpdateTime = 0;
        this.currencyShortName = currencyShortName;
        this.gpu = gpu;
    }

    @Override
    public void monitorOutput(String line, String previousLine) {
        if (System.currentTimeMillis() - lastUpdateTime < HASHRATE_REPORTING_RATE) {
            return;
        }
        Float hashrate = getHashrate(line) != null ? getHashrate(line): getHashrate(previousLine);
        hashrate = hashrate == null ? 0: hashrate;
        HttpRequestHandling httpRequestHandling = new HttpRequestHandling();

        httpRequestHandling.reportMiningDiagnosis(ReportMiningDiagnosisRequestData.builder()
                .currency(currencyShortName)
                .hashRate(hashrate)
                .gpu(gpu)
                .build());
        lastUpdateTime = System.currentTimeMillis();
    }

    // Get the second brace () from the regex so the implementation of this needs to have the value located in the second brace
    private Float getHashrate(String line) {
        Float pattern = RegexPatternMatcher.findFloatPatternMatch(getHashrateRegex(), CASE_INSENSITIVE, line, 2);
        return pattern == null ? null: pattern * getScaling(line);
    }

    protected String getHashrateUnitRegex() {
        return "(|k|m|g|t)h( )?\\/( )?s";
    }

    protected abstract String getHashrateRegex();

    private Float getScaling(String line) {
        if(RegexPatternMatcher.patternMatch("( )h( )?/( )?s", CASE_INSENSITIVE, line)) {
            return 1F;
        } else if(RegexPatternMatcher.patternMatch("kh( )?/( )?s", CASE_INSENSITIVE, line)) {
            return KILO;
        } else if(RegexPatternMatcher.patternMatch("mh( )?/( )?s", CASE_INSENSITIVE, line)) {
            return MEGA;
        } else if(RegexPatternMatcher.patternMatch("gh( )?/( )?s", CASE_INSENSITIVE, line)) {
            return GIGA;
        } else if(RegexPatternMatcher.patternMatch("th( )?/( )?s", CASE_INSENSITIVE, line)) {
            return TERA;
        } else {
            return 0F;
        }
    }
}
