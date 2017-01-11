/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheoryminer.service;

import com.njin.loltheory.entity.ChampSpecWinRate;
import com.njin.loltheory.riotapi.model.MatchDetail;
import com.njin.loltheory.riotapi.model.MatchParticipant;
import com.njin.loltheory.riotapi.model.Region;
import com.njin.loltheory.riotapi.service.RiotApiService;
import com.njin.loltheory.service.ChampSpecService;
import com.njin.loltheory.service.LolMatchService;
import com.njin.loltheory.service.LolVersionService;
import com.njin.loltheoryminer.model.LolAggregateAnalysis;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author AJ
 */
@Service
public class MatchAnalyzerService {

    @Autowired
    RiotApiService riotApiService;

    @Autowired
    LolMatchService lolMatchService;

    @Autowired
    LolAggregateAnalysis aggregateAnalysis;

    @Autowired
    ChampSpecService champSpecService;

    @Autowired
    LolVersionService lolVersionService;

    public void analyzeMatches(int matchesToAnalyze) {
        List<Long> matchIdsToAnalyze = lolMatchService.findMatchesToAnalyze(matchesToAnalyze);

        aggregateAnalysis.resetAnalysis();

        for (Long matchId : matchIdsToAnalyze) {
            MatchDetail currentMatch = riotApiService.getMatchDetailWithTimeline(Region.NA, matchId);
            // If no status is present, there was no error fetching the match
            if (currentMatch.getStatus() == null) {
                analyzeMatch(currentMatch, aggregateAnalysis);
            }
        }

        aggregateAnalysis.save();

    }

    public void analyzeMatch(MatchDetail match, LolAggregateAnalysis aggregateAnalysis) {
        loadEntities(match);

        analyzeChampSpecWinRates(match, aggregateAnalysis);
        analyzeChampMatchups(match, aggregateAnalysis);
    }

    private void loadEntities(MatchDetail match) {
        match.setMatchVersion(lolVersionService.loadEntity(match.getMatchVersion()));
        match.getParticipants().stream().forEach((participant) -> {
            participant.setChampSpec(champSpecService.loadEntity(participant.getChampSpec()));
        });
    }

    private void analyzeChampSpecWinRates(MatchDetail match, LolAggregateAnalysis aggregateAnalysis) {
        for (MatchParticipant participant : match.getParticipants()) {
            ChampSpecWinRate winRate = new ChampSpecWinRate(participant.getChampSpec());
            if (participant.getTeamId() == match.getWinner()) {
                winRate.addWin();
            } else {
                winRate.addLoss();
            }
            aggregateAnalysis.addChampSpecWinRate(winRate);
        }
    }

    private void analyzeChampMatchups(MatchDetail match, LolAggregateAnalysis aggregateAnalysis) {
        for(MatchParticipant blueParticipant : match.getBlueTeam()) {
            for(MatchParticipant purpleParticipant : match.getPurpleTeam()) {
                
            }
        }
    }
}
