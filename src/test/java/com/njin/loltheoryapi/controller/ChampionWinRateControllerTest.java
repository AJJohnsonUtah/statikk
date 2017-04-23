/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheoryapi.controller;

import com.njin.loltheoryapi.config.TestConfig;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 *
 * @author AJ
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class, loader = AnnotationConfigWebContextLoader.class)
@ActiveProfiles("test")
public class ChampionWinRateControllerTest {        
    
    @Autowired
    ChampionWinRateController championWinRateController;
    
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

    /**
     * Test of getAllChampionWinRates method, of class ChampionWinRateController.
     */
    @Test
    public void testGetAllChampionWinRates() {
        System.out.println("getAllChampionWinRates");
        Assert.assertTrue("No results found for getAllChampionWinRates()", championWinRateController.getAllChampionWinRates().size() > 0);
    }
    
}
