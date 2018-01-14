package com.company.Miners;

import com.company.Miners.MiningAlgorithm.ASCIMining.SHA256Currencies.Bitcoin;
import com.company.Miners.MiningAlgorithm.GpuMining.EthashCurrencies.Ethereum;
import com.company.Miners.MiningAlgorithm.GpuMining.EthashCurrencies.EthereumClassic;
import org.apache.log4j.Logger;

public class MinersFactory {

    private final static Logger logger = Logger.getLogger(MinersFactory.class);

    public static Miner getMiner(MinedCurrencyShortName currencyShortName) {
        if (currencyShortName == null) {
            logger.warn("Empty request content defaulting to Ethereum");
            return Ethereum.getInstance();
        }

        switch (currencyShortName.toString()) {
            case "ETH": return Ethereum.getInstance();
            case "ETC": return EthereumClassic.getInstance();
            case "BTC": return Bitcoin.getInstance();
            default: logger.warn("Could not find the miner:" + currencyShortName + ", defaulting to ethereum miner");
            return Ethereum.getInstance();
        }
    }
}
