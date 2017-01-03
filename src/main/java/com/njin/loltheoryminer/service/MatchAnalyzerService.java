/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheoryminer.service;

import com.njin.loltheory.riotapi.model.MatchDetail;
import com.njin.loltheory.riotapi.model.Region;
import com.njin.loltheory.riotapi.service.RiotApiService;
import com.njin.loltheory.service.LolMatchService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author AJ
 */
@Service
public class MatchAnalyzerService {
    @Autowired
    RiotApiService riotApiService;
    
    @Autowired
    LolMatchService lolMatchService;
    
    public void analyzeMatches(int matchesToAnalyze) {
        List<MatchDetail> matches = new ArrayList<>();
        List<Long> matchIdsToAnalyze = lolMatchService.findMatchesToAnalyze(matchesToAnalyze);
        long start = System.currentTimeMillis();
        for(Long matchId : matchIdsToAnalyze) {        
            MatchDetail currentMatch = riotApiService.getMatchDetailWithoutTimeline(Region.NA, matchId);
            if(currentMatch.getStatus() != null) {
                matches.add(currentMatch);
            }            
        }
        System.out.println("To fetch " + matchesToAnalyze + " matches WITHOUT timeline data, it took: " + ((System.currentTimeMillis() - start) / 1000.0) + " seconds.");
        
        
        matchIdsToAnalyze = lolMatchService.findMatchesToAnalyze(matchesToAnalyze);
        start = System.currentTimeMillis();
        for(Long matchId : matchIdsToAnalyze) {            
            MatchDetail currentMatch = riotApiService.getMatchDetailWithTimeline(Region.NA, matchId);
            if(currentMatch.getStatus() != null) {
                matches.add(currentMatch);
            }                  }
        System.out.println("To fetch " + matchesToAnalyze + " matches WITH timeline data, it took: " + ((System.currentTimeMillis() - start) / 1000.0) + " seconds.");
        System.out.println(matches.size());
    }
}
