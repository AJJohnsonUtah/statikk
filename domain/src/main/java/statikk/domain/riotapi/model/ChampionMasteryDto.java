/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.riotapi.model;

import java.io.Serializable;

/**
 *
 * @author Grann
 */
public class ChampionMasteryDto implements Serializable {

    private boolean chestGranted;
    private int championLevel;
    private int championPoints;
    private long championId;
    private long playerId;
    private long championPointsUntilNextLevel;
    private long championPointsSinceLastLevel;
    private long lastPlayTime;

    public boolean isChestGranted() {
        return chestGranted;
    }

    public int getChampionLevel() {
        return championLevel;
    }

    public int getChampionPoints() {
        return championPoints;
    }

    public long getChampionId() {
        return championId;
    }

    public long getPlayerId() {
        return playerId;
    }

    public long getChampionPointsUntilNextLevel() {
        return championPointsUntilNextLevel;
    }

    public long getChampionPointsSinceLastLevel() {
        return championPointsSinceLastLevel;
    }

    public long getLastPlayTime() {
        return lastPlayTime;
    }

}
