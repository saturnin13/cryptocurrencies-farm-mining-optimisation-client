package com.company.Miners.MinerManagment;

import com.company.Client.HttpRequestHandling;
import com.company.Client.JsonFormat.ClientJson.MiningConfiguration.ClientConfiguration.ClientConfiguration;
import com.company.Client.JsonFormat.ClientJson.MiningConfiguration.MiningConfigurationRequestData;
import com.company.Client.JsonFormat.ServerJson.MiningConfiguration.GraphicCardMiningConfiguration.GraphicCardMiningConfiguration;
import com.company.Client.JsonFormat.ServerJson.MiningConfigurationResponse;
import com.company.MachineInformation.MachineConfigurationRetriever;
import com.company.Client.JsonFormat.General.MinedCurrencyShortName;
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
            MiningConfigurationResponse miningConfiguration = httpRequestHandling.getMiningConfiguration(MiningConfigurationRequestData.builder().clientConfiguration(clientConfig).build());
            for(GraphicCardMiningConfiguration graphicCardMiningConfiguration: miningConfiguration.getCurrenciesConfiguration()) {
                MinedCurrencyShortName newCurrency = chooseMiner(graphicCardMiningConfiguration, graphicCardMiningConfiguration.isActivateMining());

                if(newCurrency != currentlyMinedCurrency) {
                    MinersStarter.startMiner(newCurrency, graphicCardMiningConfiguration.getGpu());
                    currentlyMinedCurrency = newCurrency;
                }
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

    private MinedCurrencyShortName chooseMiner(GraphicCardMiningConfiguration graphicCardMiningConfiguration, boolean isActivateMining) {
        if(!isMiningBeActivated(graphicCardMiningConfiguration, isActivateMining)) {
            logger.warn("Mining is deactivated");
            return null;
        }

        for (MinedCurrencyShortName currency : graphicCardMiningConfiguration.getCurrenciesToMine()) {
            if(MinersFactory.getMiner(currency).canMineOnMachine(MachineConfigurationRetriever.getMachineCharacteristics())) {
                return currency;
            } else {
                logger.info(currency + " cannot be mined on the machine");
            }
        }

        logger.warn("None of the given currencies can be mined on the machine, mining is deactivated");
        return null;
    }

    private boolean isMiningBeActivated(GraphicCardMiningConfiguration graphicCardMiningConfiguration, boolean isActivateMining) {
        return isActivateMining && !graphicCardMiningConfiguration.getCurrenciesToMine().isEmpty();
    }
}
