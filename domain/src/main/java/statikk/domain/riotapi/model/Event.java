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
public class Event implements Serializable {

    private AscendedType ascendedType;
    private List<Integer> assistingParticipantIds;
    private BuildingType buildingType;
    private int creatorId;
    private EventType type;
    private int itemAfter;
    private int itemBefore;
    private int itemId;
    private int killerId;
    private LaneType laneType;
    private LevelUpType levelUpType;
    private String monsterSubType;
    private MonsterType monsterType;
    private int participantId;
    private CapturePoint pointCaptured;
    private Position position;
    private int skillSlot;
    private int teamId;
    private long timestamp;
    private TowerType towerType;
    private int victimId;
    private WardType wardType;

    public AscendedType getAscendedType() {
        return ascendedType;
    }

    public List<Integer> getAssistingParticipantIds() {
        return assistingParticipantIds;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public EventType getType() {
        return type;
    }

    public int getItemAfter() {
        return itemAfter;
    }

    public int getItemBefore() {
        return itemBefore;
    }

    public int getItemId() {
        return itemId;
    }

    public int getKillerId() {
        return killerId;
    }

    public LaneType getLaneType() {
        return laneType;
    }

    public LevelUpType getLevelUpType() {
        return levelUpType;
    }

    public String getMonsterSubType() {
        return monsterSubType;
    }

    public MonsterType getMonsterType() {
        return monsterType;
    }

    public int getParticipantId() {
        return participantId;
    }

    public CapturePoint getPointCaptured() {
        return pointCaptured;
    }

    public Position getPosition() {
        return position;
    }

    public int getSkillSlot() {
        return skillSlot;
    }

    public int getTeamId() {
        return teamId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public TowerType getTowerType() {
        return towerType;
    }

    public int getVictimId() {
        return victimId;
    }

    public WardType getWardType() {
        return wardType;
    }

}
