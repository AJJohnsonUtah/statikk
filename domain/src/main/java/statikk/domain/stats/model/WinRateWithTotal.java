/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.stats.model;

import java.util.List;

/**
 *
 * @author AJ
 */
public class WinRateWithTotal<T extends BaseWinRate> {

    private double totalPlayedCount;
    private double totalWinCount;
    private List<BaseWinRate> winRateData;

    public WinRateWithTotal(List<BaseWinRate> winRateData) {
        this.winRateData = winRateData;
        this.totalPlayedCount = 0;
        winRateData.stream().forEach(winRate -> this.totalPlayedCount += winRate.getPlayedCount());
        winRateData.stream().forEach(winRate -> this.totalWinCount += winRate.getWinCount());
    }
    
    public double getTotalPlayed() {
        return totalPlayedCount;
    }

    public double getTotalWinCount() {
        return totalWinCount;
    }
    
    public List<BaseWinRate> getWinRateData() {
        return this.winRateData;
    }
}
