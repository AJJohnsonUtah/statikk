/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.stats.model;

/**
 *
 * @author AJ
 */
public class WinRateByChampion extends BaseWinRate {

    private final int championId;

    public WinRateByChampion(int championId, long playedCount, long winCount) {
        this.playedCount = playedCount;
        this.winCount = winCount;
        this.championId = championId;
    }

    public int getChampionId() {
        return championId;
    }

}
