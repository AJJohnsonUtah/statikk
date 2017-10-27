/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.webapi.controller;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author AJ
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ChampionWinRateControllerTest {           
    
    @Autowired
    private ChampionWinRateController championWinRateController;    
    
    public ChampionWinRateControllerTest() {
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

    @Test
    public void contextLoads() {
        assertThat(championWinRateController).isNotNull();
    }
    
    /**
     * Test of getAllChampionWinRates method, of class ChampionWinRateController.
     */
    @Test
    public void testGetAllChampionWinRates() {
        System.out.println("getAllChampionWinRates");
        Assert.assertTrue("No results found for getAllChampionWinRates()", championWinRateController.getAllChampionWinRates().containsKey(1L));
    }
    
    
}
