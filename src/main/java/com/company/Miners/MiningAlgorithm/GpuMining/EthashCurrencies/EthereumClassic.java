package com.company.Miners.MiningAlgorithm.GpuMining.EthashCurrencies;

import org.apache.log4j.Logger;

import static com.company.Miners.MinedCurrencyShortName.ETC;
import static com.company.Variables.GIGA;

public class EthereumClassic extends Ethash {

    private final static Logger logger = Logger.getLogger(EthereumClassic.class);

    public EthereumClassic() {
        minedCurrencyShortName = ETC;
        poolAddressProtocol1 = "eu1-etc.ethermine.org";
        poolPortProtocol1 = 4444;
        poolAddressProtocol2 = "http://etc.poolto.be";
        poolPortProtocol2 = 8746;
        necessaryDagSize = (long) (2.48 * GIGA);
    }
}
