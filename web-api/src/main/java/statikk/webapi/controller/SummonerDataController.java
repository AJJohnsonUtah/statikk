/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.webapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import statikk.domain.riotapi.model.Region;
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
    @RequestMapping(value = "/{summonerId}/champion-mastery/all/{region}", method = RequestMethod.GET)
    public String getChampionMastery(@PathVariable("summonerId") long summonerId, @PathVariable("region") String region) {
        return riotApiService.getChampionMastery(Region.valueOf(region), summonerId);
    }

    @ResponseBody
    @RequestMapping(value = "/{summonerId}/champion-mastery/all", method = RequestMethod.GET)
    public String getChampionMastery(@PathVariable("summonerId") long summonerId) {
        return riotApiService.getChampionMastery(Region.NA, summonerId);
    }
    
    @ResponseBody
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
    @RequestMapping(value = "/{summonerId}/current-game", method = RequestMethod.GET)
    public String getCurrentGame(@PathVariable("summonerId") long summonerId) {
        String response = riotApiService.getCurrentGame(Region.NA, summonerId);
        if (response == null) {
            return "{}";
        } else {
            return response;
        }
    }

}
