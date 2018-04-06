package com.company.Miners;

import com.company.Miners.MiningAlgorithm.ASCIMining.SHA256Currencies.Bitcoin;
import com.company.Miners.MiningAlgorithm.GpuMining.EthashCurrencies.*;
import org.apache.log4j.Logger;

public class MinersFactory {

    private final static Logger logger = Logger.getLogger(MinersFactory.class);

    public static Miner getMiner(MinedCurrencyShortName currencyShortName) {
        if (currencyShortName == null) {
            logger.warn("Empty request content defaulting to Ethereum");
            return new Ethereum();
        }

        switch (currencyShortName.toString()) {
            case "ETH"  : return new Ethereum();
            case "ETC"  : return new EthereumClassic();
            case "BTC"  : return new Bitcoin();
            case "EXP"  : return new Expanse();
            case "MUSIC": return new MusicCoin();
            case "PIRL" : return new Pirl();
            case "UBQ"  : return new Ubiq();
            default: logger.error("Could not find the miner:" + currencyShortName + ", defaulting to ethereum miner");
            return new Ethereum();
        }
    }
}
