package com.company.Miners.MiningAlgorithm.GpuMining.EthashCurrencies;

import org.apache.log4j.Logger;

import static com.company.Miners.MinedCurrencyShortName.UBQ;
import static com.company.Variables.GIGA;

public class Ubiq extends Ethash {

    private final static Logger logger = Logger.getLogger(Ubiq.class);

    public Ubiq() {
        minedCurrencyShortName = UBQ;
        poolAddressProtocol1 = "us.ubiqpool.io";
        poolPortProtocol1 = 8008;
        poolAddressProtocol2 = "http://lb.geo.ubiqpool.org";
        poolPortProtocol2 = 8881;
        necessaryDagSize = (long) (1.11 * GIGA);
    }

}
