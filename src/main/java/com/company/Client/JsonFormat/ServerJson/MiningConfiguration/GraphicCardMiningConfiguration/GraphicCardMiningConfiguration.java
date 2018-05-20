package com.company.Client.JsonFormat.ServerJson.MiningConfiguration.GraphicCardMiningConfiguration;

import com.company.Client.JsonFormat.General.GPU.GPU;
import com.company.Client.JsonFormat.General.MinedCurrencyShortName;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GraphicCardMiningConfiguration {
    private boolean activateMining;
    private GPU gpu;
    private List<MinedCurrencyShortName> currenciesToMine;
}
