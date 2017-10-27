/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.riotapi.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
    @Value("${riot.api.key}")
    private String RIOT_API_KEY;

    private final String RIOT_API_URL_PROTOCOL = "https://";
    private final String RIOT_API_URL_DOMAIN = ".api.riotgames.com";
    private final RestTemplate restTemplate;

    public RiotApiService() throws IOException {
        restTemplate = new RestTemplate();
    }

    public String getStaticChampionsData(Region region) {
        String url = getURLWithAPIKey(region, "/lol/static-data/v3/champions", "&champListData=image");
        ParameterizedTypeReference<String> typeRef = new ParameterizedTypeReference<String>() {
        };
        return getRiotApiRequest(url, false, typeRef);
    }

    public String getStaticChampionData(Region region, long championId) {
        String url = getURLWithAPIKey(region, "/lol/static-data/v3/champions/" + championId, "&champData=all");
        ParameterizedTypeReference<String> typeRef = new ParameterizedTypeReference<String>() {
        };
        return getRiotApiRequest(url, false, typeRef);
    }

    public MatchDetail getMatchDetailWithoutTimeline(Region region, Long matchId) {
        return getMatchDetail(region, matchId);
    }

    public MatchDetail getMatchDetailWithTimeline(Region region, Long matchId) {
        MatchDetail matchDetail = getMatchDetail(region, matchId);
        if (matchDetail == null) {
            System.out.println("Match " + matchId + " for " + region + " not found.");
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
        return getRiotApiRequest(url, false, typeRef);
    }

    private MatchDetail getMatchDetail(Region region, Long matchId) throws HttpServerErrorException {
        String url = getURLWithAPIKey(region, "/lol/match/v3/matches/" + matchId);
        ParameterizedTypeReference<MatchDetail> typeRef = new ParameterizedTypeReference<MatchDetail>() {
        };
        return getRiotApiRequest(url, true, typeRef);
    }

    public ItemListDto getItemsData(Region region) {
        String url = getURLWithAPIKey(region, "/lol/static-data/v3/items", "&itemListData=all");
        ParameterizedTypeReference<ItemListDto> typeRef = new ParameterizedTypeReference<ItemListDto>() {
        };
        return getRiotApiRequest(url, false, typeRef);
    }

    public String getStaticItemsData(Region region) {
        String url = getURLWithAPIKey(region, "/lol/static-data/v3/items", "&itemListData=all");
        ParameterizedTypeReference<String> typeRef = new ParameterizedTypeReference<String>() {
        };
        return getRiotApiRequest(url, false, typeRef);
    }

    public MatchListDto getRecentGames(Region region, Long accountId) {
        String url = getURLWithAPIKey(region, "/lol/match/v3/matchlists/by-account/" + accountId + "/recent");
        ParameterizedTypeReference<MatchListDto> typeRef = new ParameterizedTypeReference<MatchListDto>() {
        };
        return getRiotApiRequest(url, true, typeRef);
    }

    public FeaturedGames getFeaturedGames(Region region) {
        String url = getURLWithAPIKey(region, "/lol/spectator/v3/featured-games");
        ParameterizedTypeReference<FeaturedGames> typeRef = new ParameterizedTypeReference<FeaturedGames>() {
        };
        return getRiotApiRequest(url, true, typeRef);
    }

    public String getCurrentGame(Region region, long summonerId) {
        String url = getURLWithAPIKey(region, "/lol/spectator/v3/active-games/by-summoner/" + summonerId);
        ParameterizedTypeReference<String> typeRef = new ParameterizedTypeReference<String>() {
        };
        return getRiotApiRequest(url, true, typeRef);
    }

    public String getChampionMastery(Region region, long summonerId) {
        String url = getURLWithAPIKey(region, "//lol/champion-mastery/v3/champion-masteries/by-summoner/" + summonerId);
        ParameterizedTypeReference<String> typeRef = new ParameterizedTypeReference<String>() {
        };
        return getRiotApiRequest(url, true, typeRef);
    }

    public List<ChampionMastery> getChampionMasteryData(Region region, long summonerId) {
        String url = getURLWithAPIKey(region, "//lol/champion-mastery/v3/champion-masteries/by-summoner/" + summonerId);
        ParameterizedTypeReference<List<ChampionMastery>> typeRef = new ParameterizedTypeReference<List<ChampionMastery>>() {
        };
        return getRiotApiRequest(url, true, typeRef);
    }

    public SummonerDto getSummonerByName(Region region, String name) {
        ParameterizedTypeReference<SummonerDto> typeRef = new ParameterizedTypeReference<SummonerDto>() {
        };
        String url = getURLWithAPIKey(region, "/lol/summoner/v3/summoners/by-name/" + name);
        return getRiotApiRequest(url, true, typeRef);
    }

    public String getURLWithAPIKey(Region region, String urlPath) {
        return appendRiotApiKey(RIOT_API_URL_PROTOCOL + region.getPlatformId() + RIOT_API_URL_DOMAIN + urlPath);
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

    private String getResponseFromURL(String urlString) {
        StringBuilder result = new StringBuilder();
        URL url;
        try {
            url = new URL(urlString);

        } catch (MalformedURLException ex) {
            Logger.getLogger(RiotApiService.class
                    .getName()).log(Level.SEVERE, null, ex);
            return "Invalid URL: " + urlString;
        }
        HttpURLConnection conn;
        try {
            conn = (HttpURLConnection) url.openConnection();

        } catch (IOException ex) {
            Logger.getLogger(RiotApiService.class
                    .getName()).log(Level.SEVERE, null, ex);
            return "Unable to establish connection for URL: " + urlString;
        }
        try {
            conn.setRequestMethod("GET");

        } catch (ProtocolException ex) {
            Logger.getLogger(RiotApiService.class
                    .getName()).log(Level.SEVERE, null, ex);
            return "GET not allowed for URL: " + urlString;
        }
        try {

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();

        } catch (IOException ex) {
            Logger.getLogger(RiotApiService.class
                    .getName()).log(Level.SEVERE, null, ex);
            return "IO Error for URL: " + urlString;
        }

        return result.toString();

    }

    private <T> T getRiotApiRequest(String url, boolean addsToKeyLimit, ParameterizedTypeReference<T> typeReference) {
        return getRiotApiRequest(url, addsToKeyLimit, typeReference, 2);
    }

    private <T> T getRiotApiRequest(String url, boolean addsToKeyLimit, ParameterizedTypeReference<T> typeReference, int timesToTry) {
        Logger.getLogger(RiotApiService.class
                .getName()).log(Level.INFO, url);
        if (timesToTry <= 0) {
            return null;
        }
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
                return null;
            } catch (HttpClientErrorException ex2) {
                waitUntilTime(System.currentTimeMillis() + 300);
                return getRiotApiRequest(url, addsToKeyLimit, typeReference, timesToTry - 1);
            }
        } catch (HttpClientErrorException ex) {
            switch (ex.getStatusCode()) {
                case TOO_MANY_REQUESTS:
                    String limitHeader = ex.getResponseHeaders().getFirst("Retry-After");
                    handleLimitHeader(limitHeader);
                    break;
                default:
                    Logger.getLogger(RiotApiService.class
                            .getName()).log(Level.SEVERE, "Client exception fetching data from " + url + ".", ex);
                    waitUntilTime(System.currentTimeMillis() + 300);
                    return getRiotApiRequest(url, addsToKeyLimit, typeReference, timesToTry - 1);
            }
        }

        return (T) response.getBody();

    }

    private void waitUntilTime(long time) {
        try {
            Thread.sleep(time - System.currentTimeMillis());
        } catch (InterruptedException ex) {
            Logger.getLogger(RiotApiKeyLimitService.class.getName()).log(Level.SEVERE, null, ex);
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

        Logger.getLogger(RiotApiKeyLimitService.class
                .getName()).log(Level.INFO, "Waiting {0} seconds before making next API request.", secondsToWait);
        waitUntilTime(System.currentTimeMillis() + (secondsToWait + 1) * 1000);
    }

}
