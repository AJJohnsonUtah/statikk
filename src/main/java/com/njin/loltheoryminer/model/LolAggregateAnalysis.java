/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheoryminer.model;

import com.njin.loltheory.entity.ChampBan;
import com.njin.loltheory.entity.ChampBanPK;
import com.njin.loltheory.entity.ChampFinalBuild;
import com.njin.loltheory.entity.ChampFinalBuildPK;
import com.njin.loltheory.entity.ChampMatchup;
import com.njin.loltheory.entity.ChampMatchupPK;
import com.njin.loltheory.entity.ChampSpec;
import com.njin.loltheory.entity.ChampSpecWinRate;
import com.njin.loltheory.entity.ChampSummonerSpells;
import com.njin.loltheory.entity.ChampSummonerSpellsPK;
import com.njin.loltheory.entity.ChampTeamup;
import com.njin.loltheory.entity.ChampTeamupPK;
import com.njin.loltheory.service.ChampBanService;
import com.njin.loltheory.service.ChampFinalBuildService;
import com.njin.loltheory.service.ChampMatchupService;
import com.njin.loltheory.service.ChampSpecWinRateService;
import com.njin.loltheory.service.ChampSummonerSpellsService;
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
    
    @Autowired
    ChampSummonerSpellsService champSummonerSpellsService;

    @Autowired
    ChampBanService champBanService;
    private HashMap<ChampSpec, ChampSpecWinRate> champSpecWinRates;
    private HashMap<ChampMatchupPK, ChampMatchup> champMatchups;
    private HashMap<ChampTeamupPK, ChampTeamup> champTeamups;
    private HashMap<ChampFinalBuildPK, ChampFinalBuild> champFinalBuilds;
    private HashMap<ChampSummonerSpellsPK, ChampSummonerSpells> champSummonerSpells;
    private HashMap<ChampBanPK, ChampBan> champBans;
    
    
    public LolAggregateAnalysis() {
        champSpecWinRates = new HashMap<>();
        champMatchups = new HashMap<>();
        champTeamups = new HashMap<>();
        champFinalBuilds = new HashMap<>();
        champSummonerSpells = new HashMap<>();
        champBans = new HashMap<>();
    }

    public void resetAnalysis() {
        champSpecWinRates = new HashMap<>();
        champMatchups = new HashMap<>();
        champTeamups = new HashMap<>();
        champFinalBuilds = new HashMap<>();
        champSummonerSpells = new HashMap<>();
        champBans = new HashMap<>();
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

    public void addChampSummonerSpells(ChampSummonerSpells champSummonerSpell) {
        if (champSummonerSpells.containsKey(champSummonerSpell.getChampSummonerSpellsPK())) {
            champSummonerSpells.get(champSummonerSpell.getChampSummonerSpellsPK()).combine(champSummonerSpell);
        } else {
            champSummonerSpells.put(champSummonerSpell.getChampSummonerSpellsPK(), champSummonerSpell);
        }
    }
    
    public void addChampBan(ChampBan champBan) {
        if(champBans.containsKey(champBan.getChampBanPK())) {
            champBans.get(champBan.getChampBanPK()).combine(champBan);
        } else {
            champBans.put(champBan.getChampBanPK(), champBan);
        }
    }
    
    public void save() {
        champSpecWinRateService.batchInsertOrUpdate(champSpecWinRates);
        champMatchupService.batchInsertOrUpdate(champMatchups);
        champTeamupService.batchInsertOrUpdate(champTeamups);
        champFinalBuildService.batchInsertOrUpdate(champFinalBuilds);
        champSummonerSpellsService.batchInsertOrUpdate(champSummonerSpells);
        champBanService.batchInsertOrUpdate(champBans);
    }

}
