/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.riotapi.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author AJ
 */
public class MatchListDto implements Serializable {

    private int totalGames;
    private int startIndex;
    private int endIndex;
    private final List<MatchReferenceDto> matches;

    public MatchListDto(){
        matches = Collections.EMPTY_LIST;
    };   

    public int getTotalGames() {
        return totalGames;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public List<MatchReferenceDto> getMatches() {
        return matches;
    }        
}
