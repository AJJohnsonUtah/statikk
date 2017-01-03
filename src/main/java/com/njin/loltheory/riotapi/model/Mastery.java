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
public class Mastery implements Serializable {

    private long masteryId;
    private long rank;

    public long getMasteryId() {
        return masteryId;
    }

    public long getRank() {
        return rank;
    }

}
