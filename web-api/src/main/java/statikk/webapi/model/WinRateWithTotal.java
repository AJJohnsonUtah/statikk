/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.webapi.model;

import java.util.List;
import statikk.domain.stats.model.BaseWinRate;

/**
 *
 * @author AJ
 */
public class WinRateWithTotal<T extends BaseWinRate> {

    private long totalPlayed;
    private List<BaseWinRate> winRateData;

    public WinRateWithTotal(List<BaseWinRate> winRateData) {
        this.winRateData = winRateData;
        this.totalPlayed = 0;
        winRateData.stream().forEach(winRate -> this.totalPlayed += winRate.getPlayedCount());
    }
    
    public long getTotalPlayed() {
        return totalPlayed;
    }
    
    public List<BaseWinRate> getWinRateData() {
        return this.winRateData;
    }
}
