package com.company.Miners.MinerManagment;

import com.company.Miners.MinedCurrencyShortName;
import com.company.Miners.Miner;
import com.company.Miners.MiningAlgorithm.ASCIMining.SHA256Currencies.Bitcoin;
import com.company.Miners.MiningAlgorithm.GpuMining.CryptoNightV7Currencies.Monero;
import com.company.Miners.MiningAlgorithm.GpuMining.EthashCurrencies.*;
import com.company.Miners.MiningAlgorithm.GpuMining.GroestlCurrencies.Groestl;
import com.company.Miners.MiningAlgorithm.GpuMining.GroestlCurrencies.Groestlcoin;
import org.apache.log4j.Logger;

public class MinersFactory {

    private final static Logger logger = Logger.getLogger(MinersFactory.class);

    private static Miner ethereum = new Ethereum();
    private static Miner ethereumClassic = new EthereumClassic();
    private static Miner bitcoin = new Bitcoin();
    private static Miner expanse = new Expanse();
    private static Miner musicCoin = new MusicCoin();
    private static Miner pirl = new Pirl();
    private static Miner ubiq = new Ubiq();
    private static Miner monero = new Monero();
    private static Miner groestlcoin = new Groestlcoin();

    public static Miner getMiner(MinedCurrencyShortName currencyShortName) {
        if (currencyShortName == null) {
            logger.warn("Empty request content defaulting to Ethereum");
            return new Ethereum();
        }

        switch (currencyShortName) {
            case ETH  : return ethereum;
            case ETC  : return ethereumClassic;
            case BTC  : return bitcoin;
            case EXP  : return expanse;
            case MUSIC: return musicCoin;
            case PIRL : return pirl;
            case UBQ  : return ubiq;
            case XMR  : return monero;
            case GRS  : return groestlcoin;
            default: logger.error("Could not find the miner object: " + currencyShortName + ", defaulting to ethereum miner");
            return new Ethereum();
        }
    }
}
