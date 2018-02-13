/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.stats.model;

import java.util.Map;
import statikk.domain.stats.service.StatisticsUtil;

/**
 *
 * @author AJ
 */
public class WinRateMapWithTotal<K, V extends BaseWinRate> {

    private double totalWinCount;
    private double totalPlayedCount;

    private final Map<K, V> winRateData;

    public WinRateMapWithTotal(Map<K, V> winRateData) {
        this.winRateData = winRateData;
        this.totalPlayedCount = 0;
        winRateData.values().stream().forEach(winRate -> {
            this.totalPlayedCount += winRate.getPlayedCount();
            this.totalWinCount += winRate.getWinCount();
        });
    }

    public double getTotalPlayedCount() {
        return totalPlayedCount;
    }

    public Map<K, V> getWinRateData() {
        return this.winRateData;
    }

    public double getTotalWinCount() {
        return totalWinCount;
    }

    public boolean hasDataFor(K key) {
        return winRateData.containsKey(key) && winRateData.get(key).getPlayedCount() > 0;
    }

    public double getTotalWinRate() {
        return this.totalWinCount / this.totalPlayedCount;
    }

    public boolean isSignificantlyHigherWinRate(K keyToCheck) {
        if (!this.winRateData.containsKey(keyToCheck) || this.winRateData.size() <= 1) {
            return false;
        }
        try {
            if (getZScore(keyToCheck) >= StatisticsUtil.Z_SCORE_95_PERCENTILE) {
                return true;
            }
        } catch (NullPointerException e) {
            return false;
        }
        return false;
    }

    public boolean isSignificantlyLowerWinRate(K keyToCheck) {
        if (!this.winRateData.containsKey(keyToCheck) || this.winRateData.size() <= 1) {
            return false;
        }
        try {
            if (getZScore(keyToCheck) <= StatisticsUtil.Z_SCORE_5_PERCENTILE) {
                return true;
            }
        } catch (NullPointerException e) {
            return false;
        }
        return false;
    }

    /**
     * Determines a Z-Score for the specific key's win rate compared to the
     * overall win rate
     *
     * Test from: https://onlinecourses.science.psu.edu/stat414/node/268
     *
     * @param keyToCheck
     * @return
     */
    public double getZScore(K keyToCheck) {
        V winRateDataForKey = this.winRateData.get(keyToCheck);

        double p1 = winRateDataForKey.getWinRate();
        double n1 = winRateDataForKey.playedCount;

        double p2 = (this.totalWinCount - winRateDataForKey.winCount) / (this.totalPlayedCount - winRateDataForKey.playedCount);
        double n2 = this.totalPlayedCount - winRateDataForKey.playedCount;

        return StatisticsUtil.getZScore(p1, n1, p2, n2);
    }
}
