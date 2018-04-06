package com.company.Miners.MiningAlgorithm.GpuMining.EthashCurrencies;

import org.apache.log4j.Logger;

import static com.company.Miners.MinedCurrencyShortName.PIRL;

public class Pirl extends Ethash {

    private final static Logger logger = Logger.getLogger(Pirl.class);

    public Pirl() {
        minedCurrencyShortName = PIRL;
        poolAddress = "http://lb.geo.pirlpool.eu:8822";
    }
}
