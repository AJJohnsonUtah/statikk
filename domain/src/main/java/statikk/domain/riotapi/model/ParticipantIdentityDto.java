/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.riotapi.model;

import java.io.Serializable;

/**
 *
 * @author AJ
 */
public class ParticipantIdentityDto implements Serializable {

    private int participantId;
    private PlayerDto player;

    public int getParticipantId() {
        return participantId;
    }

    public PlayerDto getPlayer() {
        return player;
    }

}
