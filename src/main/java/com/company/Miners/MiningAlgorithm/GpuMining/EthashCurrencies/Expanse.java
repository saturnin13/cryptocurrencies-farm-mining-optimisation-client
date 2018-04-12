package com.company.Miners.MiningAlgorithm.GpuMining.EthashCurrencies;

import org.apache.log4j.Logger;

import static com.company.Miners.MinedCurrencyShortName.EXP;
import static com.company.Variables.GIGA;

public class Expanse extends Ethash {

    private final static Logger logger = Logger.getLogger(Expanse.class);

    public Expanse() {
        minedCurrencyShortName = EXP;
        poolAddressProtocol1 = "eu.expmine.pro";
        poolPortProtocol1 = 9009;
        poolAddressProtocol2 = "http://pool.expanse.tech";
        poolPortProtocol2 = 8888;
        necessaryDagSize = (long) (1.28 * GIGA);
    }
}
