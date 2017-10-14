/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.framework.riotapi.model;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author AJ
 */
public class Summoners implements Serializable {

    private HashMap<String, SummonerDto> summoners;

    public HashMap<String, SummonerDto> getSummoners() {
        return summoners;
    }

}
