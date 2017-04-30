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
public class MetaDataDto implements Serializable {

    private boolean isRune;
    private String tier;
    private String type;

    public boolean isRune() {
        return isRune;
    }

    public String getTier() {
        return tier;
    }

    public String getType() {
        return type;
    }

}
