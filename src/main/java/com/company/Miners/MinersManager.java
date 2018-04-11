package com.company.Miners;

import com.company.Client.HttpRequestHandling;
import com.company.MachineInformation.Configuration.ClientConfiguration;
import com.company.MachineInformation.MachineConfigurationRetriever;
import com.company.Miners.Configuration.MiningConfiguration;
import org.apache.log4j.Logger;

import java.util.concurrent.TimeUnit;

import static com.company.Variables.CONFIGURATION_UPDATE_RATE;

public class MinersManager {

    private final static Logger logger = Logger.getLogger(MinersManager.class);

    private MinedCurrencyShortName currentlyMinedCurrency;

    public void launchMiners() {
        ClientConfiguration clientConfig = MachineConfigurationRetriever.getMachineCharacteristics();
        HttpRequestHandling httpRequestHandling = new HttpRequestHandling();

        while(true) {
            MiningConfiguration miningConfiguration = httpRequestHandling.getMiningConfiguration(clientConfig);
            MinedCurrencyShortName newCurrency = chooseMiner(miningConfiguration);

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

    private MinedCurrencyShortName chooseMiner(MiningConfiguration miningConfiguration) {
        //TODO: build a miner factory taking the shortname of the miner as param
        if(!miningConfiguration.isActivateMining() || miningConfiguration.getCurrenciesToMine().isEmpty()) {
            logger.warn("Mining is deactivated");
            return null;
        }

        return MinedCurrencyShortName.valueOf(miningConfiguration.getCurrenciesToMine().get(0));
    }
}
