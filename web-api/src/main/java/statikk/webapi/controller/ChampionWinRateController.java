/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.webapi.controller;

import java.util.EnumSet;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import statikk.domain.entity.LolVersion;
import statikk.domain.entity.enums.Lane;
import statikk.domain.entity.enums.Role;
import statikk.domain.riotapi.model.QueueType;
import statikk.domain.riotapi.model.Rank;
import statikk.domain.stats.model.WinRateByChampion;
import statikk.domain.stats.model.WinRateByChampionLane;
import statikk.domain.stats.model.WinRateByMatchType;
import statikk.domain.stats.model.WinRateByLane;
import statikk.domain.stats.model.WinRateByRole;
import statikk.domain.stats.service.ChampionWinRateService;
import statikk.domain.stats.model.WinRateMapWithTotal;
import statikk.domain.stats.model.WinRateWithTotal;

/**
 *
 * @author AJ
 */
@Controller
@RequestMapping(value = "/api/champion/win-rate")
public class ChampionWinRateController {

    @Autowired
    ChampionWinRateService championWinRateService;

    @Autowired
    ApiStatusController apiStatusController;

    @ResponseBody
    @Cacheable("champion-win-rates")
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public WinRateWithTotal<WinRateByChampion> getAllChampionWinRates(
            @RequestParam(value = "matchType") int matchType,
            @RequestParam(value = "rank", required = false) String rank,
            @RequestParam(value = "lane", required = false) String lane,
            @RequestParam(value = "version") String version
    ) {
        QueueType matchTypeCriteria = QueueType.fromId(matchType);
        Rank rankCriteria = rank == null || rank.equals("") ? null : Rank.valueOf(rank);
        Lane laneCriteria = lane == null || lane.equals("") ? null : Lane.valueOf(lane);
        LolVersion lolVersion = new LolVersion(version);

        return new WinRateWithTotal(championWinRateService.getAllChampionWinRates(
                matchTypeCriteria,
                rankCriteria,
                laneCriteria,
                lolVersion
        ));
    }

    @ResponseBody
    @Cacheable("champion-win-rates")
    @RequestMapping(value = "/{championId}", method = RequestMethod.GET, produces = "application/json")
    public WinRateMapWithTotal<QueueType, WinRateByMatchType> getChampionWinRates(
            @PathVariable("championId") int championId,
            @RequestParam(value = "version", required = false) String version
    ) {
        if (version == null) {
            version = apiStatusController.getMostRecentVersionWithData();
        }
        LolVersion lolVersion = new LolVersion(version);
        return new WinRateMapWithTotal(championWinRateService.getChampionWinRates(championId, lolVersion));
    }

    @ResponseBody
    @Cacheable("champion-win-rates")
    @RequestMapping(value = "/by-lane/{championId}", method = RequestMethod.GET, produces = "application/json")
    public WinRateMapWithTotal<Lane, WinRateByLane> getChampionWinRatesByLane(
            @PathVariable("championId") int championId,
            @RequestParam(value = "matchType", required = false) Integer matchType,
            @RequestParam(value = "role", required = false) String role,
            @RequestParam(value = "version", required = false) String version
    ) {
        if (version == null) {
            version = apiStatusController.getMostRecentVersionWithData();
        }
        LolVersion lolVersion = new LolVersion(version);
        if (matchType == null) {
            if (role == null) {
                return new WinRateMapWithTotal(
                        championWinRateService.getChampionLaneWinRates(championId, QueueType.standardSRMatchTypes, lolVersion));
            } else {
                return new WinRateMapWithTotal(
                        championWinRateService.getChampionLaneWinRates(championId, Role.valueOf(role), QueueType.standardSRMatchTypes, lolVersion));
            }
        } else {
            if (role == null) {
                return new WinRateMapWithTotal(
                        championWinRateService.getChampionLaneWinRates(championId, EnumSet.of(QueueType.fromId(matchType)), lolVersion));
            } else {
                return new WinRateMapWithTotal(
                        championWinRateService.getChampionLaneWinRates(championId, Role.valueOf(role), EnumSet.of(QueueType.fromId(matchType)), lolVersion)
                );
            }
        }
    }

    @ResponseBody
    @Cacheable("champion-win-rates")
    @RequestMapping(value = "/by-role/{championId}", method = RequestMethod.GET, produces = "application/json")
    public WinRateMapWithTotal<Role, WinRateByRole> getChampionWinRatesByRole(
            @PathVariable("championId") int championId,
            @RequestParam(value = "matchType", required = false) Integer matchType,
            @RequestParam(value = "lane", required = false) String lane,
            @RequestParam(value = "version", required = false) String version
    ) {
        if (version == null) {
            version = apiStatusController.getMostRecentVersionWithData();
        }
        LolVersion lolVersion = new LolVersion(version);
        if (matchType == null) {
            if (lane == null) {
                return new WinRateMapWithTotal(
                        championWinRateService.getChampionRoleWinRates(championId, QueueType.standardSRMatchTypes, lolVersion));
            } else {
                return new WinRateMapWithTotal(
                        championWinRateService.getChampionRoleWinRates(championId, Lane.valueOf(lane), QueueType.standardSRMatchTypes, lolVersion)
                );
            }
        } else {
            if (lane == null) {
                return new WinRateMapWithTotal(
                        championWinRateService.getChampionRoleWinRates(championId, EnumSet.of(QueueType.fromId(matchType)), lolVersion)
                );
            } else {
                return new WinRateMapWithTotal(
                        championWinRateService.getChampionRoleWinRates(championId, Lane.valueOf(lane), EnumSet.of(QueueType.fromId(matchType)), lolVersion)
                );
            }
        }
    }

    @ResponseBody
    @Cacheable("champion-win-rates")
    @RequestMapping(value = "/by-champion-lane", method = RequestMethod.GET, produces = "application/json")
    public Map<Integer, WinRateMapWithTotal<Lane, WinRateByChampionLane>> getChampionWinRatesByLane(
            @RequestParam(value = "matchType", required = false) Integer matchType
    ) {
        if (matchType == null) {
            return championWinRateService.getWinRatesByChampionLane(QueueType.standardSRMatchTypes);
        } else {
            return championWinRateService.getWinRatesByChampionLane(EnumSet.of(QueueType.fromId(matchType)));
        }
    }
}
