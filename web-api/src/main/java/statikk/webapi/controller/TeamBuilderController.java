/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.webapi.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import statikk.domain.riotapi.model.ChampionMasteryDto;
import statikk.domain.riotapi.model.Region;
import statikk.domain.riotapi.model.SummonerDto;
import statikk.webapi.model.ChampionSuggestion;
import statikk.webapi.model.TeamBuilderProgressData;
import statikk.webapi.service.TeamBuilderService;

/**
 *
 * @author Grann
 */
@Controller
@RequestMapping(value = "/api/team-builder")
public class TeamBuilderController extends BaseController {

    @Autowired
    TeamBuilderService teamBuilderService;

    @ResponseBody
    @RequestMapping(value = "/suggestions", method = RequestMethod.POST)
    public Map<Long, ChampionSuggestion> getSuggestions(@RequestBody TeamBuilderProgressData teamBuilderProgess) {
        return teamBuilderService.getChampionSuggestions(teamBuilderProgess);
    }
}
