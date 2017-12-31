/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.service;

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
    public WinRateMapWithTotal<Integer, WinRateByChampion> getWinRatesByChampionLane(Integer championId, Iterable<QueueType> matchTypes) {
        List<LolVersion> recentVersions = lolVersionService.findRecentVersions();
        Map<Integer, WinRateByChampion> winRates = champMatchupDao
                .findWinRatesByGroupedByEnemyChampion(championId, matchTypes, recentVersions).stream().collect(Collectors.toMap(p -> p.getChampionId(), p -> p));
        return new WinRateMapWithTotal(winRates);
    }
}
