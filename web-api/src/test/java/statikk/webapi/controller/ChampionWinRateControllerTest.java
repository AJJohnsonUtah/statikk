/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.webapi.controller;

import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import statikk.domain.stats.model.ChampionWinRate;
import statikk.domain.stats.service.ChampionWinRateService;

/**
 *
 * @author AJ
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ChampionWinRateController.class, secure = false)
public class ChampionWinRateControllerTest {

    @Autowired
    private ChampionWinRateController championWinRateController;

    @MockBean
    private ChampionWinRateService mockService;

    private List<ChampionWinRate> mockWinRates;

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
        this.mockWinRates = new ArrayList<>();
        mockWinRates.add(new ChampionWinRate(1, 100, 45));
        mockWinRates.add(new ChampionWinRate(2, 200, 155));
    }

    @After
    public void tearDown() {
    }

    @Test
    public void contextLoads() {
        assertThat(championWinRateController).isNotNull();
    }

    /**
     * Test of getAllChampionWinRates method, of class
     * ChampionWinRateController.
     */
    @Test
    public void testGetAllChampionWinRates() {
        System.out.println("getAllChampionWinRates");
        given(this.mockService.getChampionWinRates())
                .willReturn(mockWinRates);
        Assert.assertEquals("getAllChampionWinRates correctly returns total played count", 300, championWinRateController.getAllChampionWinRates().getTotalPlayed());
    }

}
