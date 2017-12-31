/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.stats.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import statikk.domain.dao.ChampSpecWinRateDao;
import statikk.domain.entity.LolVersion;
import statikk.domain.entity.enums.Lane;
import statikk.domain.entity.enums.Role;
import statikk.domain.riotapi.model.QueueType;
import statikk.domain.riotapi.model.Rank;
import statikk.domain.service.LolVersionService;
import statikk.domain.stats.model.WinRateByChampion;
import statikk.domain.stats.model.WinRateByChampionLane;
import statikk.domain.stats.model.WinRateByMatchType;
import statikk.domain.stats.model.WinRateByLane;
import statikk.domain.stats.model.WinRateByRole;
import statikk.domain.stats.model.WinRateMapWithTotal;

/**
 *
 * @author AJ
 */
@Service
public class ChampionWinRateService {

    @Autowired
    ChampSpecWinRateDao champSpecWinRateDao;

    @Autowired
    LolVersionService lolVersionService;

    public List<WinRateByChampion> getAllChampionWinRates(QueueType queueType, Rank rank, Lane lane, LolVersion lolVersion) {
        lolVersion = lolVersionService.findOrCreate(lolVersion);

        if (rank == null) {
            if (lane == null) {
                // Get win rates for all ranks/lanes
                return champSpecWinRateDao.findWinRatesGroupedByChampionId(queueType, lolVersion);

            } else {
                // Get win rates for all ranks, specific lane
                return champSpecWinRateDao.findWinRatesGroupedByChampionId(queueType, lane, lolVersion);

            }
        } else {
            if (lane == null) {
                // Get win rates for specific rank, all lanes.
                return champSpecWinRateDao.findWinRatesGroupedByChampionId(queueType, rank, lolVersion);

            } else {
                // Get win rates for specific rank, specific lane
                return champSpecWinRateDao.findWinRatesGroupedByChampionId(queueType, rank, lane, lolVersion);

            }
        }
    }

    public Map<QueueType, WinRateByMatchType> getChampionWinRates(int championId, LolVersion lolVersion) {
        lolVersion = lolVersionService.findOrCreate(lolVersion);
        // Get win rates for all ranks/lanes
        return champSpecWinRateDao.findChampionWinRatesGroupedByMatchType(championId, lolVersion)
                .stream().collect(Collectors.toMap(WinRateByMatchType::getMatchType, p -> p));
    }

    public Map<Lane, WinRateByLane> getChampionLaneWinRates(int championId, Iterable<QueueType> matchTypes, LolVersion lolVersion) {
        lolVersion = lolVersionService.findOrCreate(lolVersion);
        // Get win rates for all ranks/lanes
        return champSpecWinRateDao.findChampionWinRatesGroupedByLane(championId, matchTypes, lolVersion)
                .stream().filter(p -> p.getLane() != null).collect(Collectors.toMap(WinRateByLane::getLane, p -> p));
    }

    public Map<Lane, WinRateByLane> getChampionLaneWinRates(int championId, Role role, Iterable<QueueType> matchTypes, LolVersion lolVersion) {
        lolVersion = lolVersionService.findOrCreate(lolVersion);
        // Get win rates for all ranks/lanes
        return champSpecWinRateDao.findChampionWinRatesByRoleGroupedByLane(championId, role, matchTypes, lolVersion)
                .stream().filter(p -> p.getLane() != null).collect(Collectors.toMap(WinRateByLane::getLane, p -> p));
    }

    public Map<Role, WinRateByRole> getChampionRoleWinRates(int championId, Iterable<QueueType> matchTypes, LolVersion lolVersion) {
        lolVersion = lolVersionService.findOrCreate(lolVersion);
        // Get win rates for all ranks/lanes
        return champSpecWinRateDao.findChampionWinRatesGroupedByRole(championId, matchTypes, lolVersion)
                .stream().filter(p -> p.getRole() != null).collect(Collectors.toMap(WinRateByRole::getRole, p -> p));
    }

    public Map<Role, WinRateByRole> getChampionRoleWinRates(int championId, Lane lane, Iterable<QueueType> matchTypes, LolVersion lolVersion) {
        lolVersion = lolVersionService.findOrCreate(lolVersion);
        // Get win rates for all ranks/lanes
        return champSpecWinRateDao.findChampionWinRatesByLaneGroupedByRole(championId, lane, matchTypes, lolVersion)
                .stream().filter(p -> p.getRole() != null).collect(Collectors.toMap(WinRateByRole::getRole, p -> p));
    }
    
    
    @Cacheable("lane-win-rates")
    public Map<Integer, WinRateMapWithTotal<Lane, WinRateByChampionLane>> getWinRatesByChampionLane(Iterable<QueueType> matchTypes) {
        List<LolVersion> recentVersions = lolVersionService.findRecentVersions();
        Map<Integer, Map<Lane,WinRateByChampionLane>> winRates = new HashMap<>();
        champSpecWinRateDao.findWinRatesByGroupedByChampionLane(matchTypes, recentVersions).forEach((winRateByChampionLane) -> {
           if(!winRates.containsKey(winRateByChampionLane.getChampionId())) {
              winRates.put(winRateByChampionLane.getChampionId(), new HashMap<>());
           } 
           winRates.get(winRateByChampionLane.getChampionId()).put(winRateByChampionLane.getLane(), winRateByChampionLane);
        });
        return winRates.keySet().stream().collect(Collectors.toMap(p -> p, p -> new WinRateMapWithTotal(winRates.get(p))));
    }
}
