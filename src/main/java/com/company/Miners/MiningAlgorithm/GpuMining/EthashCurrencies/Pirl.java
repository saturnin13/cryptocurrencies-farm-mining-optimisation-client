package com.company.Miners.MiningAlgorithm.GpuMining.EthashCurrencies;

import org.apache.log4j.Logger;

import static com.company.Miners.MinedCurrencyShortName.PIRL;
import static com.company.Variables.GIGA;

public class Pirl extends Ethash {

    private final static Logger logger = Logger.getLogger(Pirl.class);

    public Pirl() {
        minedCurrencyShortName = PIRL;
        poolAddressProtocol1 = "lb.geo.pirlpool.eu";
        poolPortProtocol1 = 8002;
        poolAddressProtocol2 = "http://lb.geo.pirlpool.eu";
        poolPortProtocol2 = 8822;
        necessaryDagSize = (long) (1.00 * GIGA); // TODO: could not find value online so it is estimation
    }
}
