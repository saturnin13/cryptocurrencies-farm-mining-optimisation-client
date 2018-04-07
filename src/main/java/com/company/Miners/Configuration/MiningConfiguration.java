package com.company.Miners.Configuration;

import lombok.Data;

import java.util.List;

@Data
public class MiningConfiguration {
    boolean activateMining;
    List<String> currenciesToMine;
}
