/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheoryminer.service;

import com.njin.loltheory.entity.ChampFinalBuild;
import com.njin.loltheory.entity.ChampFinalBuildPK;
import com.njin.loltheory.entity.ChampMatchup;
import com.njin.loltheory.entity.ChampMatchupPK;
import com.njin.loltheory.entity.ChampSpec;
import com.njin.loltheory.entity.ChampSpecWinRate;
import com.njin.loltheory.entity.ChampTeamup;
import com.njin.loltheory.entity.ChampTeamupPK;
import com.njin.loltheory.riotapi.model.LolTeam;
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

    @Autowired
    ItemAnalysisService itemAnalysisService;

    public MatchAnalyzerService() {

    }

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
        // Champ Spec and LolVersion entities must be loaded before analysis
        loadEntities(match);

        analyzeChampSpecWinRates(match, aggregateAnalysis);
        analyzeChampMatchups(match, aggregateAnalysis);
        analyzeChampTeamups(match, aggregateAnalysis);
        analyzeFinalBuildOrder(match, aggregateAnalysis);
    }

    private void loadEntities(MatchDetail match) {
        // Load LolVersion
        match.setMatchVersion(lolVersionService.loadEntity(match.getMatchVersion()));

        // Load ChampSpecs
        match.getParticipants().stream().forEach((participant) -> {
            participant.setChampSpec(new ChampSpec(match, participant));
            participant.setChampSpec(champSpecService.loadEntity(participant.getChampSpec()));
        });
        // Load FinalBuildOrders
        itemAnalysisService.loadFinalBuildOrders(match);
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
        for (MatchParticipant blueParticipant : match.getTeam(LolTeam.BLUE)) {
            for (MatchParticipant purpleParticipant : match.getTeam(LolTeam.PURPLE)) {
                ChampMatchupPK blueVsPurplePK = new ChampMatchupPK(blueParticipant.getChampSpec(), purpleParticipant.getChampSpec());
                ChampMatchup blueVsPurple = new ChampMatchup(blueVsPurplePK);
                ChampMatchupPK purpleVsBluePK = new ChampMatchupPK(purpleParticipant.getChampSpec(), blueParticipant.getChampSpec());
                ChampMatchup purpleVsBlue = new ChampMatchup(purpleVsBluePK);
                if (match.getWinner() == LolTeam.BLUE) {
                    blueVsPurple.addWin();
                    purpleVsBlue.addLoss();
                } else {
                    blueVsPurple.addLoss();
                    purpleVsBlue.addLoss();
                }
                aggregateAnalysis.addChampMatchup(blueVsPurple);
                aggregateAnalysis.addChampMatchup(purpleVsBlue);
            }
        }
    }

    private void analyzeChampTeamups(MatchDetail match, LolAggregateAnalysis aggregateAnalysis) {
        for (MatchParticipant blueParticipantA : match.getTeam(LolTeam.BLUE)) {
            for (MatchParticipant blueParticipantB : match.getTeam(LolTeam.BLUE)) {
                if (blueParticipantA.equals(blueParticipantB)) {

                    continue;
                }
                ChampTeamupPK blueTeamupPK = new ChampTeamupPK(blueParticipantA.getChampSpec(), blueParticipantB.getChampSpec());
                ChampTeamup blueTeamup = new ChampTeamup(blueTeamupPK);
                if (match.getWinner() == LolTeam.BLUE) {
                    blueTeamup.addWin();
                } else {
                    blueTeamup.addLoss();
                }
                aggregateAnalysis.addChampTeamup(blueTeamup);
            }
        }

        for (MatchParticipant purpleParticipantA : match.getTeam(LolTeam.PURPLE)) {
            for (MatchParticipant purpleParticipantB : match.getTeam(LolTeam.PURPLE)) {
                if (purpleParticipantA.equals(purpleParticipantB)) {
                    continue;
                }
                ChampTeamupPK purpleTeamupPK = new ChampTeamupPK(purpleParticipantA.getChampSpec(), purpleParticipantB.getChampSpec());
                ChampTeamup purpleTeamup = new ChampTeamup(purpleTeamupPK);
                if (match.getWinner() == LolTeam.PURPLE) {
                    purpleTeamup.addWin();
                } else {
                    purpleTeamup.addLoss();
                }
                aggregateAnalysis.addChampTeamup(purpleTeamup);
            }
        }
    }

    private void analyzeFinalBuildOrder(MatchDetail match, LolAggregateAnalysis aggregateAnalysis) {
        for (MatchParticipant participant : match.getParticipants()) {
            ChampFinalBuildPK champFinalBuildPK = new ChampFinalBuildPK(participant.getChampSpec(), participant.getFinalBuildOrder());
            ChampFinalBuild champFinalBuild = new ChampFinalBuild(champFinalBuildPK);
            if (participant.getTeamId() == match.getWinner()) {
                champFinalBuild.addWin();
            } else {
                champFinalBuild.addLoss();
            }
            aggregateAnalysis.addChampFinalBuild(champFinalBuild);
        }
    }

}
