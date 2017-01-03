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
public class MatchParticipant implements Serializable {

    private Rank highestAchievedSeasonTier;
    private List<Mastery> masteries;
    private int participantId;
    private List<Rune> runes;
    private int spell1Id;
    private int spell2Id;
    private ParticipantStats stats;
    private int teamId;
    private ParticipantTimeline timeline;

    public Rank getHighestAchievedSeasonTier() {
        return highestAchievedSeasonTier;
    }

    public List<Mastery> getMasteries() {
        return masteries;
    }

    public int getParticipantId() {
        return participantId;
    }

    public List<Rune> getRunes() {
        return runes;
    }

    public int getSpell1Id() {
        return spell1Id;
    }

    public int getSpell2Id() {
        return spell2Id;
    }

    public ParticipantStats getStats() {
        return stats;
    }

    public int getTeamId() {
        return teamId;
    }

    public ParticipantTimeline getTimeline() {
        return timeline;
    }

}
