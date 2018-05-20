package com.company;

import com.company.Client.HttpRequestHandling;
import com.company.Client.JsonFormat.ClientJson.MiningConfiguration.ClientConfiguration.ClientConfiguration;
import com.company.Client.JsonFormat.General.GPU.GPU;
import com.company.Client.JsonFormat.ClientJson.ReportMiningDiagnosis.ReportMiningDiagnosisRequestData;
import com.company.Client.JsonFormat.General.GPU.GraphicCard;
import com.company.Client.JsonFormat.General.MinedCurrencyShortName;
import com.company.MachineInformation.MachineConfigurationRetriever;
import com.company.Miners.MinerManagment.MinersManager;
import com.company.MiningSoftware.MiningSoftware;
import org.apache.log4j.Logger;

import java.util.List;

import static com.company.Client.JsonFormat.General.GPU.GPUType.CUDA;
import static com.company.Client.JsonFormat.General.GPU.GPUType.OPEN_CL;

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
        ClientConfiguration clientConfiguration = MachineConfigurationRetriever.getMachineCharacteristics();
        List<GPU> gpus = clientConfiguration.getGpus();
        for(GPU gpu: gpus) {
            GraphicCard graphicCard = null;
            if(gpu.getGraphicCard() != null) {
                graphicCard = gpu.getGraphicCard();
            } else if(gpu.getGpuType() == CUDA) {
                graphicCard = GraphicCard.GTX_750_TI;
            } else if(gpu.getGpuType() == OPEN_CL) {
                graphicCard = GraphicCard.AMD_280X;
            } else {
                continue;
            }
            requestHandling.reportMiningDiagnosis(ReportMiningDiagnosisRequestData.builder()
                    .currency(MinedCurrencyShortName.ETH)
                    .hashRate(0)
                    .gpu(gpu)
                    .build());
        }
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
