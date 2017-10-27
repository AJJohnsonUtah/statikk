/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.dataminer.model;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import statikk.domain.entity.ChampBan;
import statikk.domain.entity.ChampBanPK;
import statikk.domain.entity.ChampFinalBuild;
import statikk.domain.entity.ChampFinalBuildPK;
import statikk.domain.entity.ChampMatchup;
import statikk.domain.entity.ChampMatchupPK;
import statikk.domain.entity.ChampSpecWinRate;
import statikk.domain.entity.ChampSpecWinRatePK;
import statikk.domain.entity.ChampSummonerSpells;
import statikk.domain.entity.ChampSummonerSpellsPK;
import statikk.domain.entity.ChampTeamup;
import statikk.domain.entity.ChampTeamupPK;
import statikk.domain.service.ChampBanService;
import statikk.domain.service.ChampFinalBuildService;
import statikk.domain.service.ChampMatchupService;
import statikk.domain.service.ChampSpecWinRateService;
import statikk.domain.service.ChampSummonerSpellsService;
import statikk.domain.service.ChampTeamupService;

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
    private HashMap<ChampSpecWinRatePK, ChampSpecWinRate> champSpecWinRates;
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
        if (champSpecWinRates.containsKey(champSpecWinRate.getChampSpecWinRatePK())) {
            champSpecWinRates.get(champSpecWinRate.getChampSpecWinRatePK()).combine(champSpecWinRate);
        } else {
            champSpecWinRates.put(champSpecWinRate.getChampSpecWinRatePK(), champSpecWinRate);
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
