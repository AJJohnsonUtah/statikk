/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.webapi.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import statikk.domain.entity.enums.Lane;
import statikk.domain.entity.enums.Role;
import statikk.domain.riotapi.model.ChampionMasteryDto;
import statikk.domain.riotapi.model.QueueType;
import statikk.domain.riotapi.model.Region;
import statikk.domain.riotapi.model.SummonerDto;
import statikk.domain.riotapi.service.RiotApiService;
import statikk.domain.service.ChampMatchupService;
import statikk.domain.service.ChampTeamupService;
import statikk.domain.service.TeamCompService;
import statikk.domain.stats.model.BaseWinRate;
import statikk.domain.stats.model.WinRateByChampion;
import statikk.domain.stats.model.WinRateByChampionLane;
import statikk.domain.stats.model.WinRateByChampionRole;
import statikk.domain.stats.model.WinRateMapWithTotal;
import statikk.domain.stats.service.ChampionWinRateService;
import statikk.webapi.controller.SummonerDataController;
import statikk.webapi.model.ChampionSuggestion;
import statikk.webapi.model.ChampionSuggestionContext;
import statikk.webapi.model.ChampionSuggestionReason;
import statikk.webapi.model.SupportingSuggestionInfo;
import statikk.webapi.model.SupportingSuggestionReason;
import statikk.webapi.model.TeamBuilderProgressData;

/**
 *
 * @author Grann
 */
@Service
public class TeamBuilderService {

    @Autowired
    ChampionWinRateService championWinRateService;

    @Autowired
    ChampTeamupService champTeamupService;

    @Autowired
    ChampMatchupService champMatchupService;

    @Autowired
    SummonerDataController summonerDataController;

    @Autowired
    TeamCompService teamCompService;

    @Autowired
    RiotApiService riotApiService;

    public Map<Integer, ChampionSuggestion> getChampionSuggestions(TeamBuilderProgressData teamBuilderProgress) {

        Map<Integer, ChampionSuggestion> championSuggestions = new HashMap<>();

        updateSuggestionsBasedOnChampionMastery(teamBuilderProgress, championSuggestions);
        updateSuggestionsBasedOnLanePerformance(teamBuilderProgress, championSuggestions);
        updateSuggestionsBasedOnTeamups(teamBuilderProgress, championSuggestions);
        updateSuggestionsBasedOnMatchups(teamBuilderProgress, championSuggestions);
        updateSuggestionsBasedOnTeamComps(teamBuilderProgress, championSuggestions);

        return championSuggestions;
    }

    public void updateSuggestionsBasedOnChampionMastery(TeamBuilderProgressData teamBuilderProgress, Map<Integer, ChampionSuggestion> championSuggestions) {
        SummonerDto currentUser = summonerDataController.getSummonerByName(teamBuilderProgress.getSummonerName(), Region.NA.name());
        if (currentUser == null) {
            return;
        }

        List<ChampionMasteryDto> championMasteries = summonerDataController.getChampionMastery(currentUser.getId(), Region.NA.name());
        if (championMasteries == null) {
            return;
        }

        Set<Integer> champsWithData = new HashSet<>(riotApiService.getStaticChampionsDataObject(Region.NA).getData().keySet());

        for (ChampionMasteryDto championMastery : championMasteries) {
            ChampionSuggestionContext suggestionContext = new ChampionSuggestionContext(ChampionSuggestionReason.ChampionMastery);
            switch (championMastery.getChampionLevel()) {
                case 7:
                    suggestionContext.setScore(1.0);
                    break;
                case 6:
                    suggestionContext.setScore(0.9);
                    break;
                case 5:
                    suggestionContext.setScore(0.8);
                    break;
                case 4:
                    suggestionContext.setScore(0.6);
                    break;
                case 3:
                    suggestionContext.setScore(0.4);
                    break;
                case 2:
                    suggestionContext.setScore(0.1);
                    break;
                default:
                    suggestionContext.setScore(0.0);
            }
            champsWithData.remove(championMastery.getChampionId());
            putOrUpdate(championSuggestions, championMastery.getChampionId(), suggestionContext);
        }

        champsWithData.stream().forEach((champId) -> {
            ChampionSuggestionContext suggestionContext = new ChampionSuggestionContext(ChampionSuggestionReason.ChampionMastery, 0.0);
            putOrUpdate(championSuggestions, champId, suggestionContext);
        });
    }

    public void updateSuggestionsBasedOnLanePerformance(TeamBuilderProgressData teamBuilderProgress, Map<Integer, ChampionSuggestion> championSuggestions) {
        Map<Integer, WinRateMapWithTotal<Lane, WinRateByChampionLane>> champLaneData
                = championWinRateService.getWinRatesByChampionLane(QueueType.standardSRMatchTypes);

        champLaneData.keySet().stream().forEach((champId) -> {
            // Analyze each champion
            WinRateMapWithTotal<Lane, WinRateByChampionLane> champWinRateData = champLaneData.get(champId);
            ChampionSuggestionContext suggestionContext = new ChampionSuggestionContext(ChampionSuggestionReason.ChampionLaneProficiency);
            // If the champion has matches played in this lane and at least one other
            if (champWinRateData.getWinRateData().containsKey(teamBuilderProgress.getLane()) && champWinRateData.getWinRateData().size() > 1) {
                // Perform a test to determine if the win rate in this lane is statistically higher than overall
                if (champWinRateData.isSignificantlyHigherWinRate(teamBuilderProgress.getLane())) {
                    // If it is, then give it an extra positive score
                    suggestionContext.setScore(1.0);
                } // Perform a test to determine if the win rate in this lane is statistically lower than overall
                else if (champWinRateData.isSignificantlyLowerWinRate(teamBuilderProgress.getLane())) {
                    // If it is, then give it an extra negative score
                    suggestionContext.setScore(0.0);
                } else {
                    // If it is not, then the lane does not seem to have a significant impact; 
                    suggestionContext.setScore(0.5);
                }
            } else {
                suggestionContext.setScore(0.5);
            }
            putOrUpdate(championSuggestions, champId, suggestionContext);
        });
    }

    public void updateSuggestionsBasedOnTeamups(TeamBuilderProgressData teamBuilderProgress, Map<Integer, ChampionSuggestion> championSuggestions) {
        if (teamBuilderProgress.getAllyChampionIds() == null || teamBuilderProgress.getAllyChampionIds().isEmpty()) {
            return;
        }
        Map<Integer, List<SupportingSuggestionInfo<Integer>>> suggestionInfo = new HashMap<>();

        teamBuilderProgress.getAllyChampionIds().stream().forEach((allyChampId) -> {
            WinRateMapWithTotal<Integer, WinRateByChampion> winRates = champTeamupService.getWinRatesByChampion(allyChampId, QueueType.standardSRMatchTypes);
            for (Integer potentialChampPickId : winRates.getWinRateData().keySet()) {
                if (!suggestionInfo.containsKey(potentialChampPickId)) {
                    suggestionInfo.put(potentialChampPickId, new LinkedList<>());
                }
                if (winRates.isSignificantlyHigherWinRate(potentialChampPickId)) {
                    suggestionInfo.get(potentialChampPickId).add(new SupportingSuggestionInfo(SupportingSuggestionReason.PositiveEffect, potentialChampPickId));
                } else if (winRates.isSignificantlyLowerWinRate(potentialChampPickId)) {
                    suggestionInfo.get(potentialChampPickId).add(new SupportingSuggestionInfo(SupportingSuggestionReason.NegativeEffect, potentialChampPickId));
                } else {
                    suggestionInfo.get(potentialChampPickId).add(new SupportingSuggestionInfo(SupportingSuggestionReason.NeutralEffect, potentialChampPickId));
                }
            }
        });
        suggestionInfo.keySet().stream().forEach((potentialChampPickId) -> {
            Double score = (suggestionInfo.get(potentialChampPickId).stream().map(info -> info.getSupportingReason().getScore()).reduce(0.0, (a, b) -> a + b) / suggestionInfo.get(potentialChampPickId).size() + 1.0) / 2.0;
            ChampionSuggestionContext<Integer> suggestionContext = new ChampionSuggestionContext<>(ChampionSuggestionReason.TeamSynergy, score, suggestionInfo.get(potentialChampPickId));
            putOrUpdate(championSuggestions, potentialChampPickId, suggestionContext);
        });
    }

    public void updateSuggestionsBasedOnMatchups(TeamBuilderProgressData teamBuilderProgress, Map<Integer, ChampionSuggestion> championSuggestions) {
        if (teamBuilderProgress.getEnemyChampionIds() == null || teamBuilderProgress.getEnemyChampionIds().isEmpty()) {
            return;
        }
        Map<Integer, List<SupportingSuggestionInfo<Integer>>> suggestionInfo = new HashMap<>();

        teamBuilderProgress.getEnemyChampionIds().stream().forEach((allyChampId) -> {
            WinRateMapWithTotal<Integer, WinRateByChampion> winRates = champTeamupService.getWinRatesByChampion(allyChampId, QueueType.standardSRMatchTypes);
            for (Integer potentialChampPickId : winRates.getWinRateData().keySet()) {
                if (!suggestionInfo.containsKey(potentialChampPickId)) {
                    suggestionInfo.put(potentialChampPickId, new LinkedList<>());
                }
                if (winRates.isSignificantlyHigherWinRate(potentialChampPickId)) {
                    suggestionInfo.get(potentialChampPickId).add(new SupportingSuggestionInfo(SupportingSuggestionReason.PositiveEffect, potentialChampPickId));
                } else if (winRates.isSignificantlyLowerWinRate(potentialChampPickId)) {
                    suggestionInfo.get(potentialChampPickId).add(new SupportingSuggestionInfo(SupportingSuggestionReason.NegativeEffect, potentialChampPickId));
                } else {
                    suggestionInfo.get(potentialChampPickId).add(new SupportingSuggestionInfo(SupportingSuggestionReason.NeutralEffect, potentialChampPickId));
                }
            }
        });
        suggestionInfo.keySet().stream().forEach((potentialChampPickId) -> {
            Double score = (suggestionInfo.get(potentialChampPickId).stream().map(info -> info.getSupportingReason().getScore()).reduce(0.0, (a, b) -> a + b) / suggestionInfo.get(potentialChampPickId).size() + 1.0) / 2.0;
            ChampionSuggestionContext<Integer> suggestionContext = new ChampionSuggestionContext<>(ChampionSuggestionReason.EnemyCounter, score, suggestionInfo.get(potentialChampPickId));
            putOrUpdate(championSuggestions, potentialChampPickId, suggestionContext);
        });
    }

    private void putOrUpdate(Map<Integer, ChampionSuggestion> championSuggestions, Integer championId, ChampionSuggestionContext suggestionContext) {
        if (championSuggestions.containsKey(championId)) {
            championSuggestions.get(championId).addSuggestionContext(suggestionContext);
        } else {
            championSuggestions.put(championId, new ChampionSuggestion(championId, suggestionContext));
        }
    }

    public void updateSuggestionsBasedOnTeamComps(TeamBuilderProgressData teamBuilderProgress, Map<Integer, ChampionSuggestion> championSuggestions) {
        Map<Integer, WinRateMapWithTotal<Role, WinRateByChampionRole>> allChampionRoleWinRates = championWinRateService.getWinRatesByChampionRole(QueueType.standardSRMatchTypes);
        HashMap<Role, Integer> allyTeam = getPredictedRoleCountsFromChampions(teamBuilderProgress.getAllyChampionIds());
        HashMap<Role, Integer> enemyTeam = getPredictedRoleCountsFromChampions(teamBuilderProgress.getEnemyChampionIds());

        WinRateMapWithTotal<Role, BaseWinRate> roleWinRates = new WinRateMapWithTotal(teamCompService.findWinRatesByRoleForPartialTeamComps(allyTeam, enemyTeam));

        // Determine which roles do well/poorly with given team comps
        Set<Role> suggestedRoles = roleWinRates.getWinRateData().keySet().stream().filter((role) -> roleWinRates.hasDataFor(role) && roleWinRates.isSignificantlyHigherWinRate(role)).collect(Collectors.toSet());
        Set<Role> discouragedRoles = roleWinRates.getWinRateData().keySet().stream().filter((role) -> roleWinRates.hasDataFor(role) && roleWinRates.isSignificantlyLowerWinRate(role)).collect(Collectors.toSet());

        // If there are no roles to suggest/discourage, do nothing
        if (suggestedRoles.isEmpty() && discouragedRoles.isEmpty()) {
            return;
        }
        allChampionRoleWinRates.keySet().stream().forEach((champId) -> {
            putOrUpdate(championSuggestions, champId, getSuggestionContextFromRoles(allChampionRoleWinRates.get(champId), champId, suggestedRoles, discouragedRoles));
        });
    }

    public HashMap<Role, Integer> getPredictedRoleCountsFromChampions(List<Integer> championIds) {
        Map<Integer, WinRateMapWithTotal<Role, WinRateByChampionRole>> championRoleWinRates = championWinRateService.getWinRatesByChampionRole(QueueType.standardSRMatchTypes);
        HashMap<Role, Integer> teamRoleCounts = new HashMap<>();
        championIds.stream().forEach((champId) -> {
            WinRateMapWithTotal<Role, WinRateByChampionRole> roleWinRatesForChamp = championRoleWinRates.get(champId);
            Optional<WinRateByChampionRole> mostPlayedRoleWinRate = roleWinRatesForChamp.getWinRateData().values().stream().max(BaseWinRate::comparePlayedCount);
            if (mostPlayedRoleWinRate.isPresent()) {
                if (!teamRoleCounts.containsKey(mostPlayedRoleWinRate.get().getRole())) {
                    teamRoleCounts.put(mostPlayedRoleWinRate.get().getRole(), 1);
                } else {
                    teamRoleCounts.put(mostPlayedRoleWinRate.get().getRole(), teamRoleCounts.get(mostPlayedRoleWinRate.get().getRole()) + 1);
                }
            }
        });
        return teamRoleCounts;
    }

    public ChampionSuggestionContext getSuggestionContextFromRoles(WinRateMapWithTotal<Role, WinRateByChampionRole> winRateData, Integer potentialChampPickId, Set<Role> suggestedRoles, Set<Role> discouragedRoles) {
        ChampionSuggestionContext suggestionContext = new ChampionSuggestionContext(ChampionSuggestionReason.TeamCompRole);
        List<SupportingSuggestionInfo<Role>> roleSupportingInfo = new LinkedList<>();

        winRateData.getWinRateData().keySet().stream().forEach(role -> {
            if (winRateData.isSignificantlyHigherWinRate(role)) {
                roleSupportingInfo.add(new SupportingSuggestionInfo(SupportingSuggestionReason.PositiveEffect, role));
            } else if (winRateData.isSignificantlyLowerWinRate(role)) {
                roleSupportingInfo.add(new SupportingSuggestionInfo(SupportingSuggestionReason.NegativeEffect, role));
            } else {
                roleSupportingInfo.add(new SupportingSuggestionInfo(SupportingSuggestionReason.NeutralEffect, role));
            }
        });
        // If champion plays any suggested role well, suggest the pick
        if (roleSupportingInfo.stream().anyMatch((info) -> suggestedRoles.contains(info.getSupportingInfo()) && info.getSupportingReason() == SupportingSuggestionReason.PositiveEffect)) {
            suggestionContext.setScore(1.0);
        } // If champion usually plays a suggested role, half-heartedly suggest it
        else if (suggestedRoles.stream().anyMatch((role) -> winRateData.hasDataFor(role) && winRateData.getWinRateData().get(role).getPlayedCount() / winRateData.getTotalPlayedCount() > 0.5 && !winRateData.isSignificantlyLowerWinRate(role))) {
            suggestionContext.setScore(0.75);
        } // If champion only plays discouraged roles well, discourage this pick
        else if (roleSupportingInfo.stream().anyMatch((info) -> info.getSupportingReason() == SupportingSuggestionReason.PositiveEffect && discouragedRoles.contains(info.getSupportingInfo()))
                && roleSupportingInfo.stream().anyMatch((info) -> info.getSupportingReason() == SupportingSuggestionReason.PositiveEffect && !discouragedRoles.contains(info.getSupportingInfo()))) {
            suggestionContext.setScore(0.0);
        } else {
            suggestionContext.setScore(0.5);
        }
        suggestionContext.setSupportingInfo(roleSupportingInfo.stream().filter((info) -> suggestedRoles.contains(info.getSupportingInfo()) || discouragedRoles.contains(info.getSupportingInfo())).collect(Collectors.toList()));
        return suggestionContext;
    }
}
