/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.riotapi.model;

import com.njin.loltheory.entity.LolVersion;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author AJ
 */
public class MatchDetail implements Serializable {

    private MapType mapId;
    private long matchCreation;
    private long matchDuration;
    private long matchId;
    private GameMode matchMode;
    private GameType matchType;
    private LolVersion matchVersion;
    private List<ParticipantIdentity> participantIdentities;
    private List<MatchParticipant> participants;
    private String platformId;
    private QueueType queueType;
    private Region region;
    private Season season;
    private List<Team> teams;
    private Timeline timeline;
    private ErrorStatus status;

    public ErrorStatus getStatus() {
        return status;
    }

    public MapType getMapId() {
        return mapId;
    }

    public long getMatchCreation() {
        return matchCreation;
    }

    public long getMatchDuration() {
        return matchDuration;
    }

    public long getMatchId() {
        return matchId;
    }

    public GameMode getMatchMode() {
        return matchMode;
    }

    public GameType getMatchType() {
        return matchType;
    }

    public LolVersion getMatchVersion() {
        return matchVersion;
    }

    public List<ParticipantIdentity> getParticipantIdentities() {
        return participantIdentities;
    }

    public List<MatchParticipant> getParticipants() {
        return participants;
    }

    public String getPlatformId() {
        return platformId;
    }

    public QueueType getQueueType() {
        return queueType;
    }

    public Region getRegion() {
        return region;
    }

    public Season getSeason() {
        return season;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public Timeline getTimeline() {
        return timeline;
    }

}
