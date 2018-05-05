/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.webapi.model;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Grann
 */
public class SignificantTeamups {
    
    List<Integer> teamupWellWithChampions;
    List<Integer> teamupPoorWithChampions;

    public SignificantTeamups() {
        teamupWellWithChampions = new LinkedList<>();
        teamupPoorWithChampions = new LinkedList<>();
    }

    public List<Integer> getTeamupWellWithChampions() {
        return teamupWellWithChampions;
    }

    public List<Integer> getTeamupPoorWithChampions() {
        return teamupPoorWithChampions;
    }
    
}
