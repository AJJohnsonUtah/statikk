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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import statikk.domain.stats.model.ChampionWinRate;
import statikk.domain.stats.service.ChampionWinRateService;
import statikk.webapi.model.WinRateWithTotal;

/**
 *
 * @author AJ
 */
@Controller
@RequestMapping(value = "/api/champion/win-rate")
public class ChampionWinRateController {

    @Autowired
    ChampionWinRateService championWinRateService;

    @ResponseBody
    @Cacheable("champion-win-rates")
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public WinRateWithTotal<ChampionWinRate> getAllChampionWinRates() {
        return new WinRateWithTotal(championWinRateService.getChampionWinRates());
    }
}
