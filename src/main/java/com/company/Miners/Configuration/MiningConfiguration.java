package com.company.Miners.Configuration;

import com.company.Miners.MinedCurrencyShortName;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class MiningConfiguration {
    boolean activateMining;
    List<String> currenciesToMine; // TODO check if the list can be of MinedCryptocurrencyShortName

    public List<MinedCurrencyShortName> getCurrenciesToMine() {
        return currenciesToMine.stream().map(MinedCurrencyShortName::valueOf).collect(Collectors.toList());
    }
}
