/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.riotapi.model;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author Grann
 */
public class ChampionListDto implements Serializable {

    private Map<String, String> keys;
    private Map<Integer, ChampionDto> data;
    private String version;
    private String type;
    private String format;

    public Map<String, String> getKeys() {
        return keys;
    }

    public Map<Integer, ChampionDto> getData() {
        return data;
    }

    public String getVersion() {
        return version;
    }

    public String getType() {
        return type;
    }

    public String getFormat() {
        return format;
    }

}
