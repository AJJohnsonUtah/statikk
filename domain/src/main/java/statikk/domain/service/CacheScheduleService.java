/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.service;

import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import statikk.domain.entity.LolVersion;
import statikk.domain.riotapi.model.QueueType;
import statikk.domain.riotapi.model.Region;
import statikk.domain.riotapi.service.RiotApiService;
import statikk.domain.stats.service.ChampionWinRateService;

/**
 *
 * @author Grann
 */
@Service
public class CacheScheduleService {

    @Autowired
    ChampMatchupService champMatchupService;

    @Autowired
    ChampTeamupService champTeamupService;

    @Autowired
    ChampionWinRateService championWinRateService;

    @Autowired
    LolVersionService lolVersionService;

    @Autowired
    RiotApiService riotApiService;

    @Scheduled(fixedDelay = 43200000)
    public void fillCacheWithAllChamps() {
        System.out.println("Starting the cache, yo");
        List<LolVersion> recentVersions = lolVersionService.findRecentVersions();
        Set<QueueType> matchTypes = QueueType.standardSRMatchTypes;
        riotApiService.getStaticChampionsDataObject(Region.NA).getData().values().stream().forEach((champData) -> {
            System.out.println("Caching for " + champData.getId());
            champMatchupService.getWinRatesByChampionLane(champData.getId(), matchTypes);
            champTeamupService.getWinRatesByChampionLane(champData.getId(), matchTypes);
        });
        championWinRateService.getWinRatesByChampionLane(matchTypes);
    }
}
