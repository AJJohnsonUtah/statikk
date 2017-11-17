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
public class ChampionWinRate extends BaseWinRate {
    private final long championId;
    
    public ChampionWinRate(int championId, long playedCount, long winCount) {
        this.playedCount = playedCount;
        this.winCount = winCount;
        this.championId = championId;
    }

    public long getChampionId() {
        return championId;
    }
    
}
