package com.company.Miners;

import com.company.Miners.Configuration.MiningConfiguration;
import com.company.Timeout.TimeoutManager;
import org.apache.log4j.Logger;

import java.util.concurrent.TimeUnit;

import static com.company.Variables.MINING_TIMEOUT_MILLIS;

public class MinersManager {

    private final static Logger logger = Logger.getLogger(MinersManager.class);

    public boolean launchMiners(MiningConfiguration miningConfiguration) {
        //TODO: build a miner factory taking the shortname of the miner as param
        if(!miningConfiguration.isActivateMining()) {
            logger.warn("Mining has been deactivated");
            TimeoutManager.deactivatedMiningTimeout();
            return false;
        }

        MinedCurrencyShortName currencyMined = MinedCurrencyShortName.valueOf(miningConfiguration.getCurrenciesToMine().get(0));
        Miner mainMiner = MinersFactory.getMiner(currencyMined);

        mainMiner.startMining();
        return true;
    }
}
