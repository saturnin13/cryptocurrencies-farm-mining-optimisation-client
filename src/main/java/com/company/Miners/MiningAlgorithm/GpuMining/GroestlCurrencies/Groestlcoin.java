package com.company.Miners.MiningAlgorithm.GpuMining.GroestlCurrencies;

import org.apache.log4j.Logger;

import static com.company.Client.JsonFormat.General.MinedCurrencyShortName.GRS;

public class Groestlcoin extends Groestl {
    private final static Logger logger = Logger.getLogger(Groestlcoin.class);

    public Groestlcoin() {
        minedCurrencyShortName = GRS;
        poolAddressProtocol1 = "stratum+tcp://erebor.dwarfpool.com";
        poolPortProtocol1 = 3345;
    }
}
