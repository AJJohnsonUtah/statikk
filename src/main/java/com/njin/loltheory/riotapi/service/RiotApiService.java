/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.riotapi.service;

import com.njin.loltheory.riotapi.model.Region;
import com.njin.loltheory.riotapi.model.FeaturedGames;
import com.njin.loltheory.riotapi.model.ItemListDto;
import com.njin.loltheory.riotapi.model.MatchDetail;
import com.njin.loltheory.riotapi.model.RecentGamesDto;
import com.njin.loltheory.riotapi.model.SummonerDto;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
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
    private final String STATIC_DATA_HOST_URL = "https://global.api.pvp.net";
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
        String url = getStaticURLWithAPIKey("/api/lol/static-data/" + region.toString() + "/v1.2/champion?champData=image&api_key=");
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
        String url = getDynamicURLWithAPIKey(region, "/api/lol/" + region.toString() + "/v2.2/match/" + matchId + "?includeTimeline=" + includeMatchId + "&api_key=");
        ParameterizedTypeReference<MatchDetail> typeRef = new ParameterizedTypeReference<MatchDetail>() {
        };
        return getRiotApiRequest(url, true, typeRef);
    }

    public ItemListDto getItemsData(Region region) {
        String url = getStaticURLWithAPIKey("/api/lol/static-data/" + region.toString() + "/v1.2/item?itemListData=all&api_key=");
        ParameterizedTypeReference<ItemListDto> typeRef = new ParameterizedTypeReference<ItemListDto>() {
        };
        return getRiotApiRequest(url, false, typeRef);
    }

    public String getStaticItemsData(Region region) {
        String url = getStaticURLWithAPIKey("/api/lol/static-data/" + region.toString() + "/v1.2/item?itemListData=all&api_key=");
        ParameterizedTypeReference<String> typeRef = new ParameterizedTypeReference<String>() {
        };
        return getRiotApiRequest(url, false, typeRef);
    }

    public RecentGamesDto getRecentGames(Region region, Long summonerId) {
        String url = getDynamicURLWithAPIKey(region, "/api/lol/" + region.toString() + "/v1.3/game/by-summoner/" + summonerId + "/recent?api_key=");
        ParameterizedTypeReference<RecentGamesDto> typeRef = new ParameterizedTypeReference<RecentGamesDto>() {
        };
        return getRiotApiRequest(url, true, typeRef);
    }

    public FeaturedGames getFeaturedGames(Region region) {
        String url = getDynamicURLWithAPIKey(region, "/observer-mode/rest/featured?api_key=");
        ParameterizedTypeReference<FeaturedGames> typeRef = new ParameterizedTypeReference<FeaturedGames>() {
        };
        return getRiotApiRequest(url, true, typeRef);

    }

    public Map<String, SummonerDto> getSummonersByName(Region region, String names) {
        ParameterizedTypeReference<Map<String, SummonerDto>> typeRef = new ParameterizedTypeReference<Map<String, SummonerDto>>() {
        };
        String url = getDynamicURLWithAPIKey(region, "/api/lol/" + region.toString() + "/v1.4/summoner/by-name/" + names + "?api_key=");
        return getRiotApiRequest(url, true, typeRef);
    }

    private String getStaticURLWithAPIKey(String url) {
        return STATIC_DATA_HOST_URL + url + RIOT_API_KEY;
    }

    private String getDynamicURLWithAPIKey(Region region, String url) {
        return DYNAMIC_DATA_HOST_URL_PREFIX + region.toString() + DYNAMIC_DATA_HOST_URL_SUFFIX + url + RIOT_API_KEY;
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
//        Logger.getLogger(RiotApiService.class
//                .getName()).log(Level.INFO, url);
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
                return getRiotApiRequest(url, addsToKeyLimit, typeReference);
            }
        } catch (HttpClientErrorException ex) {
            riotApiKeytLimitService.waitSeconds();
            return getRiotApiRequest(url, addsToKeyLimit, typeReference);
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
