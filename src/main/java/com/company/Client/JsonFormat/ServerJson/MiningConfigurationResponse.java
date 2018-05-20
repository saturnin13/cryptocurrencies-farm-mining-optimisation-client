package com.company.Client.JsonFormat.ServerJson;

import com.company.Client.JsonFormat.ServerJson.MiningConfiguration.GraphicCardMiningConfiguration.GraphicCardMiningConfiguration;
import lombok.Data;

import java.util.List;

@Data
public class MiningConfigurationResponse {
    List<GraphicCardMiningConfiguration> currenciesConfiguration; // TODO check if the list can be of MinedCryptocurrencyShortName
}
