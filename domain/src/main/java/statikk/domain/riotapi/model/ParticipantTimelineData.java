/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.riotapi.model;

import java.io.Serializable;

/**
 *
 * @author AJ
 */
public class ParticipantTimelineData implements Serializable {

    private double zeroToTen;
    private double tenToTwenty;
    private double twentyToThirty;
    private double thirtyToEnd;

    public double getZeroToTen() {
        return zeroToTen;
    }

    public double getTenToTwenty() {
        return tenToTwenty;
    }

    public double getTwentyToThirty() {
        return twentyToThirty;
    }

    public double getThirtyToEnd() {
        return thirtyToEnd;
    }

}
