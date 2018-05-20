package com.company.Miners.MiningAlgorithm.GpuMining.CryptoNightV7Currencies;

import org.apache.log4j.Logger;

import static com.company.Client.JsonFormat.General.MinedCurrencyShortName.XMR;

public class Monero extends CryptoNightV7 {
    private final static Logger logger = Logger.getLogger(Monero.class);

    //TODO: add a stratum proxy option
    public Monero() {
        minedCurrencyShortName = XMR;
        poolAddressProtocol1 = "pool.monero.hashvault.pro";
        poolPortProtocol1 = 3333;
    }
}
