/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.webapi.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import statikk.domain.riotapi.model.QueueType;
import statikk.domain.service.ChampTeamupService;
import statikk.domain.stats.model.CorrelationInfo;
import statikk.domain.stats.model.WinRateByChampionPair;
import statikk.domain.stats.model.WinRateMapWithTotal;
import statikk.webapi.model.SignificantTeamups;

/**
 *
 * @author Grann
 */
@Controller
@RequestMapping(value = "/api/teamup")
public class TeamupWinRateController {

    @Autowired
    ChampTeamupService champTeamupService;

    /**
     * Returns lists of champion ids that the specified champion either counters
     * or is countered by
     *
     * @param championId
     * @return
     */
    @ResponseBody
    @Cacheable("significant-teamups")
    @RequestMapping(value = "/significant/by-champion-id/{championId}", method = RequestMethod.GET)
    public SignificantTeamups getSignificantTeamupsForChampion(@PathVariable("championId") int championId) {
        SignificantTeamups matchups = new SignificantTeamups();
        Map<Integer, WinRateMapWithTotal<Integer, WinRateByChampionPair>> allTeamupsByChampion = champTeamupService.getAllTeamupsByChampion(QueueType.standardSRMatchTypes);
        if (allTeamupsByChampion.containsKey(championId)) {
            WinRateMapWithTotal<Integer, WinRateByChampionPair> champWinRateData = allTeamupsByChampion.get(championId);
            champWinRateData.getWinRateData().keySet().stream().forEach(allyChampId -> {
                WinRateMapWithTotal<Integer, WinRateByChampionPair> allyWinRateData = allTeamupsByChampion.get(allyChampId);
                CorrelationInfo matchupCorrelation = champTeamupService.getCorrelationInfoForChampions(championId, allyChampId, champWinRateData, allyWinRateData, QueueType.standardSRMatchTypes);
                if (matchupCorrelation == CorrelationInfo.PositiveEffect) {
                    matchups.getTeamupWellWithChampions().add(allyChampId);
                } else if (matchupCorrelation == CorrelationInfo.NegativeEffect) {
                    matchups.getTeamupPoorWithChampions().add(allyChampId);
                }
            });
        }
        return matchups;
    }
}
