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
public class RecentGamesDto implements Serializable {

    private long summonerId;
    private List<GameDto> games;

    public long getSummonerId() {
        return summonerId;
    }

    public List<GameDto> getGames() {
        return games;
    }

}
