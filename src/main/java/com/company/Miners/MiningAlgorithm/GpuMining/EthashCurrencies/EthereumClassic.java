package com.company.Miners.MiningAlgorithm.GpuMining.EthashCurrencies;

import com.company.Miners.MiningAlgorithm.GpuMining.Ethash;
import org.apache.log4j.Logger;

import static com.company.Miners.MinedCurrencyShortName.ETC;

public class EthereumClassic extends Ethash {

    private final static Logger logger = Logger.getLogger(EthereumClassic.class);
    private static final EthereumClassic instance = new EthereumClassic();

    private EthereumClassic() {
        minedCurrencyShortName = ETC;
    }

    public static EthereumClassic getInstance(){
        return instance;
    }

    @Override
    public void startMining() {

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
