/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.riotapi.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author AJ
 */
public class Frame implements Serializable {

    private List<Event> events;
    private Map<String, ParticipantFrame> participantFrames;
    Long timestamp;

    public List<Event> getEvents() {
        return events;
    }

    public Map<String, ParticipantFrame> getParticipantFrames() {
        return participantFrames;
    }

    public Long getTimestamp() {
        return timestamp;
    }

}
