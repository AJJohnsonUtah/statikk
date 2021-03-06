/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.dataminer.service;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import statikk.dataminer.model.LolAggregateAnalysis;
import statikk.domain.entity.ChampBan;
import statikk.domain.entity.ChampBanPK;
import statikk.domain.entity.ChampFinalBuild;
import statikk.domain.entity.ChampFinalBuildPK;
import statikk.domain.entity.ChampMatchup;
import statikk.domain.entity.ChampMatchupPK;
import statikk.domain.entity.ChampSpec;
import statikk.domain.entity.ChampSpecWinRate;
import statikk.domain.entity.ChampSpecWinRatePK;
import statikk.domain.entity.ChampSummonerSpells;
import statikk.domain.entity.ChampSummonerSpellsPK;
import statikk.domain.entity.ChampTeamup;
import statikk.domain.entity.ChampTeamupPK;
import statikk.domain.entity.LolMatch;
import statikk.domain.entity.LolSummoner;
import statikk.domain.entity.TeamComp;
import statikk.domain.entity.TeamCompPK;
import statikk.domain.entity.enums.MatchStatus;
import statikk.domain.riotapi.model.LolTeam;
import statikk.domain.riotapi.model.MatchDetail;
import statikk.domain.riotapi.model.ParticipantDto;
import statikk.domain.riotapi.model.Region;
import statikk.domain.riotapi.model.TeamBansDto;
import statikk.domain.riotapi.service.RiotApiService;
import statikk.domain.service.ChampSpecService;
import statikk.domain.service.LolMatchService;
import statikk.domain.service.LolSummonerService;
import statikk.domain.service.LolVersionService;

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
    LolSummonerService lolSummonerService;

    @Autowired
    ChampSpecService champSpecService;

    @Autowired
    LolVersionService lolVersionService;

    @Autowired
    ItemAnalysisService itemAnalysisService;

    @Autowired
    MatchMiningService matchMiningService;

    @Autowired
    LolAggregateAnalysisService lolAggregateAnalysisService;

    public MatchAnalyzerService() {
    }

    public MatchAnalyzerService(RiotApiService riotApiService) {
        this.riotApiService = riotApiService;
    }

    public int analyzeMatches(Region region, LolAggregateAnalysis aggregateAnalysis) {
        List<LolSummoner> summonersFromMatches = new LinkedList<>();
        List<LolMatch> matchesToAnalyze = lolMatchService.findMatchesToAnalyzeByRegion(region, 50);

        Logger.getLogger(MatchAnalyzerService.class.getName()).log(Level.INFO, "Matches fetched for analysis");

        for (LolMatch match : matchesToAnalyze) {
            Logger.getLogger(MatchAnalyzerService.class.getName()).log(Level.INFO, " Fetching match {0} ({1}). ", new Object[]{match.getMatchId(), region});
            MatchDetail currentMatch = riotApiService.getMatchDetailWithTimeline(region, match.getMatchId());
            // If no status is present, there was no error fetching the match
            if (currentMatch != null && currentMatch.getStatus() == null) {
                // Matches must be at least 5 minutes long to do a decent analysis, right?
                if (currentMatch.getGameDuration() <= 300) {
                    match.setStatus(MatchStatus.MATCH_TOO_SHORT);
                } else if (currentMatch.getMatchLolVersion().isBefore(lolVersionService.findMostRecentVersion())) {
                    match.setStatus(MatchStatus.MATCH_VERSION_NOT_CURRENT);
                } else {
                    if (currentMatch.getMatchLolVersion().isAfter(itemAnalysisService.getMostRecentVersion())) {                        
                        itemAnalysisService.reloadItems();
                    }
                    summonersFromMatches.addAll(currentMatch.getLolSummoners());
                    analyzeMatch(currentMatch, aggregateAnalysis);
                    match.setStatus(MatchStatus.COMPLETED);
                }
            } else {
                match.setStatus(MatchStatus.DATA_NOT_FOUND);
            }
        }

        lolAggregateAnalysisService.save(aggregateAnalysis);
        if (matchesToAnalyze.size() > 0) {
            lolMatchService.update(matchesToAnalyze);
            lolSummonerService.addOrUpdate(summonersFromMatches);
        } else {
            Logger.getLogger(MatchAnalyzerService.class.getName()).log(Level.INFO, "No matches found to analyze. Waiting before next call.");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(MatchAnalyzerService.class.getName()).log(Level.SEVERE, "Error while trying to wait between analysis calls", ex);
            }
        }
        return matchesToAnalyze.size();
    }

    public void analyzeMatch(MatchDetail match, LolAggregateAnalysis aggregateAnalysis) {
        // Champ Spec and LolVersion entities must be loaded before analysis
        loadEntities(match);

        analyzeChampSpecWinRates(match, aggregateAnalysis);
        analyzeChampMatchups(match, aggregateAnalysis);
        analyzeChampTeamups(match, aggregateAnalysis);
        analyzeFinalBuildOrder(match, aggregateAnalysis);
        analyzeChampSummonerSpells(match, aggregateAnalysis);
        analyzeChampBans(match, aggregateAnalysis);
        analyzeTeamComps(match, aggregateAnalysis);
    }

    private void loadEntities(MatchDetail match) {
        // Load LolVersion
        match.setMatchLolVersion(lolVersionService.findOrCreate(match.getMatchLolVersion()));

        // Load FinalBuildOrders
        itemAnalysisService.loadFinalBuildOrders(match);
        itemAnalysisService.loadParticipantRoles(match);

        // Load ChampSpecs
        match.getParticipants().stream().forEach((participant) -> {
            participant.setChampSpec(champSpecService.findOrCreate(new ChampSpec(match, participant)));
        });

        // Load ChampSpecs for bans
        match.getBannedChampions().stream().forEach((bannedChamp) -> {
            bannedChamp.setChampSpec(champSpecService.findOrCreate(new ChampSpec(match, bannedChamp)));
        });

    }

    private void analyzeChampSpecWinRates(MatchDetail match, LolAggregateAnalysis aggregateAnalysis) {
        for (ParticipantDto participant : match.getParticipants()) {
            ChampSpecWinRate winRate = new ChampSpecWinRate(new ChampSpecWinRatePK(participant.getChampSpec()));
            if (participant.getTeamId() == match.getWinner()) {
                winRate.addWin();
            } else {
                winRate.addLoss();
            }
            aggregateAnalysis.addChampSpecWinRate(winRate);
        }
    }

    private void analyzeChampMatchups(MatchDetail match, LolAggregateAnalysis aggregateAnalysis) {
        for (ParticipantDto blueParticipant : match.getTeam(LolTeam.BLUE)) {
            for (ParticipantDto purpleParticipant : match.getTeam(LolTeam.PURPLE)) {
                ChampMatchupPK blueVsPurplePK = new ChampMatchupPK(blueParticipant.getChampSpec(), purpleParticipant.getChampSpec());
                ChampMatchup blueVsPurple = new ChampMatchup(blueVsPurplePK);
                ChampMatchupPK purpleVsBluePK = new ChampMatchupPK(purpleParticipant.getChampSpec(), blueParticipant.getChampSpec());
                ChampMatchup purpleVsBlue = new ChampMatchup(purpleVsBluePK);
                if (match.getWinner() == LolTeam.BLUE) {
                    blueVsPurple.addWin();
                    purpleVsBlue.addLoss();
                } else {
                    blueVsPurple.addLoss();
                    purpleVsBlue.addWin();
                }
                aggregateAnalysis.addChampMatchup(blueVsPurple);
                aggregateAnalysis.addChampMatchup(purpleVsBlue);
            }
        }
    }

    private void analyzeChampTeamups(MatchDetail match, LolAggregateAnalysis aggregateAnalysis) {
        for (ParticipantDto blueParticipantA : match.getTeam(LolTeam.BLUE)) {
            for (ParticipantDto blueParticipantB : match.getTeam(LolTeam.BLUE)) {
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

        for (ParticipantDto purpleParticipantA : match.getTeam(LolTeam.PURPLE)) {
            for (ParticipantDto purpleParticipantB : match.getTeam(LolTeam.PURPLE)) {
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
        if (match.getTimeline() == null) {
            return;
        }
        for (ParticipantDto participant : match.getParticipants()) {
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

    private void analyzeChampSummonerSpells(MatchDetail match, LolAggregateAnalysis aggregateAnalysis) {
        for (ParticipantDto participant : match.getParticipants()) {
            ChampSummonerSpellsPK champSummonerSpellsPK = new ChampSummonerSpellsPK(participant.getChampSpec(), participant.getSpell1Id(), participant.getSpell2Id());
            ChampSummonerSpells champSummonerSpells = new ChampSummonerSpells(champSummonerSpellsPK);
            if (participant.getTeamId() == match.getWinner()) {
                champSummonerSpells.addWin();
            } else {
                champSummonerSpells.addLoss();
            }
            aggregateAnalysis.addChampSummonerSpells(champSummonerSpells);
        }
    }

    private void analyzeChampBans(MatchDetail match, LolAggregateAnalysis aggregateAnalysis) {
        for (TeamBansDto bannedChamp : match.getBannedChampions()) {
            ChampBanPK pk = new ChampBanPK(bannedChamp);
            ChampBan champBan = new ChampBan(pk, 1);
            aggregateAnalysis.addChampBan(champBan);
        }
    }

    private void analyzeTeamComps(MatchDetail match, LolAggregateAnalysis aggregateAnalysis) {
        if (match.getBlueTeam() == null || match.getBlueTeam().isEmpty()
                || match.getPurpleTeam() == null || match.getPurpleTeam().isEmpty() || match.getTimeline() == null) {
            return;
        }

        for (LolTeam teamColor : LolTeam.values()) {
            match.getTeam(teamColor).stream().forEach(allyParticipant -> {
                TeamCompPK allyTeamPK = new TeamCompPK(allyParticipant, match.getBlueTeam(), match.getPurpleTeam(), match.getQueueId(), match.getMatchLolVersion(), match.getPlatformId());
                TeamComp allyTeamComp = new TeamComp(allyTeamPK);
                if (match.getWinner() == LolTeam.BLUE) {
                    allyTeamComp.addWin();
                } else {
                    allyTeamComp.addLoss();
                }
                aggregateAnalysis.addTeamComp(allyTeamComp);
            });
        }

    }

}
