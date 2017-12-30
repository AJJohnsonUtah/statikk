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

    private long totalWinCount;
    private long totalPlayedCount;

    private final Map<K, V> winRateData;

    public WinRateMapWithTotal(Map<K, V> winRateData) {
        this.winRateData = winRateData;
        this.totalPlayedCount = 0;
        winRateData.values().stream().forEach(winRate -> {
            this.totalPlayedCount += winRate.getPlayedCount();
            this.totalWinCount += winRate.getWinCount();
        });
    }

    public long getTotalPlayedCount() {
        return totalPlayedCount;
    }

    public Map<K, V> getWinRateData() {
        return this.winRateData;
    }

    public long getTotalWinCount() {
        return totalWinCount;
    }

    public double getTotalWinRate() {
        return this.totalWinCount * 1.0 / this.totalPlayedCount;
    }

    public boolean isSignificantlyHigherWinRate(K keyToCheck) {
        double zScore95Percentile = 1.645;

        if (!this.winRateData.containsKey(keyToCheck) || this.winRateData.size() <= 1) {
            return false;
        }

        if (getZScore(keyToCheck) >= zScore95Percentile) {
            return true;
        }
        return false;
    }

    public boolean isSignificantlyLowerWinRate(K keyToCheck) {
        double zScore5Percentile = -1.645;

        if (!this.winRateData.containsKey(keyToCheck) || this.winRateData.size() <= 1) {
            return false;
        }

        if (getZScore(keyToCheck) <= zScore5Percentile) {
            return true;
        }
        return false;
    }

    /**
     * Determines a Z-Score for the specific key's win rate compared to the overall win rate
     * 
     * Test from: https://onlinecourses.science.psu.edu/stat414/node/268
     * 
     * @param keyToCheck
     * @return 
     */
    public double getZScore(K keyToCheck) {
        V winRateDataForKey = this.winRateData.get(keyToCheck);

        long n1 = this.totalPlayedCount - winRateDataForKey.playedCount;
        long n2 = winRateDataForKey.playedCount;

        double p1 = (this.totalWinCount - winRateDataForKey.winCount) / n1;
        double p2 = winRateDataForKey.getWinRate();
        double p = this.getTotalWinRate();

        return (p1 - p2) / Math.sqrt(p * (1 - p) * (1 / n1 + 1 / n2));
    }
}
