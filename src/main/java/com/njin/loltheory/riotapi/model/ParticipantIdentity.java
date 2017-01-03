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
public class ParticipantIdentity implements Serializable {

    private int participantId;
    private Player player;

    public int getParticipantId() {
        return participantId;
    }

    public Player getPlayer() {
        return player;
    }

}
