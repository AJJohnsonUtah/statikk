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
public class SignificantMatchups {

    List<Integer> counterToChampions;
    List<Integer> counteredByChampions;

    public SignificantMatchups() {
        counterToChampions = new LinkedList<>();
        counteredByChampions = new LinkedList<>();
    }

    public List<Integer> getCounterToChampions() {
        return counterToChampions;
    }

    public List<Integer> getCounteredByChampions() {
        return counteredByChampions;
    }
    
}
