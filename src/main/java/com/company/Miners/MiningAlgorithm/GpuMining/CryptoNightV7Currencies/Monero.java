package com.company.Miners.MiningAlgorithm.GpuMining.CryptoNightV7Currencies;

import com.company.CommandExecutor.CommandOutputMonitoring.CommandOutputMonitor;
import com.company.Miners.MiningAlgorithm.GpuMining.EthashCurrencies.EthMinerOutputMonitor;
import com.company.Miners.MiningAlgorithm.GpuMining.EthashCurrencies.Ethereum;
import com.google.common.collect.ImmutableList;
import org.apache.log4j.Logger;

import java.util.List;

import static com.company.Miners.MinedCurrencyShortName.ETH;
import static com.company.Miners.MinedCurrencyShortName.XMR;
import static com.company.Variables.*;

public class Monero extends CryptoNightV7 {
    private final static Logger logger = Logger.getLogger(Monero.class);

    //TODO: add a stratum proxy option
    public Monero() {
        minedCurrencyShortName = XMR;
        poolAddress = "pool.monero.hashvault.pro:3333";
    }
}
