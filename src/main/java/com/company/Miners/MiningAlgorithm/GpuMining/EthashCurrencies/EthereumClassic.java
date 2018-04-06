package com.company.Miners.MiningAlgorithm.GpuMining.EthashCurrencies;

import org.apache.log4j.Logger;

import static com.company.Miners.MinedCurrencyShortName.ETC;

public class EthereumClassic extends Ethash {

    private final static Logger logger = Logger.getLogger(EthereumClassic.class);

    public EthereumClassic() {
        minedCurrencyShortName = ETC;
        poolAddress = "http://etc.poolto.be:8746";
    }
}
