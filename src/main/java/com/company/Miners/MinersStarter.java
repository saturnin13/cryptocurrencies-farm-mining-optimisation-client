package com.company.Miners;

public class MinersStarter {
    private static Miner currentMiner = null;

    public static void startMiner(MinedCurrencyShortName currencyShortName) {
        if(currentMiner != null) {
            currentMiner.stopMining();
        }
        currentMiner = MinersFactory.getMiner(currencyShortName);
        currentMiner.startMining();
    }
}
