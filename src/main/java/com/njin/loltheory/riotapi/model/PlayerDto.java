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
public class PlayerDto implements Serializable {

    private int championId;
    private long summonerId;
    private int teamId;

    public int getChampionId() {
        return championId;
    }

    public long getSummonerId() {
        return summonerId;
    }

    public int getTeamId() {
        return teamId;
    }

}