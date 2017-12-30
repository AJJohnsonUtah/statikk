/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.webapi.model;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Grann
 */
public class ChampionSuggestion {

    private long championId;
    private Map<ChampionSuggestionReason, Double> scores;

    public ChampionSuggestion(long championId) {
        this.championId = championId;
        this.scores = new HashMap<>();
    }

    public ChampionSuggestion(long championId, ChampionSuggestionReason reason, double score) {
        this(championId);
        this.scores.put(reason, score);
    }

    public long getChampionId() {
        return championId;
    }

    public void setChampionId(long championId) {
        this.championId = championId;
    }

    public Map<ChampionSuggestionReason, Double> getScores() {
        return scores;
    }

    public double getScore() {
        return scores.values().stream().reduce(0.0, (x, y) -> x + y) / scores.size();
    }

    public void addScore(ChampionSuggestionReason reason, double score) {
        this.scores.put(reason, score);
    }

}
