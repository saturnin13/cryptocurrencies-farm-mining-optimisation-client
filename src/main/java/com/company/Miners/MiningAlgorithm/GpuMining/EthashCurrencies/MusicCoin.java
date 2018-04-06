package com.company.Miners.MiningAlgorithm.GpuMining.EthashCurrencies;

import org.apache.log4j.Logger;

import static com.company.Miners.MinedCurrencyShortName.MUSIC;

public class MusicCoin extends Ethash {

    private final static Logger logger = Logger.getLogger(MusicCoin.class);

    public MusicCoin() {
        minedCurrencyShortName = MUSIC;
        poolAddress = "http://mc.minecrypto.pro:7777";
    }
}
