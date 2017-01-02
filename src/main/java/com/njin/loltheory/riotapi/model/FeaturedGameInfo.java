/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.riotapi.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author AJ
 */
public class FeaturedGameInfo implements Serializable {

    private List<BannedChampion> bannedChampions;
    private long gameId;
    private long gameLength;
    private GameMode gameMode;
    private QueueType gameQueueConfigId;
    private long gameStartTime;
    private GameType gameType;
    private long mapId;
    private Observer observers;
    private List<Participant> participants;
    private String platformId;

    public List<BannedChampion> getBannedChampions() {
        return bannedChampions;
    }

    public long getGameId() {
        return gameId;
    }

    public long getGameLength() {
        return gameLength;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public QueueType getGameQueueConfigId() {
        return gameQueueConfigId;
    }

    public long getGameStartTime() {
        return gameStartTime;
    }

    public GameType getGameType() {
        return gameType;
    }

    public long getMapId() {
        return mapId;
    }

    public Observer getObservers() {
        return observers;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public String getPlatformId() {
        return platformId;
    }

}
