/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.framework.riotapi.service;

import statikk.framework.riotapi.model.ChampionMastery;
import statikk.framework.riotapi.model.Region;
import statikk.framework.riotapi.model.FeaturedGames;
import statikk.framework.riotapi.model.ItemListDto;
import statikk.framework.riotapi.model.MatchDetail;
import statikk.framework.riotapi.model.RecentGamesDto;
import statikk.framework.riotapi.model.SummonerDto;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;
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
public class RiotApiService {

    private final String RIOT_API_KEY;
    private final String STATIC_DATA_HOST_URL_PREFIX = "https://global.api.pvp.net/api/lol/static-data/";
    private final String STATIC_DATA_HOST_URL_SUFFIX = "/v1.2/";
    private final String DYNAMIC_DATA_HOST_URL_PREFIX = "https://";
    private final String DYNAMIC_DATA_HOST_URL_SUFFIX = ".api.pvp.net";
    private final RestTemplate restTemplate;

    @Autowired
    RiotApiKeyLimitService riotApiKeytLimitService;

    public RiotApiService() throws IOException {
        RIOT_API_KEY = "RGAPI-d309f20c-14c4-4c93-9d7d-eebdf291578a";
        restTemplate = new RestTemplate();
    }

    public RiotApiService(String key) {
        RIOT_API_KEY = key;
        restTemplate = new RestTemplate();
    }

    public String getStaticChampionsData(Region region) {
        String url = getStaticURLWithAPIKey(region, "champion", "&champData=image");
        ParameterizedTypeReference<String> typeRef = new ParameterizedTypeReference<String>() {
        };
        return getRiotApiRequest(url, false, typeRef);
    }

    public String getStaticChampionData(Region region, long championId) {
        String url = getStaticURLWithAPIKey(region, "champion/" + championId, "&champData=all");
        ParameterizedTypeReference<String> typeRef = new ParameterizedTypeReference<String>() {
        };
        return getRiotApiRequest(url, false, typeRef);
    }

    public MatchDetail getMatchDetailWithoutTimeline(Region region, Long matchId) {
        return getMatchDetail(region, matchId, false);
    }

    public MatchDetail getMatchDetailWithTimeline(Region region, Long matchId) {
        return getMatchDetail(region, matchId, true);
    }

    private MatchDetail getMatchDetail(Region region, Long matchId, boolean includeMatchId) throws HttpServerErrorException {
        String url = getDynamicURLWithAPIKey(region, "/api/lol/" + region.toString() + "/v2.2/match/" + matchId, "&includeTimeline=" + includeMatchId);
        ParameterizedTypeReference<MatchDetail> typeRef = new ParameterizedTypeReference<MatchDetail>() {
        };
        return getRiotApiRequest(url, true, typeRef);
    }

    public ItemListDto getItemsData(Region region) {
        String url = getStaticURLWithAPIKey(region, "item", "&itemListData=all");
        ParameterizedTypeReference<ItemListDto> typeRef = new ParameterizedTypeReference<ItemListDto>() {
        };
        return getRiotApiRequest(url, false, typeRef);
    }

    public String getStaticItemsData(Region region) {
        String url = getStaticURLWithAPIKey(region, "item", "&itemListData=all");
        ParameterizedTypeReference<String> typeRef = new ParameterizedTypeReference<String>() {
        };
        return getRiotApiRequest(url, false, typeRef);
    }

    public RecentGamesDto getRecentGames(Region region, Long summonerId) {
        String url = getDynamicURLWithAPIKey(region, "/api/lol/" + region.toString() + "/v1.3/game/by-summoner/" + summonerId + "/recent");
        ParameterizedTypeReference<RecentGamesDto> typeRef = new ParameterizedTypeReference<RecentGamesDto>() {
        };
        return getRiotApiRequest(url, true, typeRef);
    }

    public FeaturedGames getFeaturedGames(Region region) {
        String url = getDynamicURLWithAPIKey(region, "/observer-mode/rest/featured");
        ParameterizedTypeReference<FeaturedGames> typeRef = new ParameterizedTypeReference<FeaturedGames>() {
        };
        return getRiotApiRequest(url, true, typeRef);
    }

    public String getCurrentGame(Region region, long summonerId) {
        String url = getDynamicURLWithAPIKey(region, "/consumer/getSpectatorGameInfo/" + region.getPlatformId() + "/" + summonerId);
        ParameterizedTypeReference<String> typeRef = new ParameterizedTypeReference<String>() {
        };
        return getRiotApiRequest(url, true, typeRef);
    }

    public String getChampionMastery(Region region, long summonerId) {
        String url = getDynamicURLWithAPIKey(region, "/championmastery/location/" + region.getPlatformId() + "/player/" + summonerId + "/champions");
        ParameterizedTypeReference<String> typeRef = new ParameterizedTypeReference<String>() {
        };
        return getRiotApiRequest(url, true, typeRef);
    }

    public List<ChampionMastery> getChampionMasteryData(Region region, long summonerId) {
        String url = getDynamicURLWithAPIKey(region, "/championmastery/location/" + region.getPlatformId() + "/player/" + summonerId + "/champions");
        ParameterizedTypeReference<List<ChampionMastery>> typeRef = new ParameterizedTypeReference<List<ChampionMastery>>() {
        };
        return getRiotApiRequest(url, true, typeRef);
    }

    public String getTopChampionMastery(Region region, long summonerId) {
        String url = getDynamicURLWithAPIKey(region, "/championmastery/location/" + region.getPlatformId() + "/player/" + summonerId + "/topchampions");
        ParameterizedTypeReference<String> typeRef = new ParameterizedTypeReference<String>() {
        };
        return getRiotApiRequest(url, true, typeRef);
    }

    public List<ChampionMastery> getTopChampionMasteryData(Region region, long summonerId) {
        String url = getDynamicURLWithAPIKey(region, "/championmastery/location/" + region.getPlatformId() + "/player/" + summonerId + "/topchampions");
        ParameterizedTypeReference<List<ChampionMastery>> typeRef = new ParameterizedTypeReference<List<ChampionMastery>>() {
        };
        return getRiotApiRequest(url, true, typeRef);
    }

    public Map<String, SummonerDto> getSummonersByName(Region region, String names) {
        ParameterizedTypeReference<Map<String, SummonerDto>> typeRef = new ParameterizedTypeReference<Map<String, SummonerDto>>() {
        };
        String url = getDynamicURLWithAPIKey(region, "/api/lol/" + region.toString() + "/v1.4/summoner/by-name/" + names);
        return getRiotApiRequest(url, true, typeRef);
    }

    private String getStaticURLWithAPIKey(Region region, String url) {
        return appendRiotApiKey(STATIC_DATA_HOST_URL_PREFIX + region.toString() + STATIC_DATA_HOST_URL_SUFFIX + url);
    }

    public String getStaticURLWithAPIKey(Region region, String url, String queryParams) {
        return appendQueryParamsToURL(getStaticURLWithAPIKey(region, url), queryParams);
    }

    public String getDynamicURLWithAPIKey(Region region, String url) {
        return appendRiotApiKey(DYNAMIC_DATA_HOST_URL_PREFIX + region.toString() + DYNAMIC_DATA_HOST_URL_SUFFIX + url);
    }

    public String getDynamicURLWithAPIKey(Region region, String url, String queryParams) {
        return appendQueryParamsToURL(getDynamicURLWithAPIKey(region, url), queryParams);
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
        return getRiotApiRequest(url, addsToKeyLimit, typeReference, 3);
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
            } catch (HttpClientErrorException ex2) {
                riotApiKeytLimitService.waitSeconds();
                return getRiotApiRequest(url, addsToKeyLimit, typeReference, timesToTry - 1);
            }
        } catch (HttpClientErrorException ex) {
            riotApiKeytLimitService.waitSeconds();
            return getRiotApiRequest(url, addsToKeyLimit, typeReference, timesToTry - 1);
        }
        if (response == null) {
            riotApiKeytLimitService.waitMinutes();
            return null;
        } else if (addsToKeyLimit) {
            String limitHeader = response.getHeaders().getFirst("X-Rate-Limit-Count");
            riotApiKeytLimitService.handleLimitHeader(limitHeader);
        }

        return (T) response.getBody();
    }
}