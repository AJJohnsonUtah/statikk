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
import statikk.domain.entity.LolMatch;
import statikk.domain.riotapi.model.FeaturedGames;
import statikk.domain.riotapi.model.MatchDetail;
import statikk.domain.riotapi.model.MatchListDto;
import statikk.domain.riotapi.model.MatchReferenceDto;
import statikk.domain.riotapi.model.QueueType;
import statikk.domain.riotapi.model.Region;
import statikk.domain.riotapi.model.SummonerDto;
import statikk.domain.riotapi.service.RiotApiKeyLimitService;
import statikk.domain.riotapi.service.RiotApiService;
import statikk.domain.service.LolMatchService;

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

    private final LinkedHashSet<Long> accountIdsToMine = new LinkedHashSet<>();

    private final HashSet<Long> alreadyMinedMatches = new HashSet<>();

    private final HashSet<Long> alreadyMinedAccounts = new HashSet<>();

    RestTemplate restTemplate;

    public MatchMiningService(RiotApiService riotApiService) {
        this.riotApiService = riotApiService;
    }

    public MatchMiningService() {
        restTemplate = new RestTemplate();
    }

    public void mineMatches(int matchesToMine) {
        long start = System.currentTimeMillis();

        ArrayList<LolMatch> minedMatches = new ArrayList<>();
        HashSet<Long> matchIds = new HashSet<>();

        while (matchIds.size() < matchesToMine) {
            System.out.print(" Mined " + matchIds.size() + " matches. ");
            Long accountId = getNextAccountId(accountIdsToMine, alreadyMinedAccounts);
            MatchListDto recentGames = riotApiService.getRecentGames(Region.NA, accountId);
            if (recentGames == null || recentGames.getMatches() == null) {
                continue;
            }
            for (MatchReferenceDto game : recentGames.getMatches()) {
                if (game == null || game.getQueue() == null || game.getQueue().equals(QueueType.CUSTOM) || alreadyMinedMatches.contains(game.getGameId())) {
                    continue;
                }
                alreadyMinedMatches.add(game.getGameId());
                minedMatches.add(new LolMatch(game));
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
}
