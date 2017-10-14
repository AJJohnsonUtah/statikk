/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.dataminer.service;

import java.util.ArrayList;
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
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import statikk.framework.entity.LolMatch;
import statikk.framework.riotapi.model.FeaturedGames;
import statikk.framework.riotapi.model.MatchDetail;
import statikk.framework.riotapi.model.MatchReferenceDto;
import statikk.framework.riotapi.model.MatchListDto;
import statikk.framework.riotapi.model.QueueType;
import statikk.framework.riotapi.model.Region;
import statikk.framework.riotapi.model.SummonerDto;
import statikk.framework.riotapi.service.RiotApiKeyLimitService;
import statikk.framework.riotapi.service.RiotApiService;
import statikk.framework.service.LolMatchService;

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

    private final LinkedHashSet<Long> accountIdsToMine = new LinkedHashSet<>();

    private final HashSet<Long> alreadyMinedMatches = new HashSet<>();

    private final HashSet<Long> alreadyMinedAccounts = new HashSet<>();

    RestTemplate restTemplate;

    public MatchMiningService() {
        restTemplate = new RestTemplate();
    }

    public void mineMatches(int matchesToMine) {
        long start = System.currentTimeMillis();

        LolMatch[] minedMatches = new LolMatch[matchesToMine];
        HashSet<Long> matchIds = new HashSet<>();

        while (matchIds.size() < matchesToMine) {
            System.out.print(" Mined " + matchIds.size() + " matches. ");
            Long accountId = getNextAccountId(accountIdsToMine, alreadyMinedAccounts);
            MatchListDto recentGames = riotApiService.getRecentGames(Region.NA, accountId);
            if (recentGames == null || recentGames.getMatches() == null) {
                continue;
            }
            for (MatchReferenceDto game : recentGames.getMatches()) {
                if (game.getQueue().equals(QueueType.CUSTOM) || alreadyMinedMatches.contains(game.getGameId())) {
                    continue;
                }
                alreadyMinedMatches.add(game.getGameId());
                minedMatches[matchIds.size()] = new LolMatch(game);
                matchIds.add(game.getGameId());
                if (matchIds.size() >= matchesToMine) {
                    break;
                }
            }
            if (matchIds.size() >= matchesToMine) {
                break;
            }
        }
        lolMatchService.batchInsert(minedMatches);
        System.out.println("Took " + (System.currentTimeMillis() - start) / 1000.0 + " seconds for " + matchesToMine + " matches.");
    }

    private List<Long> getStartingAccountIds() {
        FeaturedGames games = riotApiService.getFeaturedGames(Region.NA);
        Random rand = new Random();
        List<String> accountNames = games.getGameList()
                .stream()
                .map((game) -> SummonerDto.getKeyFromName(game.getParticipants().get(rand.nextInt(game.getParticipants().size()) % game.getParticipants().size()).getSummonerName()))
                .collect(Collectors.toList());

        return accountNames
                .stream()
                .map((name) -> riotApiService.getSummonerByName(Region.NA, name).getAccountId())
                .collect(Collectors.toList());
    }

    private void addStartingAccountsIfNeeded(LinkedHashSet<Long> accountsToMine) {
        if (accountsToMine.isEmpty()) {
            accountsToMine.addAll(getStartingAccountIds());
        }
    }

    private Long getNextAccountId(LinkedHashSet<Long> accountsToMine, HashSet<Long> alreadyMinedAccounts) {
        addStartingAccountsIfNeeded(accountsToMine);
        Iterator<Long> iter = accountsToMine.iterator();
        Long summonerId = iter.next();
        iter.remove();
        alreadyMinedAccounts.add(summonerId);
        return summonerId;
    }

    public void addAccountsToMineIfNeeded(MatchDetail match) {
        if (accountIdsToMine.size() < 1000) {
            if (match.getParticipantIdentities().get(0).getPlayer() != null) {
                match.getParticipantIdentities()
                        .stream()
                        .map(s -> s.getPlayer().getSummonerId())
                        .sequential()
                        .collect(Collectors.toCollection(() -> accountIdsToMine));
            }
        }
    }

    @Autowired
    private Executor executor;

    public List<MatchListDto> getRecentGamesAsync(List<Long> summonerIds) {
        List<MatchListDto> summonersRecentGames = new ArrayList<>();
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
        private final FutureTask<MatchListDto> task;

        public GetRecentGamesTask(Long summonerId, Executor executor) {
            this.work = new GetRecentGamesWork(summonerId);
            this.task = new FutureTask<>(work);
            executor.execute(this.task);
        }

        public MatchListDto getResponse() {
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
    class GetRecentGamesWork implements Callable<MatchListDto> {

        private final Long summonerId;

        public GetRecentGamesWork(Long url) {
            this.summonerId = url;
        }

        public Long getSummonerId() {
            return this.summonerId;
        }

        public MatchListDto call() throws Exception {
            return riotApiService.getRecentGames(Region.NA, summonerId);
        }
    }

    private <T> T getRiotApiRequest(String url, boolean addsToKeyLimit, ParameterizedTypeReference<T> typeReference) {
//        Logger.getLogger(RiotApiService.class
//                .getName()).log(Level.INFO, url);
//        System.out.println("start at: " + new Date());
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
//        System.out.println("return at: " + new Date());

        return (T) response.getBody();

    }
}
