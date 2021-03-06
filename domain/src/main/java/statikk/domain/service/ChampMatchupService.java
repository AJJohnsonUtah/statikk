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
import statikk.domain.stats.model.CorrelationInfo;
import statikk.domain.stats.model.WinRateByChampion;
import statikk.domain.stats.model.WinRateByChampionPair;
import statikk.domain.stats.model.WinRateMapWithTotal;
import statikk.domain.stats.service.StatisticsUtil;

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
    public Map<Integer, WinRateMapWithTotal<Integer, WinRateByChampionPair>> getAllMatchupsByChampion(Iterable<QueueType> matchTypes) {
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

    public CorrelationInfo getCorrelationInfoForChampions(Integer championId1,
            Integer championId2,
            WinRateMapWithTotal<Integer, WinRateByChampionPair> champ1Data,
            WinRateMapWithTotal<Integer, WinRateByChampionPair> champ2Data,
            Iterable<QueueType> matchTypes) {
        double n1WinCountWithout2 = champ1Data.getTotalWinCount() - champ1Data.getWinRateData().get(championId2).getWinCount();
        double n1PlayedCountWithout2 = champ1Data.getTotalPlayedCount() - champ1Data.getWinRateData().get(championId2).getPlayedCount();
        double n2WinCountWithout1 = champ2Data.getTotalWinCount() - champ2Data.getWinRateData().get(championId1).getWinCount();
        double n2PlayedCountWithout1 = champ2Data.getTotalPlayedCount() - champ2Data.getWinRateData().get(championId1).getPlayedCount();

        double p1Without2 = n1WinCountWithout2 / n1PlayedCountWithout2;
        double p2Without1 = n2WinCountWithout1 / n2PlayedCountWithout1;

        // Expected win rate for champ1 vs champ2
        double p1Vs2Expected = p1Without2 / (p1Without2 + p2Without1);

        WinRateByChampionPair teamupData = champ1Data.getWinRateData().get(championId2);

        if (StatisticsUtil.isSignificantlyHigherProportion(teamupData.getWinRate(), teamupData.getPlayedCount(), p1Vs2Expected, n1PlayedCountWithout2 + n2PlayedCountWithout1)) {
            return CorrelationInfo.PositiveEffect;
        } else if (StatisticsUtil.isSignificantlyLowerProportion(teamupData.getWinRate(), teamupData.getPlayedCount(), p1Vs2Expected, n1PlayedCountWithout2 + n2PlayedCountWithout1)) {
            return CorrelationInfo.NegativeEffect;
        }
        return CorrelationInfo.NeutralEffect;
    }
}
