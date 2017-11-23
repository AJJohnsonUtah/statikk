/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.riotapi.service;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import statikk.domain.riotapi.model.ChampionMastery;
import statikk.domain.riotapi.model.FeaturedGames;
import statikk.domain.riotapi.model.ItemListDto;
import statikk.domain.riotapi.model.MatchDetail;
import statikk.domain.riotapi.model.MatchListDto;
import statikk.domain.riotapi.model.Region;
import statikk.domain.riotapi.model.SummonerDto;
import statikk.domain.riotapi.model.Timeline;

/**
 *
 * @author AJ
 */
@Service
public class RiotApiService {

    /**
     * Riot API Key is populated from a properties file.
     */
    @Value(value = "${riot.api.key}")
    private String RIOT_API_KEY;

    private final String RIOT_API_URL_PROTOCOL = "https://";
    private final String RIOT_API_URL_DOMAIN = ".api.riotgames.com";
    private final RestTemplate restTemplate;

    public RiotApiService() throws IOException {
        restTemplate = new RestTemplate();
    }

    public String getStaticChampionsData(Region region) {
        String url = getURLWithAPIKey(region, "/lol/static-data/v3/champions", "&champListData=image&dataById=true");
        ParameterizedTypeReference<String> typeRef = new ParameterizedTypeReference<String>() {
        };
        return getRiotApiRequest(url, typeRef);
    }

    public String getStaticChampionData(Region region, long championId) {
        String url = getURLWithAPIKey(region, "/lol/static-data/v3/champions/" + championId, "&champData=all");
        ParameterizedTypeReference<String> typeRef = new ParameterizedTypeReference<String>() {
        };
        return getRiotApiRequest(url, typeRef);
    }

    public MatchDetail getMatchDetailWithoutTimeline(Region region, Long matchId) {
        return getMatchDetail(region, matchId);
    }

    public MatchDetail getMatchDetailWithTimeline(Region region, Long matchId) {
        MatchDetail matchDetail = getMatchDetail(region, matchId);
        if (matchDetail == null) {
            Logger.getLogger(RiotApiService.class
                    .getName()).log(Level.WARNING, "Match {0} for {1} not found.", new Object[]{matchId, region});
            System.out.println();
            return null;
        }
        Timeline timeline = getMatchTimeline(region, matchId);
        matchDetail.setTimeline(timeline);
        return matchDetail;
    }

    private Timeline getMatchTimeline(Region region, Long matchId) {
        String url = getURLWithAPIKey(region, "/lol/match/v3/timelines/by-match/" + matchId);
        ParameterizedTypeReference<Timeline> typeRef = new ParameterizedTypeReference<Timeline>() {
        };
        return getRiotApiRequest(url, typeRef);
    }

    private MatchDetail getMatchDetail(Region region, Long matchId) throws HttpServerErrorException {
        String url = getURLWithAPIKey(region, "/lol/match/v3/matches/" + matchId);
        ParameterizedTypeReference<MatchDetail> typeRef = new ParameterizedTypeReference<MatchDetail>() {
        };
        return getRiotApiRequest(url, typeRef);
    }

    public ItemListDto getItemsData(Region region) {
        String url = getURLWithAPIKey(region, "/lol/static-data/v3/items", "&itemListData=all");
        ParameterizedTypeReference<ItemListDto> typeRef = new ParameterizedTypeReference<ItemListDto>() {
        };
        return getRiotApiRequest(url, typeRef);
    }

    public String getStaticItemsData(Region region) {
        String url = getURLWithAPIKey(region, "/lol/static-data/v3/items", "&itemListData=all");
        ParameterizedTypeReference<String> typeRef = new ParameterizedTypeReference<String>() {
        };
        return getRiotApiRequest(url, typeRef);
    }

    public String getRealmsData(Region region) {
        String url = getURLWithAPIKey(region, "/lol/static-data/v3/realms");
        ParameterizedTypeReference<String> typeRef = new ParameterizedTypeReference<String>() {
        };
        return getRiotApiRequest(url, typeRef);
    }

    public MatchListDto getRecentGames(Region region, Long accountId) {
        String url = getURLWithAPIKey(region, "/lol/match/v3/matchlists/by-account/" + accountId + "/recent");
        ParameterizedTypeReference<MatchListDto> typeRef = new ParameterizedTypeReference<MatchListDto>() {
        };
        return getRiotApiRequest(url, typeRef);
    }

    public FeaturedGames getFeaturedGames(Region region) {
        String url = getURLWithAPIKey(region, "/lol/spectator/v3/featured-games");
        ParameterizedTypeReference<FeaturedGames> typeRef = new ParameterizedTypeReference<FeaturedGames>() {
        };
        return getRiotApiRequest(url, typeRef);
    }

    public String getCurrentGame(Region region, long summonerId) {
        String url = getURLWithAPIKey(region, "/lol/spectator/v3/active-games/by-summoner/" + summonerId);
        ParameterizedTypeReference<String> typeRef = new ParameterizedTypeReference<String>() {
        };
        return getRiotApiRequest(url, typeRef);
    }

    public String getChampionMastery(Region region, long summonerId) {
        String url = getURLWithAPIKey(region, "//lol/champion-mastery/v3/champion-masteries/by-summoner/" + summonerId);
        ParameterizedTypeReference<String> typeRef = new ParameterizedTypeReference<String>() {
        };
        return getRiotApiRequest(url, typeRef);
    }

    public List<ChampionMastery> getChampionMasteryData(Region region, long summonerId) {
        String url = getURLWithAPIKey(region, "//lol/champion-mastery/v3/champion-masteries/by-summoner/" + summonerId);
        ParameterizedTypeReference<List<ChampionMastery>> typeRef = new ParameterizedTypeReference<List<ChampionMastery>>() {
        };
        return getRiotApiRequest(url, typeRef);
    }

    public SummonerDto getSummonerByName(Region region, String name) {
        ParameterizedTypeReference<SummonerDto> typeRef = new ParameterizedTypeReference<SummonerDto>() {
        };
        String url = getURLWithAPIKey(region, "/lol/summoner/v3/summoners/by-name/" + name);
        return getRiotApiRequest(url, typeRef);
    }

    public String getURLWithAPIKey(Region region, String urlPath) {
        return appendRiotApiKey(RIOT_API_URL_PROTOCOL + region.getPlatformId().toLowerCase() + RIOT_API_URL_DOMAIN + urlPath);
    }

    public String getURLWithAPIKey(Region region, String url, String queryParams) {
        return appendQueryParamsToURL(getURLWithAPIKey(region, url), queryParams);
    }

    public String appendQueryParamsToURL(String url, String queryParams) {
        return url + queryParams;
    }

    public String appendRiotApiKey(String urlToAppendTo) {
        return urlToAppendTo + "?api_key=" + RIOT_API_KEY;
    }

    private <T> T getRiotApiRequest(String url, ParameterizedTypeReference<T> typeReference) {
        return getRiotApiRequest(url, typeReference, 2);
    }

    private <T> T getRiotApiRequest(String url, ParameterizedTypeReference<T> typeReference, int timesToTry) {
        Logger.getLogger(RiotApiService.class
                .getName()).log(Level.INFO, url);
        if (timesToTry <= 0) {
            Logger.getLogger(RiotApiService.class
                    .getName()).log(Level.WARNING, "Unable to fetch data from {0}. Returning null.", url);
            return null;
        }
        try {
            ResponseEntity response = restTemplate.exchange(url, HttpMethod.GET, null, typeReference);
            return (response == null ? null : (T) response.getBody());
        } catch (HttpServerErrorException ex) {
            Logger.getLogger(RiotApiService.class
                    .getName()).log(Level.WARNING, "Unable to fetch data from " + url + " (blame RITO).", ex);
            return getRiotApiRequest(url, typeReference, timesToTry - 1);
        } catch (HttpClientErrorException ex) {
            switch (ex.getStatusCode()) {
                case TOO_MANY_REQUESTS:
                    String limitHeader = ex.getResponseHeaders().getFirst("Retry-After");
                    handleLimitHeader(limitHeader);
                    return getRiotApiRequest(url, typeReference, timesToTry - 1);
                case NOT_FOUND:
                    Logger.getLogger(RiotApiService.class
                            .getName()).log(Level.INFO, "404; Data not found for {0}.", url);
                    return null;
                case FORBIDDEN:
                    Logger.getLogger(RiotApiService.class
                            .getName()).log(Level.INFO, "403; Riot API Key has likely expired! {0}", url);
                    return null;
                default:
                    Logger.getLogger(RiotApiService.class
                            .getName()).log(Level.WARNING, "Client exception fetching data from " + url + ".", ex);
                    waitUntilTime(System.currentTimeMillis() + 300);
                    return getRiotApiRequest(url, typeReference, timesToTry - 1);
            }
        } catch (HttpMessageNotReadableException ex) {
            // This is likely caused by a change to an API endpoint (game type?) that hasn't been added yet
            // Log this error with the URL, but still throw it so that we don't keep processing it.
            Logger.getLogger(RiotApiService.class
                    .getName()).log(Level.SEVERE, "Could not parse JSON, likely due to an API change. " + url, ex);
            throw ex;
        }
    }

    private void waitUntilTime(long time) {
        try {
            Thread.sleep(time - System.currentTimeMillis());
        } catch (InterruptedException ex) {
            Logger.getLogger(RiotApiService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Pass the string number of seconds needed to wait before making another
     * request
     *
     * @param limitHeader
     */
    public void handleLimitHeader(String limitHeader) {
        int secondsToWait = Integer.parseInt(limitHeader);

        Logger.getLogger(RiotApiService.class
                .getName()).log(Level.INFO, "Waiting {0} seconds before making next API request.", secondsToWait);
        waitUntilTime(System.currentTimeMillis() + (secondsToWait + 1) * 1000);
    }

}
