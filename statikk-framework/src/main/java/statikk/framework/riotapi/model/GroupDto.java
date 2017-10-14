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
public class GroupDto implements Serializable {

    String MaxGroupOwnable;
    String key;

    public String getMaxGroupOwnable() {
        return MaxGroupOwnable;
    }

    public String getKey() {
        return key;
    }

}
