/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import statikk.domain.dao.ChampMatchupDao;
import statikk.domain.entity.ChampMatchup;
import statikk.domain.entity.ChampMatchupPK;
import statikk.domain.entity.LolVersion;
import statikk.domain.riotapi.model.QueueType;
import statikk.domain.stats.model.WinRateByChampion;
import statikk.domain.stats.model.WinRateByChampionPair;
import statikk.domain.stats.model.WinRateMapWithTotal;

/**
 *
 * @author AJ
 */
@Service
@Transactional
public class ChampMatchupService extends BaseWinRateService<ChampMatchup, ChampMatchupPK> {

    @Autowired
    LolVersionService lolVersionService;

    @Autowired
    ChampMatchupDao champMatchupDao;

    @Override
    public ChampMatchup find(ChampMatchup champMatchup) {
        return dao.findOne(champMatchup.getChampMatchupPK());
    }

    @Cacheable("matchup-win-rates")
    public WinRateMapWithTotal<Integer, WinRateByChampion> getWinRatesByChampion(Integer championId, Iterable<QueueType> matchTypes) {
        List<LolVersion> recentVersions = lolVersionService.findRecentVersions();
        Map<Integer, WinRateByChampion> winRates = champMatchupDao
                .findWinRatesByGroupedByEnemyChampion(championId, matchTypes, recentVersions).stream().collect(Collectors.toMap(p -> p.getChampionId(), p -> p));
        return new WinRateMapWithTotal(winRates);
    }

    @Cacheable("matchup-win-rates")
    public Map<Integer, WinRateMapWithTotal<Integer, WinRateByChampion>> getAllMatchupsByChampion(Iterable<QueueType> matchTypes) {
        List<LolVersion> recentVersions = lolVersionService.findRecentVersions();
        Map<Integer, Map<Integer, WinRateByChampionPair>> winRates = new HashMap<>();

        champMatchupDao.findWinRatesByGroupedByAllyAndEnemyChampion(matchTypes, recentVersions).stream().forEach(winRate -> {
            if (!winRates.containsKey(winRate.getChampionId1())) {
                winRates.put(winRate.getChampionId1(), new HashMap<>());
            }
            winRates.get(winRate.getChampionId1()).put(winRate.getChampionId2(), winRate);
        });

        return winRates.keySet().stream().collect(Collectors.toMap((key) -> key, (key) -> new WinRateMapWithTotal(winRates.get(key))));
    }
}
