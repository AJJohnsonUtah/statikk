/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.webapi.model;

import java.util.List;
import java.util.Map;
import statikk.domain.stats.model.BaseWinRate;

/**
 *
 * @author AJ
 */
public class WinRateMapWithTotal<T extends BaseWinRate> {

    private long totalPlayed;
    private Map<?, BaseWinRate> winRateData;

    public WinRateMapWithTotal(Map<?, BaseWinRate> winRateData) {
        this.winRateData = winRateData;
        this.totalPlayed = 0;
        winRateData.values().stream().forEach(winRate -> this.totalPlayed += winRate.getPlayedCount());
    }

    public long getTotalPlayed() {
        return totalPlayed;
    }

    public Map<?, BaseWinRate> getWinRateData() {
        return this.winRateData;
    }

}
