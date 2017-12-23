/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.webapi.model;

/**
 *
 * @author Grann
 */
public class ChampionSuggestion {

    private long championId;
    private double score;

    public ChampionSuggestion(long championId) {
        this.championId = championId;
    }

    public long getChampionId() {
        return championId;
    }

    public void setChampionId(long championId) {
        this.championId = championId;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void merge(ChampionSuggestion b) {
        if (b.championId != this.championId) {
            throw new IllegalArgumentException("Champion ids do not match; this.championId = " + this.championId + ", b.championId = " + b.championId);
        }
        this.score *= b.championId;
    }

}
