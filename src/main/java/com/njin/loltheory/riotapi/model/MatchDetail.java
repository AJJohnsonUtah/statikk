/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.riotapi.model;

import com.njin.loltheory.entity.LolVersion;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

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
    private LolTeam winner;    

    public void setMatchVersion(LolVersion matchVersion) {
        this.matchVersion = matchVersion;
    }

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

    public LolTeam getWinner() {
        if (winner != null) {
            return winner;
        }
        for (Team team : teams) {
            if (team.isWinner()) {
                return team.getTeamId();
            }
        }
        throw new RuntimeException("No winner found.");
    }

    public List<MatchParticipant> getBlueTeam() {
        return getTeam(LolTeam.BLUE);
    }

    public List<MatchParticipant> getPurpleTeam() {
        return getTeam(LolTeam.PURPLE);
    }

    public List<MatchParticipant> getTeam(LolTeam teamId) {
        return participants.stream()
                .filter(a -> a.getTeamId() == teamId)
                .collect(Collectors.toList());
    }

    public MatchParticipant getParticipantFromId(int participantId) {
        if (this.participants.get(participantId - 1).getParticipantId() == participantId) {
            return this.participants.get(participantId - 1);
        }
        for (MatchParticipant p : this.participants) {
            if (p.getParticipantId() == participantId) {
                return p;
            }
        }
        return null;
    }
}
