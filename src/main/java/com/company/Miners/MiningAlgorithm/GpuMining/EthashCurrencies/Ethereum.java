package com.company.Miners.MiningAlgorithm.GpuMining.EthashCurrencies;

import org.apache.log4j.Logger;

import static com.company.Miners.MinedCurrencyShortName.ETH;
import static com.company.Variables.GIGA;

public class Ethereum extends Ethash {

    private final static Logger logger = Logger.getLogger(Ethereum.class);

    //TODO: add a stratum proxy option
    public Ethereum() {
        minedCurrencyShortName = ETH;
        poolAddressProtocol1 = "eu1.ethermine.org";
        poolPortProtocol1 = 4444;
        poolAddressProtocol2 = "http://eth-eu.dwarfpool.com";
        poolPortProtocol2 = 80;
        necessaryDagSize = (long) (2.49 * GIGA);
    }
}
