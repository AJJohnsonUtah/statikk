/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.webapi.model;

import java.io.Serializable;
import statikk.domain.entity.enums.Lane;

/**
 *
 * @author Grann
 */
public class TeamBuilderProgressData implements Serializable {

    private String summonerName;
    private Lane lane;

    public Lane getLane() {
        return lane;
    }

    public void setLane(Lane lane) {
        this.lane = lane;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }

}
