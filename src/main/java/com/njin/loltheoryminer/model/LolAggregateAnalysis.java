/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheoryminer.model;

import com.njin.loltheory.entity.ChampSpecWinRate;
import com.njin.loltheory.service.ChampSpecWinRateService;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author AJ
 */
@Service
public class LolAggregateAnalysis {

    @Autowired
    ChampSpecWinRateService champSpecWinRateService;

    private HashMap<ChampSpecWinRate, ChampSpecWinRate> champSpecWinRates;

    public LolAggregateAnalysis() {
        champSpecWinRates = new HashMap<>();
    }

    public void resetAnalysis() {
        champSpecWinRates = new HashMap<>();
    }

    public void addChampSpecWinRate(ChampSpecWinRate champSpecWinRate) {
        if (champSpecWinRates.containsKey(champSpecWinRate)) {
            champSpecWinRates.get(champSpecWinRate).combine(champSpecWinRate);
        } else {
            champSpecWinRates.put(champSpecWinRate, champSpecWinRate);
        }
    }

    public void save() {
        champSpecWinRateService.batchInsert(champSpecWinRates);
    }

}
