/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.riotapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

/**
 *
 * @author AJ
 */
public class GroupDto implements Serializable {

    String maxGroupOwnable;
    String key;

    @JsonProperty("MaxGroupOwnable")
    public String getMaxGroupOwnable() {
        return maxGroupOwnable;
    }

    public String getKey() {
        return key;
    }

}
