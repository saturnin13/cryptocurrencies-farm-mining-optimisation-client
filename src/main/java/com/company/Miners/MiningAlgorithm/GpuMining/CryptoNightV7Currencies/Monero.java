package com.company.Miners.MiningAlgorithm.GpuMining.CryptoNightV7Currencies;

import com.company.MachineInformation.Configuration.ClientConfiguration;
import org.apache.log4j.Logger;

import static com.company.Miners.MinedCurrencyShortName.XMR;

public class Monero extends CryptoNightV7 {
    private final static Logger logger = Logger.getLogger(Monero.class);

    //TODO: add a stratum proxy option
    public Monero() {
        minedCurrencyShortName = XMR;
        poolAddressProtocol1 = "pool.monero.hashvault.pro";
        poolPortProtocol1 = 3333;
    }

    @Override
    public boolean canMineOnMachine(ClientConfiguration clientConfiguration) {
        return true; // TODO: look into to implement
    }
}
