package com.company.CommandExecutor.CommandOutputMonitoring;

import com.company.Client.HttpRequestHandling;
import com.company.Miners.MinedCurrencyShortName;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.company.Variables.HASHRATE_UPDATING_RATE;

public abstract class MiningCommandOutputMonitor implements CommandOutputMonitor {
    protected long lastUpdateTime;
    protected MinedCurrencyShortName currencyShortName;
    private String hashrateRegex;

    public MiningCommandOutputMonitor(MinedCurrencyShortName currencyShortName) {
        this.lastUpdateTime = 0;
        this.currencyShortName = currencyShortName;
    }

    @Override
    public void monitorOutput(String line) {
        if (System.currentTimeMillis() - lastUpdateTime < HASHRATE_UPDATING_RATE) {
            return;
        }
        Float hashrate = getHashrate(line);
        if(hashrate != null) {
            HttpRequestHandling httpRequestHandling = new HttpRequestHandling();
            httpRequestHandling.reportMiningDiagnosis(currencyShortName, hashrate);
        }
        lastUpdateTime = System.currentTimeMillis();
    }

    private Float getHashrate(String line) {
        Pattern pattern = Pattern.compile(getHashrateRegex());
        Matcher matcher = pattern.matcher(line);
        Float result = null;

        if (matcher.find()) {
            try {
                result = Float.parseFloat(matcher.group(1));
            } catch (NumberFormatException e) {
            }
        }
        return result;
    }

    protected abstract String getHashrateRegex();
}
