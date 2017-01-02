/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.riotapi.service;

import com.njin.loltheory.riotapi.model.Region;
import com.njin.loltheory.riotapi.model.FeaturedGames;
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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
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

    public RiotApiService() throws IOException {
        RIOT_API_KEY = "c08236ae-d093-4c4e-978f-96fe66140466";
        restTemplate = new RestTemplate();
    }

    public RiotApiService(String key) {
        RIOT_API_KEY = key;
        restTemplate = new RestTemplate();
    }

    public String getStaticChampionsData(Region region) {
        return getResponseFromURL(getStaticURLWithAPIKey("/api/lol/static-data/" + region.toString() + "/v1.2/champion?champData=image&api_key="));
    }

    public String getStaticItemsData(Region region) {
        return getResponseFromURL(getStaticURLWithAPIKey("/api/lol/static-data/" + region.toString() + "/v1.2/item?itemListData=all&api_key="));
    }

    public RecentGamesDto
            getRecentGames(Region region, Long summonerId) {
        return restTemplate.getForObject(getDynamicURLWithAPIKey(region, "/api/lol/" + region.toString() + "/v1.3/game/by-summoner/" + summonerId + "/recent?api_key="), RecentGamesDto.class
        );
    }

    public FeaturedGames
            getFeaturedGames(Region region) {
        return restTemplate.getForObject(getDynamicURLWithAPIKey(region, "/observer-mode/rest/featured?api_key="), FeaturedGames.class
        );
    }

    public Map<String, SummonerDto> getSummonersByName(Region region, String names) {
        ParameterizedTypeReference<Map<String, SummonerDto>> typeRef = new ParameterizedTypeReference<Map<String, SummonerDto>>() {
        };
        String url = getDynamicURLWithAPIKey(region, "/api/lol/" + region.toString() + "/v1.4/summoner/by-name/" + names + "?api_key=");
        return restTemplate.exchange(url, HttpMethod.GET, null, typeRef).getBody();
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

}
