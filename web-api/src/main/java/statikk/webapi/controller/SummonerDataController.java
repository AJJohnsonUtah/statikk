/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.webapi.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import statikk.domain.riotapi.model.ChampionMasteryDto;
import statikk.domain.riotapi.model.MatchListDto;
import statikk.domain.riotapi.model.Region;
import statikk.domain.riotapi.model.SummonerDto;
import statikk.domain.riotapi.service.RiotApiService;

/**
 *
 * @author AJ
 */
@Controller
@RequestMapping(value = "/api/summoner")
public class SummonerDataController {

    @Autowired
    RiotApiService riotApiService;

    @ResponseBody
    @Cacheable("summoner-mastery")
    @RequestMapping(value = "/{summonerId}/champion-mastery/all/{region}", method = RequestMethod.GET)
    public List<ChampionMasteryDto> getChampionMastery(@PathVariable("summonerId") long summonerId, @PathVariable("region") String region) {
        return riotApiService.getChampionMastery(Region.valueOf(region), summonerId);
    }

    @ResponseBody
    @Cacheable("summoner-mastery")
    @RequestMapping(value = "/{summonerId}/champion-mastery/all", method = RequestMethod.GET)
    public List<ChampionMasteryDto> getChampionMastery(@PathVariable("summonerId") long summonerId) {
        return riotApiService.getChampionMastery(Region.NA, summonerId);
    }

    @ResponseBody
    @Cacheable("current-game")
    @RequestMapping(value = "/{summonerId}/current-game/{region}", method = RequestMethod.GET)
    public String getCurrentGame(@PathVariable("summonerId") long summonerId, @PathVariable("region") String region) {
        String response = riotApiService.getCurrentGame(Region.valueOf(region), summonerId);
        if (response == null) {
            return "{}";
        } else {
            return response;
        }
    }

    @ResponseBody
    @Cacheable("current-game")
    @RequestMapping(value = "/{summonerId}/current-game", method = RequestMethod.GET)
    public String getCurrentGame(@PathVariable("summonerId") long summonerId) {
        String response = riotApiService.getCurrentGame(Region.NA, summonerId);
        if (response == null) {
            return "{}";
        } else {
            return response;
        }
    }

    @ResponseBody
    @Cacheable("summoner-name")
    @RequestMapping(value = "/by-name/{summonerName}", method = RequestMethod.GET)
    public SummonerDto getSummonerByName(@PathVariable("summonerName") String summonerName) {
        return riotApiService.getSummonerByName(Region.NA, summonerName);
    }

    @ResponseBody
    @Cacheable("summoner-name")
    @RequestMapping(value = "/by-name/{summonerName}/{region}", method = RequestMethod.GET)
    public SummonerDto getSummonerByName(@PathVariable("summonerName") String summonerName, @PathVariable("region") String region) {
        return riotApiService.getSummonerByName(Region.valueOf(region), summonerName);
    }

    @ResponseBody
    @Cacheable("summoner-matches")
    @RequestMapping(value = "/matchlist/{summonerId}", method = RequestMethod.GET)
    public MatchListDto getMatchlist(@PathVariable("summonerId") long summonerId) {
        return riotApiService.getRecentGames(Region.NA, summonerId);
    }

    @ResponseBody
    @Cacheable("summoner-matches")
    @RequestMapping(value = "/matchlist/{summonerId}/{region}", method = RequestMethod.GET)
    public MatchListDto getMatchlist(@PathVariable("summonerId") long summonerId, @PathVariable("region") String region) {
        return riotApiService.getRecentGames(Region.valueOf(region), summonerId);
    }
}
