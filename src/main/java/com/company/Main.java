package com.company;

import com.company.Client.HttpRequestHandling;
import com.company.Miners.MinedCurrencyShortName;
import com.company.Miners.MinerManagment.MinersManager;
import com.company.MiningSoftware.MiningSoftware;
import org.apache.log4j.Logger;

public class Main {

    private final static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        if(!manageMiningSoftwares()){
            return;
        }

        startService();
    }

    private static void startService() {
        registerMiner();
        MinersManager minersManager = new MinersManager();
        minersManager.launchMiners();
    }

    private static void registerMiner() {
        HttpRequestHandling requestHandling = new HttpRequestHandling();
        // TODO create a proper call to register a miner rather than using a side effect of one of the call
        requestHandling.reportMiningDiagnosis(MinedCurrencyShortName.ETH, 0);
    }


    private static boolean manageMiningSoftwares() {
        MiningSoftware miningSoftware = MiningSoftware.getInstance();
        if(!miningSoftware.installAll()) {
            logger.error("The mining software installation did not work correctly, exiting");
            return false;
        }
        if(!miningSoftware.updateAll()) {
            logger.error("The mining software update did not work correctly, exiting");
            return false;
        }
        return true;
    }
}
