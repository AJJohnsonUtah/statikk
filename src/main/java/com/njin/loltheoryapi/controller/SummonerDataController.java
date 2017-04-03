/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheoryapi.controller;

import com.njin.loltheory.riotapi.model.ChampionMastery;
import com.njin.loltheory.riotapi.model.Region;
import com.njin.loltheory.riotapi.service.RiotApiService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author AJ
 */
@RestController
@ControllerAdvice
@RequestMapping(value = "/api/summoner")
public class SummonerDataController {

    @Autowired
    RiotApiService riotApiService;

    @RequestMapping(value = "/{summonerId}/champion-mastery/all/{region}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getChampionMastery(@PathVariable("summonerId") long summonerId, @PathVariable("region") String region) {
        return riotApiService.getChampionMastery(Region.valueOf(region), summonerId);
    }

    @RequestMapping(value = "/{summonerId}/champion-mastery/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getChampionMastery(@PathVariable("summonerId") long summonerId) {
        return riotApiService.getChampionMastery(Region.NA, summonerId);
    }

    @RequestMapping(value = "/{summonerId}/champion-mastery/top/{region}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getTopChampionMastery(@PathVariable("summonerId") long summonerId, @PathVariable("region") String region) {
        return riotApiService.getTopChampionMastery(Region.valueOf(region), summonerId);
    }

    @RequestMapping(value = "/{summonerId}/champion-mastery/top", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getTopChampionMastery(@PathVariable("summonerId") long summonerId) {
        return riotApiService.getTopChampionMastery(Region.NA, summonerId);
    }
    
    @RequestMapping(value = "/{summonerId}/current-game/{region}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getCurrentGame(@PathVariable("summonerId") long summonerId, @PathVariable("region") String region) {
        return riotApiService.getCurrentGame(Region.valueOf(region), summonerId);
    }

    @RequestMapping(value = "/{summonerId}/current-game", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getCurrentGame(@PathVariable("summonerId") long summonerId) {
        return riotApiService.getCurrentGame(Region.NA, summonerId);
    }    

}
