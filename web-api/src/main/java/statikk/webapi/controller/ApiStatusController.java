package statikk.webapi.controller;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import statikk.domain.entity.LolVersion;
import statikk.domain.service.LolVersionService;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author AJ
 */
@Controller
@RequestMapping(value = "/api/status")
public class ApiStatusController {

    @Autowired
    LolVersionService lolVersionService;

    @ResponseBody
    @Cacheable("supported-versions")
    @RequestMapping(value = "/supported-versions", method = RequestMethod.GET, produces = "application/json")
    public Iterable<String> getSupportedVersions() {
        return lolVersionService.findVersionsWithData();
    }

    @ResponseBody
    @Cacheable("current-version")
    @RequestMapping(value = "/current-version", method = RequestMethod.GET, produces = "application/json")
    public String getMostRecentVersionWithData() {
        return getSupportedVersions().iterator().next();
    }
}
