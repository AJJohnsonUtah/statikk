/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheoryapi.controller;

import com.njin.loltheory.riotapi.model.Region;
import com.njin.loltheory.riotapi.service.RiotApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
@RequestMapping(value = "/api/static-data")
public class StaticDataController extends BaseController {

    @Autowired 
    RiotApiService riotApiService;
    
    @RequestMapping(value = "/champions/{region}", method = RequestMethod.GET)
    public String getChampions(@PathVariable("region") String region) {
        return riotApiService.getStaticChampionsData(Region.valueOf(region));
    }
    
    @RequestMapping(value = "/champions", method = RequestMethod.GET)
    public String getChampions() {
        return riotApiService.getStaticChampionsData(Region.NA);
    }
}