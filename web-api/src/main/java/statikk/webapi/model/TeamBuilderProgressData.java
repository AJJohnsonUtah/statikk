/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.webapi.model;

import java.io.Serializable;
import java.util.List;
import statikk.domain.entity.enums.Lane;

/**
 *
 * @author Grann
 */
public class TeamBuilderProgressData implements Serializable {

    private String summonerName;
    private Lane lane;
    private List<Integer> allyChampionIds;
    private List<Integer> enemyChampionIds;

    public Lane getLane() {
        return lane;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public List<Integer> getAllyChampionIds() {
        return allyChampionIds;
    }

    public List<Integer> getEnemyChampionIds() {
        return enemyChampionIds;
    }

}
