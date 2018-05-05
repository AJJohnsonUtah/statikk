/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.stats.model;

/**
 *
 * @author Grann
 */
public enum CorrelationInfo {
    PositiveEffect(1.0), NegativeEffect(-1.0), NeutralEffect(0.0);

    private final double score;

    CorrelationInfo(double score) {
        this.score = score;
    }

    public Double getScore() {
        return score;
    }

}
