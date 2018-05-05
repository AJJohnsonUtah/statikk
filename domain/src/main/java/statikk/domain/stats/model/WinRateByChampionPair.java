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
public class WinRateByChampionPair extends BaseWinRate {
        private final int championId1;
        private final int championId2;

    public WinRateByChampionPair(int championId1, int championId2, long playedCount, long winCount) {
        super(winCount, playedCount);
        this.championId1 = championId1;
        this.championId2 = championId2;
    }

    public int getChampionId1() {
        return championId1;
    }
    
    public int getChampionId2() {
        return championId2;
    }
}
