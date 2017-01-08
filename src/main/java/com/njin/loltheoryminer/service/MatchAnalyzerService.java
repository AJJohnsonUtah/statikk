/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheoryminer.service;

import com.njin.loltheory.entity.ChampSpec;
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
        long winningTeam = match.getWinner();
        match.setMatchVersion(lolVersionService.loadEntity(match.getMatchVersion()));
        for (MatchParticipant participant : match.getParticipants()) {
            lolVersionService.update(match.getMatchVersion());
            ChampSpec partSpec = champSpecService.loadEntity(new ChampSpec(match, participant));
            champSpecService.update(partSpec);
            ChampSpecWinRate winRate = new ChampSpecWinRate(partSpec);
            participant.setChampSpec(partSpec);
            if (participant.getTeamId() == winningTeam) {
                winRate.addWin();
            } else {
                winRate.addLoss();
            }
            aggregateAnalysis.addChampSpecWinRate(winRate);
        }

    }
}
