package com.company.Miners.MiningAlgorithm.GpuMining.EthashCurrencies;

import org.apache.log4j.Logger;

import static com.company.Miners.MinedCurrencyShortName.UBQ;

public class Ubiq extends Ethash {

    private final static Logger logger = Logger.getLogger(Ubiq.class);

    public Ubiq() {
        minedCurrencyShortName = UBQ;
        poolAddress = "http://lb.geo.ubiqpool.org:8881";
    }
}
