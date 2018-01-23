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
public enum SupportingSuggestionReason {
    PositiveEffect(1.0), NegativeEffect(-1.0), NeutralEffect(0.0);

    private final double score;

    SupportingSuggestionReason(double score) {
        this.score = score;
    }

    public Double getScore() {
        return score;
    }

}
