/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.webapi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import statikk.domain.riotapi.model.ChampionMasteryDto;
import statikk.domain.riotapi.model.Region;
import statikk.domain.riotapi.model.SummonerDto;
import statikk.webapi.controller.SummonerDataController;
import statikk.webapi.model.ChampionSuggestion;
import statikk.webapi.model.TeamBuilderProgressData;

/**
 *
 * @author Grann
 */
@Service
public class TeamBuilderService {

    @Autowired
    SummonerDataController summonerDataController;

    public Map<Long, ChampionSuggestion> getChampionSuggestions(TeamBuilderProgressData teamBuilderProgess) {

        SummonerDto currentUser = summonerDataController.getSummonerByName(teamBuilderProgess.getSummonerName(), Region.NA.name());
        List<ChampionMasteryDto> championMasteries = summonerDataController.getChampionMastery(currentUser.getId(), Region.NA.name());

        Map<Long, ChampionSuggestion> championSuggestions = new HashMap<>();

        for (ChampionMasteryDto championMastery : championMasteries) {
            ChampionSuggestion suggestion = new ChampionSuggestion(championMastery.getChampionId());
            switch (championMastery.getChampionLevel()) {
                case 7:
                    suggestion.setScore(1);
                    break;
                case 6:
                    suggestion.setScore(0.9);
                    break;
                case 5:
                    suggestion.setScore(0.75);
                    break;
                case 4:
                    suggestion.setScore(0.5);
                    break;
                case 3:
                    suggestion.setScore(0.25);
                    break;
                case 2:
                    suggestion.setScore(0.1);
                    break;
                default:
                    suggestion.setScore(0);
            }
            championSuggestions.put(suggestion.getChampionId(), suggestion);
        }

        return championSuggestions;
    }

    public void (Map<Long, ChampionSuggestion> championSuggestions)
}
