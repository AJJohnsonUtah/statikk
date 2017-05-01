/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.framework.riotapi.model;

import java.io.Serializable;

/**
 *
 * @author AJ
 */
public class ParticipantTimeline implements Serializable {

    private int participantId;
    private ParticipantTimelineData creepsPerMinDeltas;
    private ParticipantTimelineData csDiffPerMinDeltas;
    private ParticipantTimelineData damageTakenDiffPerMinDeltas;
    private ParticipantTimelineData damageTakenPerMinDeltas;
    private ParticipantTimelineData goldPerMinDeltas;
    private MatchLane lane;
    private MatchRole role;
    private ParticipantTimelineData xpDiffPerMinDeltas;
    private ParticipantTimelineData xpPerMinDeltas;

    public int getParticipantId() {
        return participantId;
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

    public ParticipantTimelineData getGoldPerMinDeltas() {
        return goldPerMinDeltas;
    }

    public MatchLane getLane() {
        return lane;
    }

    public MatchRole getRole() {
        return role;
    }

    public ParticipantTimelineData getXpDiffPerMinDeltas() {
        return xpDiffPerMinDeltas;
    }

    public ParticipantTimelineData getXpPerMinDeltas() {
        return xpPerMinDeltas;
    }

}
