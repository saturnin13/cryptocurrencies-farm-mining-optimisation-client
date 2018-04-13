package com.company.Miners.MiningAlgorithm.GpuMining.GroestlCurrencies;

import org.apache.log4j.Logger;

import java.util.List;

import static com.company.Miners.MinedCurrencyShortName.GRS;

public class Groestlcoin extends Groestl {
    private final static Logger logger = Logger.getLogger(Groestlcoin.class);

    public Groestlcoin() {
        minedCurrencyShortName = GRS;
        poolAddressProtocol1 = "stratum+tcp://erebor.dwarfpool.com";
        poolPortProtocol1 = 3345;
    }
}
