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
public class RuneDto implements Serializable {

    private long runeId;
    private long rank;

    public long getRuneId() {
        return runeId;
    }

    public long getRank() {
        return rank;
    }

}
