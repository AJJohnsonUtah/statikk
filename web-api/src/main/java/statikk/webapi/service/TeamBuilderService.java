/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.webapi.service;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import statikk.domain.entity.enums.Lane;
import statikk.domain.riotapi.model.ChampionMasteryDto;
import statikk.domain.riotapi.model.QueueType;
import statikk.domain.riotapi.model.Region;
import statikk.domain.riotapi.model.SummonerDto;
import statikk.domain.stats.model.WinRateByChampionLane;
import statikk.domain.stats.model.WinRateMapWithTotal;
import statikk.domain.stats.service.ChampionWinRateService;
import statikk.webapi.controller.SummonerDataController;
import statikk.webapi.model.ChampionSuggestion;
import statikk.webapi.model.ChampionSuggestionReason;
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
    SummonerDataController summonerDataController;

    public Map<Integer, ChampionSuggestion> getChampionSuggestions(TeamBuilderProgressData teamBuilderProgress) {

        Map<Integer, ChampionSuggestion> championSuggestions = new HashMap<>();

        updateSuggestionsBasedOnChampionMastery(teamBuilderProgress, championSuggestions);
        updateSuggestionsBasedOnLanePerformance(teamBuilderProgress, championSuggestions);

        return championSuggestions;
    }

    private void updateSuggestionsBasedOnChampionMastery(TeamBuilderProgressData teamBuilderProgress, Map<Integer, ChampionSuggestion> championSuggestions) {
        SummonerDto currentUser = summonerDataController.getSummonerByName(teamBuilderProgress.getSummonerName(), Region.NA.name());

        if (currentUser == null) {
            return;
        }
        List<ChampionMasteryDto> championMasteries = summonerDataController.getChampionMastery(currentUser.getId(), Region.NA.name());

        if (championMasteries == null) {
            return;
        }

        for (ChampionMasteryDto championMastery : championMasteries) {
            switch (championMastery.getChampionLevel()) {
                case 7:
                    putOrUpdate(championSuggestions, championMastery.getChampionId(), ChampionSuggestionReason.ChampionMastery, 1.0);
                    break;
                case 6:
                    putOrUpdate(championSuggestions, championMastery.getChampionId(), ChampionSuggestionReason.ChampionMastery, 0.9);
                    break;
                case 5:
                    putOrUpdate(championSuggestions, championMastery.getChampionId(), ChampionSuggestionReason.ChampionMastery, 0.75);
                    break;
                case 4:
                    putOrUpdate(championSuggestions, championMastery.getChampionId(), ChampionSuggestionReason.ChampionMastery, 0.5);
                    break;
                case 3:
                    putOrUpdate(championSuggestions, championMastery.getChampionId(), ChampionSuggestionReason.ChampionMastery, 0.25);
                    break;
                case 2:
                    putOrUpdate(championSuggestions, championMastery.getChampionId(), ChampionSuggestionReason.ChampionMastery, 0.1);
                    break;
                default:
                    putOrUpdate(championSuggestions, championMastery.getChampionId(), ChampionSuggestionReason.ChampionMastery, 0.0);
            }
        }
    }

    private void updateSuggestionsBasedOnLanePerformance(TeamBuilderProgressData teamBuilderProgress, Map<Integer, ChampionSuggestion> championSuggestions) {
        Map<Integer, WinRateMapWithTotal<Lane, WinRateByChampionLane>> champLaneData
                = championWinRateService.getWinRatesByChampionLane(EnumSet.of(QueueType.RANKED_FLEX_SR, QueueType.RANKED_SOLO_5x5, QueueType.RANKED_PREMADE_5x5));

        champLaneData.keySet().stream().forEach((champId) -> {
            // Analyze each champion
            WinRateMapWithTotal<Lane, WinRateByChampionLane> champWinRateData = champLaneData.get(champId);
            // If the champion has matches played in this lane and at least one other
            if (champWinRateData.getWinRateData().containsKey(teamBuilderProgress.getLane()) && champWinRateData.getWinRateData().size() > 1) {
                // Perform a test to determine if the win rate in this lane is statistically higher than overall
                if (champWinRateData.isSignificantlyHigherWinRate(teamBuilderProgress.getLane())) {
                    // If it is, then give it an extra positive score
                    putOrUpdate(championSuggestions, champId, ChampionSuggestionReason.ChampionLaneProficiency, 1);
                } // Perform a test to determine if the win rate in this lane is statistically lower than overall
                else if (champWinRateData.isSignificantlyLowerWinRate(teamBuilderProgress.getLane())) {
                    // If it is, then give it an extra negative score
                    putOrUpdate(championSuggestions, champId, ChampionSuggestionReason.ChampionLaneProficiency, 0.0);
                } else {
                    // If it is not, then the lane does not seem to have a significant impact; 
                    putOrUpdate(championSuggestions, champId, ChampionSuggestionReason.ChampionLaneProficiency, 0.5);
                }
            }
        });
    }

    private void putOrUpdate(Map<Integer, ChampionSuggestion> championSuggestions, Integer championId, ChampionSuggestionReason reason, double scoreToAdd) {
        if (championSuggestions.containsKey(championId)) {
            championSuggestions.get(championId).addScore(reason, scoreToAdd);
        } else {
            championSuggestions.put(championId, new ChampionSuggestion(championId, reason, scoreToAdd));
        }
    }
}
