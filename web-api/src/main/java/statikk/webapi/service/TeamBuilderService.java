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
import statikk.domain.stats.model.CorrelationInfo;
import statikk.domain.stats.model.WinRateByChampionPair;
import statikk.webapi.model.TeamBuilderProgressData;
import statikk.webapi.model.TeamBuilderSuggestion;

/**
 * The TeamBuilderService is the main service used for the Team Builder feature.
 * It uses historical data gathered from the data miner to generate scores and
 * "suggestions" for users to help determine which champion to select during
 * Champion Select
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

    /**
     * Used to determine all champion suggestions and associated context, based
     * on the provided teamBuilderProgress
     *
     * @param teamBuilderProgress
     * @return
     */
    public TeamBuilderSuggestion getTeamBuilderSuggestion(TeamBuilderProgressData teamBuilderProgress) {

        TeamBuilderSuggestion teamBuilderSuggestion = new TeamBuilderSuggestion();

        updateSuggestionsBasedOnChampionMastery(teamBuilderProgress, teamBuilderSuggestion);
        updateSuggestionsBasedOnLanePerformance(teamBuilderProgress, teamBuilderSuggestion);
        updateSuggestionsBasedOnTeamups(teamBuilderProgress, teamBuilderSuggestion);
        updateSuggestionsBasedOnMatchups(teamBuilderProgress, teamBuilderSuggestion);
        updateSuggestionsBasedOnTeamComps(teamBuilderProgress, teamBuilderSuggestion);

        // Return a Collection so that it is more efficient for the front-end to process
        return teamBuilderSuggestion;
    }

    /**
     * For each champion, the champion will be given a score based on the user's
     * Champion Mastery (a measure of how many games they have played with a
     * champion, and how well they have performed). Higher Champion Mastery will
     * result in higher suggestion scores
     *
     * @param teamBuilderProgress
     * @param teamBuilderSuggestion
     */
    public void updateSuggestionsBasedOnChampionMastery(TeamBuilderProgressData teamBuilderProgress, TeamBuilderSuggestion teamBuilderSuggestion) {
        // If no username was provided, don't continue
        if (teamBuilderProgress.getSummonerName() == null || teamBuilderProgress.getSummonerName().isEmpty()) {
            return;
        }
        // If the provided username wasn't found in Riot's API, don't continue
        SummonerDto currentUser = summonerDataController.getSummonerByName(teamBuilderProgress.getSummonerName(), Region.NA.name());
        if (currentUser == null) {
            return;
        }
        // If the user does not have any Champion Mastery data, don't continue
        List<ChampionMasteryDto> championMasteries = summonerDataController.getChampionMastery(currentUser.getId(), Region.NA.name());
        if (championMasteries == null) {
            return;
        }
        // This set is used to keep track of which champions do not have mastery data
        Set<Integer> champsWithoutMasterySuggestions = new HashSet<>(riotApiService.getStaticChampionsDataObject(Region.NA).getData().keySet());
        // Determine the score for each champion mastery, based on mastery level
        championMasteries.stream().forEach((championMastery) -> {
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
            champsWithoutMasterySuggestions.remove(championMastery.getChampionId());
            putOrUpdate(teamBuilderSuggestion.getChampionSuggestionsMap(), championMastery.getChampionId(), suggestionContext);
        });
        // For remaining champions, they do not have champion mastery info (meaning this user has never played them), so give them the lowest score
        champsWithoutMasterySuggestions.stream().forEach((champId) -> {
            ChampionSuggestionContext suggestionContext = new ChampionSuggestionContext(ChampionSuggestionReason.ChampionMastery, 0.0);
            putOrUpdate(teamBuilderSuggestion.getChampionSuggestionsMap(), champId, suggestionContext);
        });
    }

    /**
     * For each champion, a score will be assigned based on how well they
     * perform in the specified lane, relative to the champion's performance in
     * the other lanes. Better lane performance will mean a better score.
     *
     * @param teamBuilderProgress
     * @param teamBuilderSuggestion
     */
    public void updateSuggestionsBasedOnLanePerformance(TeamBuilderProgressData teamBuilderProgress, TeamBuilderSuggestion teamBuilderSuggestion) {
        // Fetch win rate data for standard match types, grouped by champion and lane
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
                // If no lane data was found for the champion a determination cannot be made; give them an average score
                suggestionContext.setScore(0.5);
            }
            putOrUpdate(teamBuilderSuggestion.getChampionSuggestionsMap(), champId, suggestionContext);
        });
    }

    /**
     * For each champion, a score will be determined based on the currently
     * selected allied champions in the teamBuilderProgress. Better performance
     * with a greater number of selected allies will increase the score, while
     * poor performance with allies will yield a lower score.
     *
     * @param teamBuilderProgress
     * @param teamBuilderSuggestion
     */
    public void updateSuggestionsBasedOnTeamups(TeamBuilderProgressData teamBuilderProgress, TeamBuilderSuggestion teamBuilderSuggestion) {
        // If no allied champions have been selected yet, don't continue
        if (teamBuilderProgress.getAllyChampionIds() == null || teamBuilderProgress.getAllyChampionIds().isEmpty()) {
            return;
        }
        // Assemble the lists of "supporting info" for each potential champion, indicating the relative performance with each ally
        Map<Integer, List<SupportingSuggestionInfo<Integer>>> suggestionInfo = new HashMap<>();
        Map<Integer, WinRateMapWithTotal<Integer, WinRateByChampionPair>> allTeamupWinRates = champTeamupService.getAllTeamupsByChampion(QueueType.standardSRMatchTypes);

        teamBuilderProgress.getAllyChampionIds().stream().forEach((allyChampId) -> {
            // Get win rate data associated with the current ally champion, and the potential teammates
            WinRateMapWithTotal<Integer, WinRateByChampionPair> allyWinRates = allTeamupWinRates.get(allyChampId);
            for (Integer potentialChampPickId : allyWinRates.getWinRateData().keySet()) {
                WinRateMapWithTotal<Integer, WinRateByChampionPair> potentialPickWinRates = allTeamupWinRates.get(potentialChampPickId);
                if (!suggestionInfo.containsKey(potentialChampPickId)) {
                    suggestionInfo.put(potentialChampPickId, new LinkedList<>());
                }

                CorrelationInfo teamupCorrelation = champTeamupService.getCorrelationInfoForChampions(potentialChampPickId, allyChampId, potentialPickWinRates, allyWinRates, QueueType.standardSRMatchTypes);
                suggestionInfo.get(potentialChampPickId).add(new SupportingSuggestionInfo(teamupCorrelation, allyChampId));
            }
        });
        // Using the supporting data from above, determine a score for each potential champion pick
        suggestionInfo.keySet().stream().forEach((potentialChampPickId) -> {
            // +1 for each positive effect, -1 for each negative effect, then normalize to between 0 and 1
            Double score = (suggestionInfo.get(potentialChampPickId).stream().map(info -> info.getSupportingReason().getScore()).reduce(0.0, (a, b) -> a + b) / suggestionInfo.get(potentialChampPickId).size() + 1.0) / 2.0;
            ChampionSuggestionContext<Integer> suggestionContext = new ChampionSuggestionContext<>(ChampionSuggestionReason.TeamSynergy, score, suggestionInfo.get(potentialChampPickId));
            putOrUpdate(teamBuilderSuggestion.getChampionSuggestionsMap(), potentialChampPickId, suggestionContext);
        });
    }

    /**
     * For each champion, a score will be determined based on the currently
     * selected enemy champions in the teamBuilderProgress. Better performance
     * with a greater number of selected enemies will increase the score, while
     * poor performance with enemies will yield a lower score.
     *
     * @param teamBuilderProgress
     * @param teamBuilderSuggestion
     */
    public void updateSuggestionsBasedOnMatchups(TeamBuilderProgressData teamBuilderProgress, TeamBuilderSuggestion teamBuilderSuggestion) {
        // If no enemy champions have been selected yet, don't continue
        if (teamBuilderProgress.getEnemyChampionIds() == null || teamBuilderProgress.getEnemyChampionIds().isEmpty()) {
            return;
        }
        // Assemble the lists of "supporting info" for each potential champion, indicating the relative performance with each ally
        Map<Integer, List<SupportingSuggestionInfo<Integer>>> suggestionInfo = new HashMap<>();

        Map<Integer, WinRateMapWithTotal<Integer, WinRateByChampionPair>> allTeamupWinRates = champMatchupService.getAllMatchupsByChampion(QueueType.standardSRMatchTypes);

        teamBuilderProgress.getEnemyChampionIds().stream().forEach((enemyChampId) -> {
            WinRateMapWithTotal<Integer, WinRateByChampionPair> enemyWinRates = allTeamupWinRates.get(enemyChampId);
            for (Integer potentialChampPickId : enemyWinRates.getWinRateData().keySet()) {
                WinRateMapWithTotal<Integer, WinRateByChampionPair> potentialPickWinRates = allTeamupWinRates.get(potentialChampPickId);
                if (!suggestionInfo.containsKey(potentialChampPickId)) {
                    suggestionInfo.put(potentialChampPickId, new LinkedList<>());
                }

                CorrelationInfo teamupCorrelation = champMatchupService.getCorrelationInfoForChampions(potentialChampPickId, enemyChampId, potentialPickWinRates, enemyWinRates, QueueType.standardSRMatchTypes);
                suggestionInfo.get(potentialChampPickId).add(new SupportingSuggestionInfo(teamupCorrelation, enemyChampId));
            }
        });
        // Using the supporting data from above, determine a score for each potential champion pick
        suggestionInfo.keySet().stream().forEach((potentialChampPickId) -> {
            // +1 for each positive effect, -1 for each negative effect, then normalize to between 0 and 1
            Double score = (suggestionInfo.get(potentialChampPickId).stream().map(info -> info.getSupportingReason().getScore()).reduce(0.0, (a, b) -> a + b) / suggestionInfo.get(potentialChampPickId).size() + 1.0) / 2.0;
            ChampionSuggestionContext<Integer> suggestionContext = new ChampionSuggestionContext<>(ChampionSuggestionReason.EnemyCounter, score, suggestionInfo.get(potentialChampPickId));
            putOrUpdate(teamBuilderSuggestion.getChampionSuggestionsMap(), potentialChampPickId, suggestionContext);
        });
    }

    private void putOrUpdate(Map<Integer, ChampionSuggestion> teamBuilderSuggestion, Integer championId, ChampionSuggestionContext suggestionContext) {
        if (teamBuilderSuggestion.containsKey(championId)) {
            teamBuilderSuggestion.get(championId).addSuggestionContext(suggestionContext);
        } else {
            teamBuilderSuggestion.put(championId, new ChampionSuggestion(championId, suggestionContext));
        }
    }

    /**
     * For each champion, a score is determined based on the currently selected
     * champions, the predicted roles of those champions, which roles would help
     * round out the team/counter the enemy, and ultimately how well potential
     * picks perform in those roles. A higher score means that a champion plays
     * a suggested role well.
     *
     * @param teamBuilderProgress
     * @param teamBuilderSuggestion
     */
    public void updateSuggestionsBasedOnTeamComps(TeamBuilderProgressData teamBuilderProgress, TeamBuilderSuggestion teamBuilderSuggestion) {
        Map<Integer, WinRateMapWithTotal<Role, WinRateByChampionRole>> allChampionRoleWinRates = championWinRateService.getWinRatesByChampionRole(QueueType.standardSRMatchTypes);
        HashMap<Role, Integer> allyTeam = getPredictedRoleCountsFromChampions(teamBuilderProgress.getAllyChampionIds());
        HashMap<Role, Integer> enemyTeam = getPredictedRoleCountsFromChampions(teamBuilderProgress.getEnemyChampionIds());

        WinRateMapWithTotal<Role, BaseWinRate> roleWinRates = new WinRateMapWithTotal(teamCompService.findWinRatesByRoleForLaneWithComps(teamBuilderProgress.getLane(), allyTeam, enemyTeam));

        // Determine which roles do well/poorly with given team comps
        Set<Role> suggestedRoles = roleWinRates.getWinRateData().keySet().stream().filter((role) -> roleWinRates.hasDataFor(role) && roleWinRates.isSignificantlyHigherWinRate(role)).collect(Collectors.toSet());
        Set<Role> discouragedRoles = roleWinRates.getWinRateData().keySet().stream().filter((role) -> roleWinRates.hasDataFor(role) && roleWinRates.isSignificantlyLowerWinRate(role)).collect(Collectors.toSet());
        teamBuilderSuggestion.setSuggestedRoles(suggestedRoles);
        teamBuilderSuggestion.setDiscouragedRoles(discouragedRoles);

        // If there are no roles to suggest/discourage, do nothing
        if (suggestedRoles.isEmpty() && discouragedRoles.isEmpty()) {
            return;
        }
        allChampionRoleWinRates.keySet().stream().forEach((champId) -> {
            putOrUpdate(teamBuilderSuggestion.getChampionSuggestionsMap(), champId, getSuggestionContextFromRoles(allChampionRoleWinRates.get(champId), champId, suggestedRoles, discouragedRoles));
        });
    }

    /**
     * Converts a list of championIds to a map of Roles, mapped to the number of
     * champions that were predicted to select that role
     *
     * @param championIds
     * @return
     */
    public HashMap<Role, Integer> getPredictedRoleCountsFromChampions(List<Integer> championIds) {
        Map<Integer, WinRateMapWithTotal<Role, WinRateByChampionRole>> championRoleWinRates = championWinRateService.getWinRatesByChampionRole(QueueType.standardSRMatchTypes);
        HashMap<Role, Integer> teamRoleCounts = new HashMap<>();
        championIds.stream().forEach((champId) -> {
            //Fetch the data for this specific champion, grouped by role
            WinRateMapWithTotal<Role, WinRateByChampionRole> roleWinRatesForChamp = championRoleWinRates.get(champId);
            // Determine which role is played most often, by finding the max playedCount
            Optional<WinRateByChampionRole> mostPlayedRoleWinRate = roleWinRatesForChamp.getWinRateData().values().stream().max(BaseWinRate::comparePlayedCount);
            // Count the roles in the map
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

    /**
     * Returns the suggestion context based on TeamComp/Roles associated with
     * the specified champion, using the provided win rate data and
     * suggested/discouraged roles.
     *
     * @param winRateData
     * @param potentialChampPickId
     * @param suggestedRoles
     * @param discouragedRoles
     * @return
     */
    public ChampionSuggestionContext getSuggestionContextFromRoles(WinRateMapWithTotal<Role, WinRateByChampionRole> winRateData, Integer potentialChampPickId, Set<Role> suggestedRoles, Set<Role> discouragedRoles) {
        ChampionSuggestionContext suggestionContext = new ChampionSuggestionContext(ChampionSuggestionReason.TeamCompRole);
        List<SupportingSuggestionInfo<Role>> roleSupportingInfo = new LinkedList<>();

        // Determine what effect each role has for the given champion
        winRateData.getWinRateData().keySet().stream().forEach(role -> {
            if (winRateData.isSignificantlyHigherWinRate(role)) {
                roleSupportingInfo.add(new SupportingSuggestionInfo(CorrelationInfo.PositiveEffect, role));
            } else if (winRateData.isSignificantlyLowerWinRate(role)) {
                roleSupportingInfo.add(new SupportingSuggestionInfo(CorrelationInfo.NegativeEffect, role));
            } else {
                roleSupportingInfo.add(new SupportingSuggestionInfo(CorrelationInfo.NeutralEffect, role));
            }
        });
        // If champion plays any suggested role well, suggest the pick
        if (roleSupportingInfo.stream().anyMatch((info) -> suggestedRoles.contains(info.getSupportingInfo()) && info.getSupportingReason() == CorrelationInfo.PositiveEffect)) {
            suggestionContext.setScore(1.0);
        } // If champion usually plays a suggested role, half-heartedly suggest it
        else if (suggestedRoles.stream().anyMatch((role) -> winRateData.hasDataFor(role) && winRateData.getWinRateData().get(role).getPlayedCount() / winRateData.getTotalPlayedCount() > 0.5 && !winRateData.isSignificantlyLowerWinRate(role))) {
            suggestionContext.setScore(0.75);
        } // If champion only plays discouraged roles well, discourage this pick
        else if (roleSupportingInfo.stream().anyMatch((info) -> info.getSupportingReason() == CorrelationInfo.PositiveEffect && discouragedRoles.contains(info.getSupportingInfo()))
                && roleSupportingInfo.stream().anyMatch((info) -> info.getSupportingReason() == CorrelationInfo.PositiveEffect && !discouragedRoles.contains(info.getSupportingInfo()))) {
            suggestionContext.setScore(0.0);
        } // If champion does not meet any of the above conditions, give an average score
        else {
            suggestionContext.setScore(0.5);
        }
        // Only include supporting info regarding encouraged/discouraged roles
        suggestionContext.setSupportingInfo(roleSupportingInfo.stream().filter((info) -> suggestedRoles.contains(info.getSupportingInfo())).collect(Collectors.toList()));
        return suggestionContext;
    }
}
