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
public class ChampionWinRate {
    private final Double winRate;
    private final double playedCount;
    private final double winCount;
    private final long championId;
    
    
    public ChampionWinRate(int championId, long playedCount, long winCount) {
        this.playedCount = playedCount;
        this.winCount = winCount;
        this.championId = championId;
        if(this.winCount == 0) {
            winRate = null;
        } else {
            winRate = ((double)winCount) / playedCount;
        }
    }

    public Double getWinRate() {
        return winRate;
    }

    public double getPlayedCount() {
        return playedCount;
    }

    public double getWinCount() {
        return winCount;
    }

    public long getChampionId() {
        return championId;
    }
    
}
