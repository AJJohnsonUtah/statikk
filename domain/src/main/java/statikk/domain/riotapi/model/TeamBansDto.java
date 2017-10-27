/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.riotapi.model;

import statikk.domain.entity.ChampSpec;
import java.io.Serializable;

/**
 *
 * @author AJ
 */
public class TeamBansDto implements Serializable {

    private int championId;
    private int pickTurn;
    private ChampSpec champSpec;

    public int getChampionId() {
        return championId;
    }

    public int getPickTurn() {
        return pickTurn;
    }

    public void setChampSpec(ChampSpec champSpec) {
        this.champSpec = champSpec;
    }

    public ChampSpec getChampSpec() {
        return champSpec;
    }

}
