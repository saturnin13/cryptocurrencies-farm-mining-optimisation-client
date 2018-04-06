package com.company.Timeout;

import com.company.Miners.MinedCurrencyShortName;

import java.util.concurrent.TimeUnit;

import static com.company.Variables.MINING_TIMEOUT_MILLIS;

public class TimeoutManager {
    public static void deactivatedMiningTimeout() {
        mainTimeout();
    }

    public static long timeout(MinedCurrencyShortName minedCurrencyShortName) {
        switch (minedCurrencyShortName) {
            case ETC: return MINING_TIMEOUT_MILLIS;
            default: return MINING_TIMEOUT_MILLIS;
        }
    }

    private static void mainTimeout() {
        try {
            TimeUnit.MILLISECONDS.sleep(MINING_TIMEOUT_MILLIS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
