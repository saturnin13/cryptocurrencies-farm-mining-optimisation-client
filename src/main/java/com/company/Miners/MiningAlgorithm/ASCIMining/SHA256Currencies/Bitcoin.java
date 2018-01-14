package com.company.Miners.MiningAlgorithm.ASCIMining.SHA256Currencies;

import com.company.Miners.MiningAlgorithm.ASCIMining.SHA256;
import org.apache.log4j.Logger;

import static com.company.Miners.MinedCurrencyShortName.BTC;

public class Bitcoin extends SHA256 {

    private final static Logger logger = Logger.getLogger(Bitcoin.class);
    private static final Bitcoin instance = new Bitcoin();

    private Bitcoin() {
        minedCurrencyShortName = BTC;
    }

    public static Bitcoin getInstance(){
        return instance;
    }

    @Override
    public void startMining() {
        logger.info("Bitcoin starting to mine");

    }

    @Override
    protected void startMiningWindows() {

    }

    @Override
    protected void startMiningLinux() {

    }

    @Override
    protected void startMiningMac() {

    }

    @Override
    public boolean install() {
        return false;
    }

    @Override
    protected void installWindows() {

    }

    @Override
    protected void installLinux() {

    }

    @Override
    protected void installMac() {

    }
}
