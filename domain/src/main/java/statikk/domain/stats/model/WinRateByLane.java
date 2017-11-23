/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.stats.model;

import statikk.domain.entity.enums.Lane;

/**
 *
 * @author AJ
 */
public class WinRateByLane extends BaseWinRate {

    private final Lane lane;

    public WinRateByLane( Lane lane, long playedCount, long winCount) {
        this.playedCount = playedCount;
        this.winCount = winCount;
        this.lane = lane;
    }


    public Lane getLane() {
        return this.lane;
    }

}
