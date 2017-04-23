/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheoryapi.controller;

import com.njin.loltheory.stats.model.ChampionWinRate;
import com.njin.loltheory.stats.service.ChampionWinRateService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author AJ
 */
@RestController
@ControllerAdvice
@RequestMapping(value = "/api/champion/win-rate")
public class ChampionWinRateController {
    
    @Autowired
    ChampionWinRateService championWinRateService;
    
    @ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ChampionWinRate> getAllChampionWinRates() {
        return championWinRateService.getChampionWinRates();
    }
}
