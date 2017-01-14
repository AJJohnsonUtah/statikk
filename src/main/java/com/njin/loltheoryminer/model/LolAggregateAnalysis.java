/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheoryminer.model;

import com.njin.loltheory.entity.ChampFinalBuild;
import com.njin.loltheory.entity.ChampFinalBuildPK;
import com.njin.loltheory.entity.ChampMatchup;
import com.njin.loltheory.entity.ChampMatchupPK;
import com.njin.loltheory.entity.ChampSpec;
import com.njin.loltheory.entity.ChampSpecWinRate;
import com.njin.loltheory.entity.ChampTeamup;
import com.njin.loltheory.entity.ChampTeamupPK;
import com.njin.loltheory.service.ChampFinalBuildService;
import com.njin.loltheory.service.ChampMatchupService;
import com.njin.loltheory.service.ChampSpecWinRateService;
import com.njin.loltheory.service.ChampTeamupService;
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

    @Autowired
    ChampMatchupService champMatchupService;

    @Autowired
    ChampTeamupService champTeamupService;

    @Autowired
    ChampFinalBuildService champFinalBuildService;

    private HashMap<ChampSpec, ChampSpecWinRate> champSpecWinRates;
    private HashMap<ChampMatchupPK, ChampMatchup> champMatchups;
    private HashMap<ChampTeamupPK, ChampTeamup> champTeamups;
    private HashMap<ChampFinalBuildPK, ChampFinalBuild> champFinalBuilds;

    public LolAggregateAnalysis() {
        champSpecWinRates = new HashMap<>();
        champMatchups = new HashMap<>();
        champTeamups = new HashMap<>();
        champFinalBuilds = new HashMap<>();
    }

    public void resetAnalysis() {
        champSpecWinRates = new HashMap<>();
        champMatchups = new HashMap<>();
        champTeamups = new HashMap<>();
        champFinalBuilds = new HashMap<>();
    }

    public void addChampSpecWinRate(ChampSpecWinRate champSpecWinRate) {
        if (champSpecWinRates.containsKey(champSpecWinRate.getChampSpec())) {
            champSpecWinRates.get(champSpecWinRate.getChampSpec()).combine(champSpecWinRate);
        } else {
            champSpecWinRates.put(champSpecWinRate.getChampSpec(), champSpecWinRate);
        }
    }

    public void addChampMatchup(ChampMatchup champMatchup) {
        if (champMatchups.containsKey(champMatchup.getChampMatchupPK())) {
            champMatchups.get(champMatchup.getChampMatchupPK()).combine(champMatchup);
        } else {
            champMatchups.put(champMatchup.getChampMatchupPK(), champMatchup);
        }
    }

    public void addChampTeamup(ChampTeamup champTeamup) {
        if (champTeamups.containsKey(champTeamup.getChampTeamupPK())) {
            champTeamups.get(champTeamup.getChampTeamupPK()).combine(champTeamup);
        } else {
            champTeamups.put(champTeamup.getChampTeamupPK(), champTeamup);
        }
    }

    public void addChampFinalBuild(ChampFinalBuild champFinalBuild) {
        if (champFinalBuilds.containsKey(champFinalBuild.getChampFinalBuildPK())) {
            champFinalBuilds.get(champFinalBuild.getChampFinalBuildPK()).combine(champFinalBuild);
        } else {
            champFinalBuilds.put(champFinalBuild.getChampFinalBuildPK(), champFinalBuild);
        }
    }

    public void save() {
        champSpecWinRateService.batchInsertOrUpdate(champSpecWinRates);
        champMatchupService.batchInsertOrUpdate(champMatchups);
        champTeamupService.batchInsertOrUpdate(champTeamups);
        champFinalBuildService.batchInsertOrUpdate(champFinalBuilds);
    }

}
