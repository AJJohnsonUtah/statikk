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
import com.njin.loltheory.riotapi.service.RiotApiKeyLimitService;
import com.njin.loltheory.riotapi.service.RiotApiService;
import com.njin.loltheory.service.LolMatchService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

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

    @Autowired
    RiotApiKeyLimitService riotApiKeyLimitService;

    LinkedHashSet<Long> summonerIdsToMine = new LinkedHashSet<>();

    HashSet<Long> alreadyMinedMatches = new HashSet<>();

    HashSet<Long> alreadyMinedSummoners = new HashSet<>();

    RestTemplate restTemplate;

    public MatchMiningService() {
        restTemplate = new RestTemplate();
    }

    public void mineMatches(int matchesToMine) {
        long start = System.currentTimeMillis();

        LolMatch[] minedMatches = new LolMatch[matchesToMine];
        HashSet<Long> matchIds = new HashSet<>();

        while (matchIds.size() < matchesToMine) {
            List<Long> summonerIds = getNextSummonerIds(summonerIdsToMine, alreadyMinedSummoners);
            for (RecentGamesDto recentGames : getRecentGames(summonerIds)) {
                for (GameDto game : recentGames.getGames()) {
                    if (alreadyMinedMatches.contains(game.getGameId())) {
                        continue;
                    }
                    minedMatches[matchIds.size()] = new LolMatch(game);
                    matchIds.add(game.getGameId());
                    addSummonersToMineIfNeeded(game, summonerIdsToMine);

                    if (matchIds.size() >= matchesToMine) {
                        break;
                    }
                }
                if (matchIds.size() >= matchesToMine) {
                    break;
                }
            }
        }
        lolMatchService.batchInsert(minedMatches);
        System.out.println("Took " + (System.currentTimeMillis() - start) / 1000.0 + " seconds for " + matchesToMine + " matches.");
    }

    private List<Long> getNextSummonerIds(LinkedHashSet<Long> summonersToMine, HashSet<Long> alreadyMinedSummoners) {
        int summonersToFind = 5;
        List<Long> summonerIds = new ArrayList<>();
        addStartingUserIfNeeded(summonersToMine);
        while (summonerIds.size() < 5 && !summonersToMine.isEmpty()) {
            summonerIds.add(getNextSummoner(summonersToMine, alreadyMinedSummoners));
        }
        return summonerIds;
    }

    private List<RecentGamesDto> getRecentGames(List<Long> summonerIds) {
        List<RecentGamesDto> recentGames = new ArrayList<>();
        for (Long id : summonerIds) {
            recentGames.add(getRecentGames(Region.NA, id));
        }
        return recentGames;
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

    private Long getNextSummoner(LinkedHashSet<Long> summonersToMine, HashSet<Long> alreadyMinedSummoners) {
        Iterator<Long> iter = summonersToMine.iterator();
        Long summonerId = iter.next();
        iter.remove();
        alreadyMinedSummoners.add(summonerId);
        return summonerId;
    }

    private void addSummonersToMineIfNeeded(GameDto game, LinkedHashSet<Long> summonersToMine) {
        if (summonersToMine.size() < 1000) {
            game.getFellowPlayers().stream().forEach((player) -> {
                summonersToMine.add(player.getSummonerId());
            });
        }
    }

    @Autowired
    private Executor executor;

    public List<RecentGamesDto> getRecentGamesAsync(List<Long> summonerIds) {
        List<RecentGamesDto> summonersRecentGames = new ArrayList<>();
        List<GetRecentGamesTask> tasks = new ArrayList<>();

        for (Long summonerId : summonerIds) {
            tasks.add(new GetRecentGamesTask(summonerId, executor));
        }

        while (!tasks.isEmpty()) {
            for (Iterator<GetRecentGamesTask> it = tasks.iterator(); it.hasNext();) {
                GetRecentGamesTask task = it.next();
                if (task.isDone()) {
                    summonersRecentGames.add(task.getResponse());
                    it.remove();
                }
            }
            //avoid tight loop in "main" thread
            if (!tasks.isEmpty()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MatchMiningService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return summonersRecentGames;
    }

    //abstraction to wrap Callable and Future
    class GetRecentGamesTask {

        private final GetRecentGamesWork work;
        private final FutureTask<RecentGamesDto> task;

        public GetRecentGamesTask(Long summonerId, Executor executor) {
            this.work = new GetRecentGamesWork(summonerId);
            this.task = new FutureTask<>(work);
            executor.execute(this.task);
        }

        public RecentGamesDto getResponse() {
            try {
                return this.task.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        public boolean isDone() {
            return this.task.isDone();
        }

    }

    //Callable representing actual HTTP GET request
    class GetRecentGamesWork implements Callable<RecentGamesDto> {

        private final Long summonerId;

        public GetRecentGamesWork(Long url) {
            this.summonerId = url;
        }

        public Long getSummonerId() {
            return this.summonerId;
        }

        public RecentGamesDto call() throws Exception {
            return getRecentGames(Region.NA, summonerId);
        }
    }

    public RecentGamesDto getRecentGames(Region region, Long summonerId) {
        String url = riotApiService.getDynamicURLWithAPIKey(region, "/api/lol/" + region.toString() + "/v1.3/game/by-summoner/" + summonerId + "/recent?api_key=");
        ParameterizedTypeReference<RecentGamesDto> typeRef = new ParameterizedTypeReference<RecentGamesDto>() {
        };
        return getRiotApiRequest(url, true, typeRef);
    }

    private <T> T getRiotApiRequest(String url, boolean addsToKeyLimit, ParameterizedTypeReference<T> typeReference) {
//        Logger.getLogger(RiotApiService.class
//                .getName()).log(Level.INFO, url);
        System.out.println("start at: " + new Date());
        ResponseEntity response = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, null, typeReference);
        } catch (HttpServerErrorException ex) {
            try {
                Logger.getLogger(RiotApiService.class
                        .getName()).log(Level.WARNING, "Unable to fetch data from " + url, ex);
                response = restTemplate.exchange(url, HttpMethod.GET, null, typeReference);
            } catch (HttpServerErrorException ex2) {
                Logger.getLogger(RiotApiService.class
                        .getName()).log(Level.SEVERE, "Unable to fetch data from " + url + ". Returning null.", ex);
            } catch (HttpClientErrorException ex2) {
                riotApiKeyLimitService.waitSeconds();
                return getRiotApiRequest(url, addsToKeyLimit, typeReference);
            }
        } catch (HttpClientErrorException ex) {
            riotApiKeyLimitService.waitSeconds();
            return getRiotApiRequest(url, addsToKeyLimit, typeReference);
        }
        if (response == null) {
            riotApiKeyLimitService.waitMinutes();
            return null;
        } else if (addsToKeyLimit) {
            String limitHeader = response.getHeaders().getFirst("X-Rate-Limit-Count");
            riotApiKeyLimitService.handleLimitHeader(limitHeader);
        }
        System.out.println("return at: " + new Date());

        return (T) response.getBody();

    }
}
