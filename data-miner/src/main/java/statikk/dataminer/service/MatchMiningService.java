/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.dataminer.service;

import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import statikk.domain.entity.LolMatch;
import statikk.domain.entity.LolSummoner;
import statikk.domain.riotapi.model.FeaturedGames;
import statikk.domain.riotapi.model.MatchListDto;
import statikk.domain.riotapi.model.MatchReferenceDto;
import statikk.domain.riotapi.model.QueueType;
import statikk.domain.riotapi.model.Region;
import statikk.domain.riotapi.model.SummonerDto;
import statikk.domain.riotapi.service.RiotApiService;
import statikk.domain.service.LolMatchService;
import statikk.domain.service.LolSummonerService;

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
    LolSummonerService lolSummonerService;

    public MatchMiningService(RiotApiService riotApiService) {
        this.riotApiService = riotApiService;
    }

    public int mineMatches(Region region) throws InterruptedException {
        Map<Long, LolMatch> minedMatches = new HashMap<>();
        List<LolSummoner> summoners = getSummonersToMine(region);
        summoners.stream().forEach(summoner -> {
            MatchListDto recentGames = riotApiService.getRecentGames(region, summoner.getAccountId());
            if (recentGames == null || recentGames.getMatches() == null || recentGames.getMatches().isEmpty()) {
                return;
            }
            if (summoner.getLastPlayedDate().before(new Date(recentGames.getMatches().get(0).getTimestamp()))) {
                summoner.setLastPlayedDate(new Date(recentGames.getMatches().get(0).getTimestamp()));
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
                        || minedMatches.containsKey(game.getGameId())
                        || isGameTooOld(game)) {
                    continue;
                }
                minedMatches.put(game.getGameId(), new LolMatch(game));
            }
        });
        Logger.getLogger(MatchMiningService.class.getName())
                .log(Level.INFO, "Inserting " + minedMatches.size() + " matches that were found for " + region + ".");
        int newMatchesMined = lolMatchService.batchInsert(minedMatches.values());

        Logger.getLogger(MatchMiningService.class.getName())
                .log(Level.INFO, newMatchesMined + " matches were successfully inserted for " + region + ".");
        summoners.stream().forEach(summoner -> summoner.setLastMinedDate(new Date()));
        lolSummonerService.addOrUpdate(summoners);
        Logger.getLogger(MatchMiningService.class.getName())
                .log(Level.INFO, summoners.size() + " summoners were successfully mined for " + region + ".");

        return newMatchesMined;
    }

    private List<LolSummoner> getSummonersToMine(Region region) {
        List<LolSummoner> summoners = lolSummonerService.getRecentSummonersToMine(region);
        if (summoners.isEmpty()) {
            summoners = getSummmonersFromFeaturedGames(region);
        }
        return summoners;
    }

    private List<LolSummoner> getSummmonersFromFeaturedGames(Region region) {
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
            return getSummmonersFromFeaturedGames(region);
        }

        List<String> accountNames = games.getGameList()
                .stream()
                .map((game) -> SummonerDto.getKeyFromName(game.getParticipants().get(rand.nextInt(game.getParticipants().size()) % game.getParticipants().size()).getSummonerName()))
                .collect(Collectors.toList());

        return accountNames
                .stream()
                .map((name) -> riotApiService.getSummonerByName(region, name))
                .filter((summonerData) -> summonerData != null)
                .map((summoner) -> new LolSummoner(summoner.getAccountId(), summoner.getId(), region, new Date(), new Date()))
                .collect(Collectors.toList());
    }

    private boolean isGameTooOld(MatchReferenceDto game) {
        Date oneWeekago = new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 24L * 7L);
        Date gameTime = new Date(game.getTimestamp());
        return gameTime.before(oneWeekago);
    }

}
