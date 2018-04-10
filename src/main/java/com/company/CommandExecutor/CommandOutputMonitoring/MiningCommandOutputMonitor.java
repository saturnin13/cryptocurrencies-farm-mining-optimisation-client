package com.company.CommandExecutor.CommandOutputMonitoring;

import com.company.Client.HttpRequestHandling;
import com.company.Miners.MinedCurrencyShortName;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.company.Variables.HASHRATE_UPDATING_RATE;

public abstract class MiningCommandOutputMonitor implements CommandOutputMonitor {
    protected long lastUpdateTime;
    protected MinedCurrencyShortName currencyShortName;
    private Float scaling;

    public MiningCommandOutputMonitor(MinedCurrencyShortName currencyShortName) {
        this.lastUpdateTime = 0;
        this.currencyShortName = currencyShortName;
    }

    @Override
    public void monitorOutput(String line, String previousLine) {
        if (System.currentTimeMillis() - lastUpdateTime < HASHRATE_UPDATING_RATE) {
            return;
        }
        Float hashrate = getHashrate(line) != null ? getHashrate(line): getHashrate(previousLine);
        if(hashrate != null) {
            HttpRequestHandling httpRequestHandling = new HttpRequestHandling();
            httpRequestHandling.reportMiningDiagnosis(currencyShortName, hashrate);
        }
        lastUpdateTime = System.currentTimeMillis();
    }

    // Get the second brace () from the regex so the implementation of this needs to have the value located in the second brace
    private Float getHashrate(String line) {
        if (line == null) {
            return null;
        }
        Pattern pattern = Pattern.compile(getHashrateRegex());
        Matcher matcher = pattern.matcher(line);
        Float result = null;

        if (matcher.find()) {
            try {
                result = Float.parseFloat(matcher.group(2));
                result = result * getScaling(); // On 2 lines to catch exceptions and avoid program crashing
            } catch (NumberFormatException e) {
            }
        }
        return result;
    }

    protected abstract String getHashrateRegex();
    protected abstract Float getScaling();
}
