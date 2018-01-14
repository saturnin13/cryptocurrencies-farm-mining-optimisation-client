package com.company.MiningSoftware;

import com.company.Main;
import com.company.Miners.MinersFactory;
import org.apache.log4j.Logger;

import static com.company.Miners.MinedCurrencyShortName.BTC;
import static com.company.Miners.MinedCurrencyShortName.ETC;
import static com.company.Miners.MinedCurrencyShortName.ETH;

public class MiningSoftwareInstallation {

    private final static Logger logger = Logger.getLogger(MiningSoftwareInstallation.class);

    public boolean installAll() {
        logger.info("Installing all mining software");
        MinersFactory.getMiner(ETH).install();
        MinersFactory.getMiner(ETC).install();
        MinersFactory.getMiner(BTC).install();

        return false;
    }
}
