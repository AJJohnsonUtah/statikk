/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.webapi.model;

import java.util.Collection;

/**
 *
 * @author Grann
 */
public class ChampionSuggestionContext<T> {

    ChampionSuggestionReason reason;
    Double score;
    Collection<SupportingSuggestionInfo<T>> supportingInfo;

    public ChampionSuggestionContext(ChampionSuggestionReason reason) {
        this.reason = reason;
    }

    public ChampionSuggestionContext(ChampionSuggestionReason reason, Double score) {
        this.reason = reason;
        this.score = score;
    }

    public ChampionSuggestionContext(ChampionSuggestionReason reason, Double score, Collection<SupportingSuggestionInfo<T>> supportingInfo) {
        this.reason = reason;
        this.score = score;
        this.supportingInfo = supportingInfo;
    }

    public ChampionSuggestionContext(ChampionSuggestionReason reason, Collection<SupportingSuggestionInfo<T>> supportingInfo) {
        this.reason = reason;
        this.supportingInfo = supportingInfo;
    }

    public ChampionSuggestionReason getReason() {
        return reason;
    }

    public void setReason(ChampionSuggestionReason reason) {
        this.reason = reason;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Collection<SupportingSuggestionInfo<T>>  getSupportingInfo() {
        return supportingInfo;
    }

    public void setSupportingInfo(Collection<SupportingSuggestionInfo<T>> supportingInfo) {
        this.supportingInfo = supportingInfo;
    }

}
