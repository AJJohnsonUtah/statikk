/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.stats.model;

import statikk.domain.entity.enums.Lane;

/**
 *
 * @author Grann
 */
public class WinRateByChampionLane extends BaseWinRate {

    private int championId;
    private Lane lane;

    public WinRateByChampionLane(int championId, Lane lane, long playedCount, long winCount) {
        super(winCount, playedCount);
        this.championId = championId;
        this.lane = lane;
    }

    public int getChampionId() {
        return championId;
    }

    public void setChampionId(int championId) {
        this.championId = championId;
    }

    public Lane getLane() {
        return lane;
    }

    public void setLane(Lane lane) {
        this.lane = lane;
    }

}
