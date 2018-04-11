package com.company;

import com.company.Client.HttpRequestHandling;
import com.company.MachineInformation.Configuration.ClientConfiguration;
import com.company.MachineInformation.MachineConfigurationRetriever;
import com.company.Miners.MinersManager;
import com.company.Miners.Configuration.MiningConfiguration;
import com.company.MiningSoftware.MiningSoftware;
import org.apache.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class Main {

    private final static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        if(!manageMiningSoftwares()){
            return;
        }

        startService();
    }

    private static void startService() {
        MinersManager minersManager = new MinersManager();
        minersManager.launchMiners();
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
