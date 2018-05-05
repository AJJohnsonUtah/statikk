/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.stats.service;

/**
 *
 * @author Grann
 */
public class StatisticsUtil {

    public static double Z_SCORE_5_PERCENTILE = -1.645;
    public static double Z_SCORE_95_PERCENTILE = 1.645;

    /**
     * Determines a Z-Score for the specific key's win rate compared to the
     * overall win rate
     *
     * Test from: https://onlinecourses.science.psu.edu/stat414/node/268
     *
     * @param p1 - Win proportion of group under test
     * @param n1 - Sample size count of group under test
     * @param p2 - Win proportion of population, excluding group under test
     * @param n2 - Sample size of population, excluding group under test
     * @return
     */
    public static double getZScore(double p1, double n1, double p2, double n2) {
        double p = (p1 * n1 + p2 * n2) / (n1 + n2);
        return (p1 - p2) / Math.sqrt(p * (1 - p) * (1 / n1 + 1 / n2));
    }

    public static boolean isSignificantlyHigherProportion(double p1, double n1, double p2, double n2) {
        return getZScore(p1, n1, p2, n2) >= Z_SCORE_95_PERCENTILE;
    }

    public static boolean isSignificantlyLowerProportion(double p1, double n1, double p2, double n2) {
        return getZScore(p1, n1, p2, n2) <= Z_SCORE_5_PERCENTILE;
    }
}
