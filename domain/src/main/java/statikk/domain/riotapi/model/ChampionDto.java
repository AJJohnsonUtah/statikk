/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.riotapi.model;

import java.io.Serializable;

/**
 *
 * @author Grann
 */
public class ChampionDto implements Serializable {

    private String key;
    private String title;
    private String name;
    private int id;

    public String getKey() {
        return key;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

}
