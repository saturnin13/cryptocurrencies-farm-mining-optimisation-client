package com.company.MiningSoftware;

import com.company.Main;
import com.company.Miners.MinedCurrencyShortName;
import com.company.Miners.Miner;
import com.company.Miners.MinersFactory;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

import static com.company.Miners.MinedCurrencyShortName.*;

public class MiningSoftwareInstallation {

    private final static Logger logger = Logger.getLogger(MiningSoftwareInstallation.class);

    public boolean installAll() {
        logger.info("Installing all mining software");
        List<MinedCurrencyShortName> minedCurrencies = Arrays.asList(MinedCurrencyShortName.values());

        for (int i = 0; i < minedCurrencies.size(); i++) {
            Miner currentMiner = MinersFactory.getMiner(minedCurrencies.get(i));
            if(!currentMiner.isInstalled()) {
                currentMiner.install();
            }
        }

        return false;
    }
}
