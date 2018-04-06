package com.company.Miners.MiningAlgorithm.GpuMining.EthashCurrencies;

import org.apache.log4j.Logger;

import static com.company.Miners.MinedCurrencyShortName.EXP;

public class Expanse extends Ethash {

    private final static Logger logger = Logger.getLogger(Expanse.class);

    public Expanse() {
        minedCurrencyShortName = EXP;
        poolAddress = "http://pool.expanse.tech:8888";
    }
}
