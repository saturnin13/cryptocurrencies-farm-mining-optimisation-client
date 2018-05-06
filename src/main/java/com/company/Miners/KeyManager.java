package com.company.Miners;

import com.company.MachineInformation.Configuration.GPU.GPUs;
import com.company.Miners.MiningAlgorithm.GpuMining.EthashCurrencies.Ethereum;
import org.apache.log4j.Logger;

import static com.company.Variables.*;

public class KeyManager {

    private final static Logger logger = Logger.getLogger(KeyManager.class);

    public static String getKey(MinedCurrencyShortName currencyShortName) {
        switch (currencyShortName) {
            case ETH  : return KEY_ETH;
            case ETC  : return KEY_ETC;
            case BTC  : return KEY_BTC;
            case EXP  : return KEY_EXP;
            case MUSIC: return KEY_MUSIC;
            case PIRL : return KEY_PIRL;
            case UBQ  : return KEY_UBQ;
            case XMR  : return KEY_XMR;
            case GRS  : return KEY_GRS;
            default: logger.error("Could not find the key for: " + currencyShortName + ", returning null");
                return null;
        }
    }
}
