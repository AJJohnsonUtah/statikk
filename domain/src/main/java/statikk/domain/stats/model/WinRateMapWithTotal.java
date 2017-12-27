/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.stats.model;

import java.util.Map;

/**
 *
 * @author AJ
 */
public class WinRateMapWithTotal<K, V extends BaseWinRate> {

    private long totalPlayed;
    private final Map<K, V> winRateData;

    public WinRateMapWithTotal(Map<K, V> winRateData) {
        this.winRateData = winRateData;
        this.totalPlayed = 0;
        winRateData.values().stream().forEach(winRate -> this.totalPlayed += winRate.getPlayedCount());
    }

    public long getTotalPlayed() {
        return totalPlayed;
    }

    public Map<?, V> getWinRateData() {
        return this.winRateData;
    }

}
