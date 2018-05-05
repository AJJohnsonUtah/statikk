/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.webapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import statikk.domain.entity.enums.Role;

/**
 *
 * @author Grann
 */
public class TeamBuilderSuggestion {

    private Collection<Role> suggestedRoles;
    private Collection<Role> discouragedRoles;

    private Map<Integer, ChampionSuggestion> championSuggestions;

    public TeamBuilderSuggestion() {
        suggestedRoles = Collections.EMPTY_LIST;
        discouragedRoles = Collections.EMPTY_LIST;
        championSuggestions = new HashMap<>();
    }

    
    
    public Collection<Role> getSuggestedRoles() {
        return suggestedRoles;
    }

    public void setSuggestedRoles(Collection<Role> suggestedRoles) {
        this.suggestedRoles = suggestedRoles;
    }

    public Collection<Role> getDiscouragedRoles() {
        return discouragedRoles;
    }

    public void setDiscouragedRoles(Collection<Role> discouragedRoles) {
        this.discouragedRoles = discouragedRoles;
    }

    @JsonIgnore
    public Map<Integer, ChampionSuggestion> getChampionSuggestionsMap() {
        return championSuggestions;
    }
    
    public Collection<ChampionSuggestion> getChampionSuggestions() {
        return championSuggestions.values();
    }

}
