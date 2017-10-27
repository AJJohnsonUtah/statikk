/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.riotapi.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author AJ
 */
public class FeaturedGames implements Serializable {

    private long clientRefreshInterval;
    private List<FeaturedGameInfo> gameList;

    public long getClientRefreshInterval() {
        return clientRefreshInterval;
    }

    public List<FeaturedGameInfo> getGameList() {
        return gameList;
    }

}
