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
public class ChampionSuggestion {

    private long championId;
    private List<ChampionSuggestionContext> suggestionContext;

    public ChampionSuggestion(long championId) {
        this.championId = championId;
        this.suggestionContext = new LinkedList<>();
    }

    public ChampionSuggestion(long championId, ChampionSuggestionContext suggestionContext) {
        this(championId);
        this.suggestionContext.add(suggestionContext);
    }

    public long getChampionId() {
        return championId;
    }

    public void setChampionId(long championId) {
        this.championId = championId;
    }

    public List<ChampionSuggestionContext> getSuggestionContext() {
        return suggestionContext;
    }

    public double getScore() {
        return suggestionContext.stream().map(context -> context.score).reduce(0.0, (x, y) -> x + y) / suggestionContext.size();
    }

    public void addSuggestionContext(ChampionSuggestionContext suggestionContext) {
        this.suggestionContext.add(suggestionContext);
    }

}
