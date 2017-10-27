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
public class MasteryDto implements Serializable {

    private long masteryId;
    private long rank;

    public long getMasteryId() {
        return masteryId;
    }

    public long getRank() {
        return rank;
    }

}
