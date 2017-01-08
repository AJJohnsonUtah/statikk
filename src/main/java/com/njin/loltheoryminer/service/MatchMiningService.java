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
import java.util.HashSet;
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

    LinkedHashSet<Long> summonerIdsToMine = new LinkedHashSet<>();

    HashSet<Long> alreadyMinedMatches = new HashSet<>();

    public void mineMatches(int matchesToMine) {
        addStartingUserIfNeeded(summonerIdsToMine);

        LolMatch[] minedMatches = new LolMatch[matchesToMine];
        HashSet<Long> matchIds = new HashSet<>();

        while (matchIds.size() < matchesToMine) {
            Long summonerId = getNextSummoner(summonerIdsToMine);

            RecentGamesDto recentGames = riotApiService.getRecentGames(Region.NA, summonerId);
            for (GameDto game : recentGames.getGames()) {
                if (alreadyMinedMatches.contains(game.getGameId())) {
                    continue;
                }
                minedMatches[matchIds.size()] = new LolMatch(game);
                matchIds.add(game.getGameId());
                alreadyMinedMatches.add(summonerId);
                addSummonersToMineIfNeeded(game, summonerIdsToMine);

                if (matchIds.size() >= matchesToMine) {
                    break;
                }
            }
        }
        lolMatchService.batchInsert(minedMatches);
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

    private void addStartingUserIfNeeded(LinkedHashSet<Long> summonersToMine) {
        if (summonersToMine.isEmpty()) {
            summonersToMine.add(getStartingSummonerId());
        }
    }

    private Long getNextSummoner(LinkedHashSet<Long> summonersToMine) {
        Iterator<Long> iter = summonersToMine.iterator();
        Long summonerId = iter.next();
        iter.remove();
        return summonerId;
    }

    private void addSummonersToMineIfNeeded(GameDto game, LinkedHashSet<Long> summonersToMine) {
        if (summonersToMine.size() < 1000) {
            game.getFellowPlayers().stream().forEach((player) -> {
                summonersToMine.add(player.getSummonerId());
            });
        }
    }

}
