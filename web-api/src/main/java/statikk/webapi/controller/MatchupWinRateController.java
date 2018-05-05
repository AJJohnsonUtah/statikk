package statikk.webapi.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import statikk.domain.riotapi.model.QueueType;
import statikk.domain.service.ChampMatchupService;
import statikk.domain.stats.model.CorrelationInfo;
import statikk.domain.stats.model.WinRateByChampionPair;
import statikk.domain.stats.model.WinRateMapWithTotal;
import statikk.webapi.model.SignificantMatchups;

/**
 *
 * @author Grann
 */
@Controller
@RequestMapping(value = "/api/matchup")
public class MatchupWinRateController {

    @Autowired
    ChampMatchupService champMatchupService;

    /**
     * Returns lists of champion ids that the specified champion either counters
     * or is countered by
     *
     * @param championId
     * @return
     */
    @ResponseBody
    @Cacheable("significant-matchups")
    @RequestMapping(value = "/significant/by-champion-id/{championId}", method = RequestMethod.GET)
    public SignificantMatchups getSignificantMatchupsForChampion(@PathVariable("championId") int championId) {
        SignificantMatchups matchups = new SignificantMatchups();
        Map<Integer, WinRateMapWithTotal<Integer, WinRateByChampionPair>> allMatchupsByChampion = champMatchupService.getAllMatchupsByChampion(QueueType.standardSRMatchTypes);
        if (allMatchupsByChampion.containsKey(championId)) {
            WinRateMapWithTotal<Integer, WinRateByChampionPair> champWinRateData = allMatchupsByChampion.get(championId);
            champWinRateData.getWinRateData().keySet().stream().forEach(enemyChampId -> {
                WinRateMapWithTotal<Integer, WinRateByChampionPair> enemyWinRateData = allMatchupsByChampion.get(enemyChampId);
                CorrelationInfo matchupCorrelation = champMatchupService.getCorrelationInfoForChampions(championId, enemyChampId, champWinRateData, enemyWinRateData, QueueType.standardSRMatchTypes);
                if (matchupCorrelation == CorrelationInfo.PositiveEffect) {
                    matchups.getCounterToChampions().add(enemyChampId);
                } else if (matchupCorrelation == CorrelationInfo.NegativeEffect) {
                    matchups.getCounteredByChampions().add(enemyChampId);
                }
            });
        }
        return matchups;
    }
}
