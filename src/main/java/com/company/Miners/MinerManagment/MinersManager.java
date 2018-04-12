package com.company.Miners.MinerManagment;

import com.company.Client.HttpRequestHandling;
import com.company.MachineInformation.Configuration.ClientConfiguration;
import com.company.MachineInformation.MachineConfigurationRetriever;
import com.company.Miners.Configuration.MiningConfiguration;
import com.company.Miners.MinedCurrencyShortName;
import org.apache.log4j.Logger;

import java.util.concurrent.TimeUnit;

import static com.company.Variables.CONFIGURATION_UPDATE_RATE;

public class MinersManager {

    private final static Logger logger = Logger.getLogger(MinersManager.class);

    private MinedCurrencyShortName currentlyMinedCurrency;
    private MiningConfiguration miningConfiguration;

    public void launchMiners() {
        ClientConfiguration clientConfig = MachineConfigurationRetriever.getMachineCharacteristics();
        HttpRequestHandling httpRequestHandling = new HttpRequestHandling();

        while(true) {
            miningConfiguration = httpRequestHandling.getMiningConfiguration(clientConfig);
            MinedCurrencyShortName newCurrency = chooseMiner();

            if(newCurrency != currentlyMinedCurrency) {
                MinersStarter.startMiner(newCurrency);
                currentlyMinedCurrency = newCurrency;
            }

            sleepMinersManager();
        }
    }

    private void sleepMinersManager() {
        try {
            TimeUnit.MILLISECONDS.sleep(CONFIGURATION_UPDATE_RATE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private MinedCurrencyShortName chooseMiner() {
        if(!isMiningBeActivated()) {
            logger.warn("Mining is deactivated");
            return null;
        }

        for (MinedCurrencyShortName currency : miningConfiguration.getCurrenciesToMine()) {
            if(MinersFactory.getMiner(currency).canMineOnMachine(MachineConfigurationRetriever.getMachineCharacteristics())) {
                return currency;
            } else {
                logger.info(currency + " cannot be mined on the machine");
            }
        }

        logger.warn("None of the given currencies can be mined on the machine, mining is deactivated");
        return null;
    }

    private boolean isMiningBeActivated() {
        return miningConfiguration.isActivateMining() && !miningConfiguration.getCurrenciesToMine().isEmpty();
    }
}
