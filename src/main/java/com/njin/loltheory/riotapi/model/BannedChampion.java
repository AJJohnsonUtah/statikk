/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.riotapi.model;

import com.njin.loltheory.entity.ChampSpec;
import java.io.Serializable;

/**
 *
 * @author AJ
 */
public class BannedChampion implements Serializable {

    private int championId;
    private int pickTurn;
    private long teamId;
    private ChampSpec champSpec;

    public int getChampionId() {
        return championId;
    }

    public int getPickTurn() {
        return pickTurn;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setChampSpec(ChampSpec champSpec) {
        this.champSpec = champSpec;
    }

    public ChampSpec getChampSpec() {
        return champSpec;
    }

}
