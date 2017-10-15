/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.webapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import statikk.framework.riotapi.model.Region;
import statikk.framework.riotapi.service.RiotApiService;

@RestController
@ControllerAdvice
@RequestMapping(value = "/api/static-data")
public class StaticDataController extends BaseController {

    @Autowired 
    RiotApiService riotApiService;
        
    @ResponseBody
    @RequestMapping(value = "/champions/{region}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getChampions(@PathVariable("region") String region) {
        return riotApiService.getStaticChampionsData(Region.valueOf(region));
    }
    
    @ResponseBody
    @RequestMapping(value = "/champions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getChampions() {
        return riotApiService.getStaticChampionsData(Region.NA);
    }
    
    @ResponseBody
    @RequestMapping(value = "/champion/{championId}/{region}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getChampion(@PathVariable("championId") long championId, @PathVariable("region") String region) {
        return riotApiService.getStaticChampionData(Region.valueOf(region), championId);
    }
    
    @ResponseBody
    @RequestMapping(value = "/champion/{championId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getChampion(@PathVariable("championId") long championId) {
        return riotApiService.getStaticChampionData(Region.NA, championId);
    }    
}