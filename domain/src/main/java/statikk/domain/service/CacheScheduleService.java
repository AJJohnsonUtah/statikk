/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.service;

import java.util.Set;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
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
        Set<QueueType> matchTypes = QueueType.standardSRMatchTypes;
        Logger.getLogger(CacheScheduleService.class.getName()).log(Logger.Level.INFO, "Beginning caching");
        championWinRateService.getWinRatesByChampionLane(matchTypes);
        championWinRateService.getWinRatesByChampionRole(matchTypes);
        Logger.getLogger(CacheScheduleService.class.getName()).log(Logger.Level.INFO, "Beginning caching matchups");
        champMatchupService.getAllMatchupsByChampion(matchTypes);
        Logger.getLogger(CacheScheduleService.class.getName()).log(Logger.Level.INFO, "Beginning caching teamups");
        champTeamupService.getAllTeamupsByChampion(matchTypes);
        Logger.getLogger(CacheScheduleService.class.getName()).log(Logger.Level.INFO, "Finished caching");
    }
}
