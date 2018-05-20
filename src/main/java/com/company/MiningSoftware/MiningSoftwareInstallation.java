package com.company.MiningSoftware;

import com.company.MachineInformation.MachineConfigurationRetriever;
import com.company.Client.JsonFormat.General.MinedCurrencyShortName;
import com.company.Miners.Miner;
import com.company.Miners.MinerManagment.MinersFactory;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class MiningSoftwareInstallation {

    private final static Logger logger = Logger.getLogger(MiningSoftwareInstallation.class);

    public boolean installAll() {
        logger.info("Installing all mining software");
        List<MinedCurrencyShortName> minedCurrencies = Arrays.asList(MinedCurrencyShortName.values());

        for (int i = 0; i < minedCurrencies.size(); i++) {
            Miner currentMiner = MinersFactory.getMiner(minedCurrencies.get(i));
            if(!currentMiner.isInstalled()) {
                currentMiner.install(MachineConfigurationRetriever.getMachineCharacteristics().getOs().getOsType());
            }
        }

        return false;
    }
}
