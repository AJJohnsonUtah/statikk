/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.dataminer.service;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import statikk.dataminer.model.LolAggregateAnalysis;
import statikk.domain.entity.TeamComp;
import statikk.domain.service.ChampBanService;
import statikk.domain.service.ChampFinalBuildService;
import statikk.domain.service.ChampMatchupService;
import statikk.domain.service.ChampSpecWinRateService;
import statikk.domain.service.ChampSummonerSpellsService;
import statikk.domain.service.ChampTeamupService;
import statikk.domain.service.TeamCompService;

/**
 *
 * @author AJ
 */
@Service
public class LolAggregateAnalysisService {

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

    @Autowired
    TeamCompService teamCompService;

    public void save(LolAggregateAnalysis aggregateAnalysis) {
        champSpecWinRateService.batchInsertOrUpdate(aggregateAnalysis.getChampSpecWinRates().values());
        champMatchupService.batchInsertOrUpdate(aggregateAnalysis.getChampMatchups().values());
        champTeamupService.batchInsertOrUpdate(aggregateAnalysis.getChampTeamups().values());
        champFinalBuildService.batchInsertOrUpdate(aggregateAnalysis.getChampFinalBuilds().values());
        champSummonerSpellsService.batchInsertOrUpdate(aggregateAnalysis.getChampSummonerSpells().values());
        champBanService.batchInsertOrUpdate(aggregateAnalysis.getChampBans().values());
        teamCompService.batchInsertOrUpdate(aggregateAnalysis.getTeamComps().values());
      
    }

}
