/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.dataminer.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import statikk.domain.entity.LolMatch;
import statikk.domain.riotapi.model.FeaturedGames;
import statikk.domain.riotapi.model.MatchListDto;
import statikk.domain.riotapi.model.MatchReferenceDto;
import statikk.domain.riotapi.model.QueueType;
import statikk.domain.riotapi.model.Region;
import statikk.domain.riotapi.model.SummonerDto;
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

    RestTemplate restTemplate;

    public MatchMiningService(RiotApiService riotApiService) {
        this.riotApiService = riotApiService;
    }

    public MatchMiningService() {
        restTemplate = new RestTemplate();
    }

    public int mineMatches(int matchesToMine, Region region, LinkedHashSet<Long> accountIdsToMine) throws InterruptedException {
        final HashSet<Long> alreadyMinedMatches = new HashSet<>();
        final HashSet<Long> alreadyMinedAccounts = new HashSet<>();

        ArrayList<LolMatch> minedMatches = new ArrayList<>();
        HashSet<Long> matchIds = new HashSet<>();

        while (matchIds.size() < matchesToMine) {
            Long accountId = getNextAccountId(accountIdsToMine, alreadyMinedAccounts, region);
            MatchListDto recentGames = riotApiService.getRecentGames(region, accountId);
            if (recentGames == null || recentGames.getMatches() == null) {
                continue;
            }
            for (MatchReferenceDto game : recentGames.getMatches()) {
                if (game == null || game.getQueue() == null
                        || EnumSet.of(QueueType.CUSTOM,
                                QueueType.COOP_VS_AI_BEGINNER_3x3,
                                QueueType.COOP_VS_AI_BEGINNER_5x5,
                                QueueType.COOP_VS_AI_INTERMEDIATE_3x3,
                                QueueType.COOP_VS_AI_INTERMEDIATE_5x5,
                                QueueType.COOP_VS_AI_INTRO_3x3,
                                QueueType.COOP_VS_AI_INTRO_5x5).contains(game.getQueue())
                        || alreadyMinedMatches.contains(game.getGameId())
                        || isGameTooOld(game)) {
                    continue;
                }
                alreadyMinedMatches.add(game.getGameId());
                minedMatches.add(new LolMatch(game));
                matchIds.add(game.getGameId());
                if (matchIds.size() >= matchesToMine) {
                    break;
                }
            }
        }
        Logger.getLogger(MatchMiningService.class.getName())
                .log(Level.INFO, "Inserting " + minedMatches.size() + " matches that were found for " + region + ".");
        int newMatchesMined = lolMatchService.batchInsert(minedMatches);

        Logger.getLogger(MatchMiningService.class.getName())
                .log(Level.INFO, newMatchesMined + " matches were successfully inserted for " + region + ".");
        return newMatchesMined;
    }

    private List<Long> getStartingAccountIds(Region region) {
        FeaturedGames games = riotApiService.getFeaturedGames(region);
        Random rand = new Random();

        if (games == null) {
            try {
                // There was an error fetching games.... this is a large problem,
                // because this is the starting point of the miner.
                // The endpoint may be down temporarily... try again in 5 minutes?
                Thread.sleep(1000 * 60 * 5);
            } catch (InterruptedException ex) {
                Logger.getLogger(MatchMiningService.class.getName()).log(Level.SEVERE, "Interrupted when sleeping...", ex);
            }
            return getStartingAccountIds(region);
        }
        List<String> accountNames = games.getGameList()
                .stream()
                .map((game) -> SummonerDto.getKeyFromName(game.getParticipants().get(rand.nextInt(game.getParticipants().size()) % game.getParticipants().size()).getSummonerName()))
                .collect(Collectors.toList());

        return accountNames
                .stream()
                .map((name) -> riotApiService.getSummonerByName(region, name))
                .filter((summonerData) -> summonerData != null)
                .map((populatedSummonerData) -> populatedSummonerData.getAccountId())
                .collect(Collectors.toList());
    }

    public void addStartingAccountsIfNeeded(LinkedHashSet<Long> accountsToMine, Region region) {
        if (accountsToMine.isEmpty()) {
            accountsToMine.addAll(getStartingAccountIds(region));
        }
    }

    private Long getNextAccountId(LinkedHashSet<Long> accountsToMine, HashSet<Long> alreadyMinedAccounts, Region region) throws InterruptedException {
        if (accountsToMine.isEmpty()) {
            accountsToMine.addAll(getStartingAccountIds(region));
        }
        for (Iterator<Long> iter = accountsToMine.iterator(); iter.hasNext();) {
            Long summonerId = iter.next();
            iter.remove();
            if (summonerId == null) {
                continue;
            }
            alreadyMinedAccounts.add(summonerId);
            return summonerId;
        }

        Thread.sleep(10000);
        return getNextAccountId(accountsToMine, alreadyMinedAccounts, region);
    }

    private boolean isGameTooOld(MatchReferenceDto game) {
        Date oneMonthAgo = new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 24L * 31L);
        Date gameTime = new Date(game.getTimestamp());
        return gameTime.before(oneMonthAgo);
    }

}
