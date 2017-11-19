/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.riotapi.model;

import statikk.domain.entity.LolVersion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author AJ
 */
public class MatchDetail implements Serializable {

    private Integer seasonId;
    private QueueType queueId;
    private Long gameId;
    private List<ParticipantIdentityDto> participantIdentities;
    private LolVersion gameVersion;
    private Region platformId;
    private GameMode gameMode;
    private MapType mapId;
    private GameType gameType;
    private List<TeamStatsDto> teams;
    private List<ParticipantDto> participants;
    private Long gameDuration;
    private Long gameCreation;
    private Timeline timeline;

    private LolTeam winner;

    private ErrorStatus status;

    public Integer getSeasonId() {
        return seasonId;
    }

    public QueueType getQueueId() {
        return queueId;
    }

    public Long getGameId() {
        return gameId;
    }

    public List<ParticipantIdentityDto> getParticipantIdentities() {
        return participantIdentities;
    }

    public LolVersion getGameVersion() {
        return gameVersion;
    }

    public Region getPlatformId() {
        return platformId;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public GameType getGameType() {
        return gameType;
    }

    public List<TeamStatsDto> getTeams() {
        return teams;
    }

    public List<ParticipantDto> getParticipants() {
        return participants;
    }

    public Long getGameDuration() {
        return gameDuration;
    }

    public Long getGameCreation() {
        return gameCreation;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setGameVersion(LolVersion matchVersion) {
        this.gameVersion = matchVersion;
    }

    public void setStatus(ErrorStatus status) {
        this.status = status;
    }

    public ErrorStatus getStatus() {
        return status;
    }

    public MapType getMapId() {
        return mapId;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public LolTeam getWinner() {
        if (winner != null) {
            return winner;
        }
        for (TeamStatsDto team : teams) {
            if (team.isWinner()) {
                winner = team.getTeamId();
                return team.getTeamId();
            }
        }
        return null;
    }

    public List<ParticipantDto> getBlueTeam() {
        return getTeam(LolTeam.BLUE);
    }

    public List<ParticipantDto> getPurpleTeam() {
        return getTeam(LolTeam.PURPLE);
    }

    public List<ParticipantDto> getTeam(LolTeam teamId) {
        return participants.stream()
                .filter(a -> a.getTeamId() == teamId)
                .collect(Collectors.toList());
    }

    public ParticipantDto getParticipantFromId(int participantId) {
        if (this.participants.get(participantId - 1).getParticipantId() == participantId) {
            return this.participants.get(participantId - 1);
        }
        for (ParticipantDto p : this.participants) {
            if (p.getParticipantId() == participantId) {
                return p;
            }
        }
        return null;
    }

    public List<TeamBansDto> getBannedChampions() {
        List<TeamBansDto> bannedChampions = new ArrayList<>();
        teams.stream().forEach((team) -> {
            bannedChampions.addAll(team.getBans());
        });
        return bannedChampions;
    }

    public List<Long> getParticipantSummonerIds() {

        if (getParticipantIdentities().get(0).getPlayer() != null) {
            return getParticipantIdentities()
                    .stream()
                    .map(s -> s.getPlayer().getSummonerId())
                    .sequential()
                    .collect(Collectors.toList());
        }
        return Collections.EMPTY_LIST;
    }
}
