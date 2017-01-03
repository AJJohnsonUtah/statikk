/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.riotapi.model;

import java.io.Serializable;

/**
 *
 * @author AJ
 */
public class ParticipantFrame implements Serializable {

    private int currentGold;
    private int dominionScore;
    private int jungleMinionsKilled;
    private int level;
    private int minionsKilled;
    private int participantId;
    private Position position;
    private int teamScore;
    private int totalGold;
    private int xp;

    public int getCurrentGold() {
        return currentGold;
    }

    public int getDominionScore() {
        return dominionScore;
    }

    public int getJungleMinionsKilled() {
        return jungleMinionsKilled;
    }

    public int getLevel() {
        return level;
    }

    public int getMinionsKilled() {
        return minionsKilled;
    }

    public int getParticipantId() {
        return participantId;
    }

    public Position getPosition() {
        return position;
    }

    public int getTeamScore() {
        return teamScore;
    }

    public int getTotalGold() {
        return totalGold;
    }

    public int getXp() {
        return xp;
    }

}
