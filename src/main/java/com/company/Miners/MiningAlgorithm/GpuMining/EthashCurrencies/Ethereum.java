package com.company.Miners.MiningAlgorithm.GpuMining.EthashCurrencies;

import org.apache.log4j.Logger;

import static com.company.Miners.MinedCurrencyShortName.ETH;

public class Ethereum extends Ethash {

    private final static Logger logger = Logger.getLogger(Ethereum.class);

    //TODO: add a stratum proxy option
    public Ethereum() {
        minedCurrencyShortName = ETH;
        poolAddress = "http://eth-eu.dwarfpool.com:80";
    }
}
