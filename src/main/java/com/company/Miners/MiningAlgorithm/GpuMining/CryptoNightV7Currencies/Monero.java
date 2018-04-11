package com.company.Miners.MiningAlgorithm.GpuMining.CryptoNightV7Currencies;

import org.apache.log4j.Logger;

import static com.company.Miners.MinedCurrencyShortName.XMR;

public class Monero extends CryptoNightV7 {
    private final static Logger logger = Logger.getLogger(Monero.class);

    //TODO: add a stratum proxy option
    public Monero() {
        minedCurrencyShortName = XMR;
        poolAddress = "pool.monero.hashvault.pro:3333";
    }
}
