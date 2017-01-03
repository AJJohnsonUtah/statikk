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
public class ParticipantTimeline implements Serializable {

    private ParticipantTimelineData ancientGolemAssistsPerMinCounts;
    private ParticipantTimelineData ancientGolemKillsPerMinCounts;
    private ParticipantTimelineData assistedLaneDeathsPerMinDeltas;
    private ParticipantTimelineData assistedLaneKillsPerMinDeltas;
    private ParticipantTimelineData baronAssistsPerMinCounts;
    private ParticipantTimelineData baronKillsPerMinCounts;
    private ParticipantTimelineData creepsPerMinDeltas;
    private ParticipantTimelineData csDiffPerMinDeltas;
    private ParticipantTimelineData damageTakenDiffPerMinDeltas;
    private ParticipantTimelineData damageTakenPerMinDeltas;
    private ParticipantTimelineData dragonAssistsPerMinCounts;
    private ParticipantTimelineData dragonKillsPerMinCounts;
    private ParticipantTimelineData elderLizardAssistsPerMinCounts;
    private ParticipantTimelineData elderLizardKillsPerMinCounts;
    private ParticipantTimelineData goldPerMinDeltas;
    private ParticipantTimelineData inhibitorAssistsPerMinCounts;
    private ParticipantTimelineData inhibitorKillsPerMinCounts;
    private MatchLane lane;
    private MatchRole role;
    private ParticipantTimelineData towerAssistsPerMinCounts;
    private ParticipantTimelineData towerKillsPerMinCounts;
    private ParticipantTimelineData towerKillsPerMinDeltas;
    private ParticipantTimelineData vilemawAssistsPerMinCounts;
    private ParticipantTimelineData vilemawKillsPerMinCounts;
    private ParticipantTimelineData wardsPerMinDeltas;
    private ParticipantTimelineData xpDiffPerMinDeltas;
    private ParticipantTimelineData xpPerMinDeltas;

    public ParticipantTimelineData getAncientGolemAssistsPerMinCounts() {
        return ancientGolemAssistsPerMinCounts;
    }

    public ParticipantTimelineData getAncientGolemKillsPerMinCounts() {
        return ancientGolemKillsPerMinCounts;
    }

    public ParticipantTimelineData getAssistedLaneDeathsPerMinDeltas() {
        return assistedLaneDeathsPerMinDeltas;
    }

    public ParticipantTimelineData getAssistedLaneKillsPerMinDeltas() {
        return assistedLaneKillsPerMinDeltas;
    }

    public ParticipantTimelineData getBaronAssistsPerMinCounts() {
        return baronAssistsPerMinCounts;
    }

    public ParticipantTimelineData getBaronKillsPerMinCounts() {
        return baronKillsPerMinCounts;
    }

    public ParticipantTimelineData getCreepsPerMinDeltas() {
        return creepsPerMinDeltas;
    }

    public ParticipantTimelineData getCsDiffPerMinDeltas() {
        return csDiffPerMinDeltas;
    }

    public ParticipantTimelineData getDamageTakenDiffPerMinDeltas() {
        return damageTakenDiffPerMinDeltas;
    }

    public ParticipantTimelineData getDamageTakenPerMinDeltas() {
        return damageTakenPerMinDeltas;
    }

    public ParticipantTimelineData getDragonAssistsPerMinCounts() {
        return dragonAssistsPerMinCounts;
    }

    public ParticipantTimelineData getDragonKillsPerMinCounts() {
        return dragonKillsPerMinCounts;
    }

    public ParticipantTimelineData getElderLizardAssistsPerMinCounts() {
        return elderLizardAssistsPerMinCounts;
    }

    public ParticipantTimelineData getElderLizardKillsPerMinCounts() {
        return elderLizardKillsPerMinCounts;
    }

    public ParticipantTimelineData getGoldPerMinDeltas() {
        return goldPerMinDeltas;
    }

    public ParticipantTimelineData getInhibitorAssistsPerMinCounts() {
        return inhibitorAssistsPerMinCounts;
    }

    public ParticipantTimelineData getInhibitorKillsPerMinCounts() {
        return inhibitorKillsPerMinCounts;
    }

    public MatchLane getLane() {
        return lane;
    }

    public MatchRole getRole() {
        return role;
    }

    public ParticipantTimelineData getTowerAssistsPerMinCounts() {
        return towerAssistsPerMinCounts;
    }

    public ParticipantTimelineData getTowerKillsPerMinCounts() {
        return towerKillsPerMinCounts;
    }

    public ParticipantTimelineData getTowerKillsPerMinDeltas() {
        return towerKillsPerMinDeltas;
    }

    public ParticipantTimelineData getVilemawAssistsPerMinCounts() {
        return vilemawAssistsPerMinCounts;
    }

    public ParticipantTimelineData getVilemawKillsPerMinCounts() {
        return vilemawKillsPerMinCounts;
    }

    public ParticipantTimelineData getWardsPerMinDeltas() {
        return wardsPerMinDeltas;
    }

    public ParticipantTimelineData getXpDiffPerMinDeltas() {
        return xpDiffPerMinDeltas;
    }

    public ParticipantTimelineData getXpPerMinDeltas() {
        return xpPerMinDeltas;
    }

}
