/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.webapi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import statikk.domain.riotapi.model.ChampionMasteryDto;
import statikk.webapi.model.ChampionSuggestion;

/**
 *
 * @author Grann
 */
@Service
public class TeamBuilderService {

    public Map<Long, ChampionSuggestion> getChampionSuggestions(List<ChampionMasteryDto> championMasteries) {
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

}
