/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.riotapi.model;

/**
 *
 * @author AJ
 */
public class ChampionMastery {

    private long championLevel;
    private boolean chestGranted;
    private long championPoints;
    private long championId;
    private long playerId;
    private long championPointsUntilNextLevel;
    private long championPointsSinceLastLevel;
    private long lastPlayTime;

    public ChampionMastery() {
    }

    public long getChampionLevel() {
        return championLevel;
    }

    public void setChampionLevel(long championLevel) {
        this.championLevel = championLevel;
    }

    public boolean isChestGranted() {
        return chestGranted;
    }

    public void setChestGranted(boolean chestGranted) {
        this.chestGranted = chestGranted;
    }

    public long getChampionPoints() {
        return championPoints;
    }

    public void setChampionPoints(long championPoints) {
        this.championPoints = championPoints;
    }

    public long getChampionId() {
        return championId;
    }

    public void setChampionId(long championId) {
        this.championId = championId;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public long getChampionPointsUntilNextLevel() {
        return championPointsUntilNextLevel;
    }

    public void setChampionPointsUntilNextLevel(long championPointsUntilNextLevel) {
        this.championPointsUntilNextLevel = championPointsUntilNextLevel;
    }

    public long getChampionPointsSinceLastLevel() {
        return championPointsSinceLastLevel;
    }

    public void setChampionPointsSinceLastLevel(long championPointsSinceLastLevel) {
        this.championPointsSinceLastLevel = championPointsSinceLastLevel;
    }

    public long getLastPlayTime() {
        return lastPlayTime;
    }

    public void setLastPlayTime(long lastPlayTime) {
        this.lastPlayTime = lastPlayTime;
    }

}
