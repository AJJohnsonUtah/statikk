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
import statikk.domain.dao.ChampTeamupDao;
import statikk.domain.entity.ChampTeamup;
import statikk.domain.entity.ChampTeamupPK;
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
public class ChampTeamupService extends BaseWinRateService<ChampTeamup, ChampTeamupPK> {

    @Autowired
    LolVersionService lolVersionService;

    @Autowired
    ChampTeamupDao champTeamupDao;

    @Override
    public ChampTeamup find(ChampTeamup champTeamup) {
        return dao.findOne(champTeamup.getChampTeamupPK());
    }

    @Cacheable("teamup-win-rates")
    public WinRateMapWithTotal<Integer, WinRateByChampion> getWinRatesByChampion(Integer championId, Iterable<QueueType> matchTypes) {
        List<LolVersion> recentVersions = lolVersionService.findRecentVersions();
        Map<Integer, WinRateByChampion> winRates = champTeamupDao
                .findWinRatesByGroupedByAllyChampion(championId, matchTypes, recentVersions).stream().collect(Collectors.toMap(p -> p.getChampionId(), p -> p));
        return new WinRateMapWithTotal(winRates);
    }

    @Cacheable("teamup-win-rates")
    public Map<Integer, WinRateMapWithTotal<Integer, WinRateByChampionPair>> getAllTeamupsByChampion(Iterable<QueueType> matchTypes) {
        List<LolVersion> recentVersions = lolVersionService.findRecentVersions();
        Map<Integer, Map<Integer, WinRateByChampionPair>> winRates = new HashMap<>();

        champTeamupDao.findWinRatesByGroupedByAllyAndOtherAllyChampion(matchTypes, recentVersions).stream().forEach(winRate -> {
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

        // Expected win rate for champ1 with champ2
        double pExpected = (p1Without2 + p2Without1) / 2;

        WinRateByChampionPair teamupData = champ1Data.getWinRateData().get(championId2);

        if (StatisticsUtil.isSignificantlyHigherProportion(teamupData.getWinRate(), teamupData.getPlayedCount(), pExpected, n1PlayedCountWithout2 + n2PlayedCountWithout1)) {
            return CorrelationInfo.PositiveEffect;
        } else if (StatisticsUtil.isSignificantlyLowerProportion(teamupData.getWinRate(), teamupData.getPlayedCount(), pExpected, n1PlayedCountWithout2 + n2PlayedCountWithout1)) {
            return CorrelationInfo.NegativeEffect;
        }
        return CorrelationInfo.NeutralEffect;
    }

}
