/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheoryminer.service;

import com.njin.loltheory.entity.LolMatch;
import com.njin.loltheory.riotapi.model.FeaturedGames;
import com.njin.loltheory.riotapi.model.GameDto;
import com.njin.loltheory.riotapi.model.RecentGamesDto;
import com.njin.loltheory.riotapi.model.SummonerDto;
import com.njin.loltheory.riotapi.model.Region;
import com.njin.loltheory.riotapi.service.RiotApiService;
import com.njin.loltheory.service.LolMatchService;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author AJ
 */
@Service
public class MatchMiningService {

    @Autowired
    RiotApiService riotApiService;

    @Autowired
    LolMatchService lolMatchService;

    private final LinkedHashSet<Long> summonersToMine;

    public MatchMiningService(RiotApiService riotApiService) {
        this.riotApiService = riotApiService;
        this.summonersToMine = new LinkedHashSet<>();
    }

    public MatchMiningService() {
        this.summonersToMine = new LinkedHashSet<>();
    }

    public void mineMatches(int matchesToMine) {
        if (summonersToMine.isEmpty()) {
            summonersToMine.add(getStartingSummonerId());
        }

        LolMatch[] minedMatches = new LolMatch[matchesToMine];
        int matchCount = 0;

        long start = System.currentTimeMillis();
        while (matchCount < matchesToMine) {
            Iterator<Long> iter = summonersToMine.iterator();
            Long summonerId = iter.next();
            iter.remove();

            RecentGamesDto recentGames = riotApiService.getRecentGames(Region.NA, summonerId);
            for (GameDto game : recentGames.getGames()) {
                if (matchCount < matchesToMine) {
                    minedMatches[matchCount] = new LolMatch(game);
                    matchCount++;
                }
                if (summonersToMine.size() < 1000) {
                    if(game.getFellowPlayers() == null)
                        continue;
                    game.getFellowPlayers().stream().forEach((player) -> {
                        summonersToMine.add(player.getSummonerId());
                    });
                }
            }

        }
        long end = System.currentTimeMillis();
        System.out.println("It took " + ((end - start) / 1000.0) + " seconds to find " + matchesToMine + " matches.");
        start = System.currentTimeMillis();
        for (LolMatch match : minedMatches) {
            lolMatchService.create(match);
        }
        end = System.currentTimeMillis();
        System.out.println("It took " + ((end - start) / 1000.0) + " seconds to insert " + matchesToMine + " matches.");

    }

    private Long getStartingSummonerId() {
        FeaturedGames games = riotApiService.getFeaturedGames(Region.NA);
        Random rand = new Random();
        int randGame = rand.nextInt(4);
        int randPlayer = rand.nextInt(8);
        String summonerName = games.getGameList().get(randGame).getParticipants().get(randPlayer).getSummonerName();
        String summonerNameKey = SummonerDto.getKeyFromName(summonerName);
        return riotApiService.getSummonersByName(Region.NA, summonerName).get(summonerNameKey).getId();
    }

}
