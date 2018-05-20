package com.company.Miners.MiningAlgorithm.GpuMining.EthashCurrencies;

import com.company.Client.JsonFormat.ClientJson.MiningConfiguration.ClientConfiguration.ClientConfiguration;
import org.apache.log4j.Logger;

import static com.company.Client.JsonFormat.General.MinedCurrencyShortName.MUSIC;
import static com.company.Variables.GIGA;

public class MusicCoin extends Ethash {

    private final static Logger logger = Logger.getLogger(MusicCoin.class);

    public MusicCoin() {
        minedCurrencyShortName = MUSIC;
        poolAddressProtocol1 = "europe.ethash-hub.miningpoolhub.com";
        poolPortProtocol1 = 20586;
        poolAddressProtocol2 = "http://mc.minecrypto.pro";
        poolPortProtocol2 = 7777;
        necessaryDagSize = (long) (1.6 * GIGA);
    }

    // TODO: As long as no live data and historical data is available with musiccoin (can't find block reward), mining with musiccoin is deactivated
    public boolean canMineOnMachine(ClientConfiguration clientConfiguration) {
        return false;
    }
}
