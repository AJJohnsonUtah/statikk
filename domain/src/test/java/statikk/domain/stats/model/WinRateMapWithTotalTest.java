/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.stats.model;

import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Grann
 */
public class WinRateMapWithTotalTest {

    public WinRateMapWithTotalTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getTotalPlayedCount method, of class WinRateMapWithTotal.
     */
    @Test
    public void testGetTotalPlayedCount() {
        System.out.println("getTotalPlayedCount");
        Map<Integer, WinRateByChampion> winRates = new HashMap<>();
        winRates.put(1, new WinRateByChampion(1, 20, 10));
        winRates.put(2, new WinRateByChampion(2, 30, 5));

        WinRateMapWithTotal instance = new WinRateMapWithTotal(winRates);
        double expResult = 50L;
        double result = instance.getTotalPlayedCount();
        assertEquals("Played count should equal summed playedCounts", expResult, result, 0.0);
    }

    /**
     * Test of getWinRateData method, of class WinRateMapWithTotal.
     */
    @Test
    public void testGetWinRateData() {
        System.out.println("getWinRateData");
        Map<Integer, WinRateByChampion> winRates = new HashMap<>();
        winRates.put(1, new WinRateByChampion(1, 20, 10));
        winRates.put(2, new WinRateByChampion(2, 30, 5));

        WinRateMapWithTotal<Integer, WinRateByChampion> instance = new WinRateMapWithTotal(winRates);

        assertEquals(winRates, instance.getWinRateData());
    }

    /**
     * Test of getTotalWinCount method, of class WinRateMapWithTotal.
     */
    @Test
    public void testGetTotalWinCount() {
        System.out.println("getTotalWinCount");
        Map<Integer, WinRateByChampion> winRates = new HashMap<>();
        winRates.put(1, new WinRateByChampion(1, 20, 10));
        winRates.put(2, new WinRateByChampion(2, 30, 5));

        WinRateMapWithTotal instance = new WinRateMapWithTotal(winRates);
        double expResult = 15;
        double result = instance.getTotalWinCount();
        assertEquals("Total win count should be summed win counts", expResult, result, 0.0);
    }

    /**
     * Test of getTotalWinRate method, of class WinRateMapWithTotal.
     */
    @Test
    public void testGetTotalWinRate() {
        System.out.println("getTotalWinRate");
        Map<Integer, WinRateByChampion> winRates = new HashMap<>();
        winRates.put(1, new WinRateByChampion(1, 20, 10));
        winRates.put(2, new WinRateByChampion(2, 30, 5));

        WinRateMapWithTotal instance = new WinRateMapWithTotal(winRates);
        double expResult = 15.0 / 50.0;
        double result = instance.getTotalWinRate();
        assertEquals("Win rate should equal summed win count divided by summed played count", expResult, result, 0.0);
    }

    /**
     * Test of isSignificantlyHigherWinRate method, of class
     * WinRateMapWithTotal.
     */
    @Test
    public void testIsSignificantlyHigherWinRate() {
        System.out.println("isSignificantlyHigherWinRate");
        Map<Integer, WinRateByChampion> winRates = new HashMap<>();
        winRates.put(1, new WinRateByChampion(1, 20, 10));
        winRates.put(2, new WinRateByChampion(2, 30, 5));
        WinRateMapWithTotal instance = new WinRateMapWithTotal(winRates);

        assertTrue("This champion with a higher win rate should be identified as significantly higher", instance.isSignificantlyHigherWinRate(1));
        assertFalse("This champion with a lower win rate should not be identified as significantly higher", instance.isSignificantlyHigherWinRate(2));

    }

    /**
     * Test of isSignificantlyLowerWinRate method, of class WinRateMapWithTotal.
     */
    @Test
    public void testIsSignificantlyLowerWinRate() {
        System.out.println("isSignificantlyLowerWinRate");
        Map<Integer, WinRateByChampion> winRates = new HashMap<>();
        winRates.put(1, new WinRateByChampion(1, 20, 10));
        winRates.put(2, new WinRateByChampion(2, 30, 5));
        WinRateMapWithTotal instance = new WinRateMapWithTotal(winRates);

        assertTrue("This champion with a lower win rate should be identified as significantly lower", instance.isSignificantlyLowerWinRate(2));
        assertFalse("This champion with a higher win rate should not be identified as significantly lower", instance.isSignificantlyLowerWinRate(1));
    }

    /**
     * Test of getZScore method, of class WinRateMapWithTotal.
     */
    @Test
    public void testGetZScore() {
        System.out.println("getZScore");
        Map<Integer, WinRateByChampion> winRates = new HashMap<>();
        winRates.put(1, new WinRateByChampion(1, 20, 10));
        winRates.put(2, new WinRateByChampion(2, 30, 5));
        WinRateMapWithTotal instance = new WinRateMapWithTotal(winRates);
        
        assertEquals("The Z score for champion 1 should be higher than 1.645 (~2.5198)", 2.5198, instance.getZScore(1), 0.001);
        assertEquals("The Z score for champion 1 should be lower than -1.645 (~-2.5198)", -2.5198, instance.getZScore(2), 0.001);
    }

}
