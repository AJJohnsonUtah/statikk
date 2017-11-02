/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.webapi.controller;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import statikk.domain.riotapi.model.Region;
import statikk.domain.riotapi.service.RiotApiService;

/**
 *
 * @author AJ
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = SummonerDataController.class)
public class SummonerDataControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RiotApiService riotApiServiceMock;

    public SummonerDataControllerTest() {

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
     * Test of getChampionMastery method, of class SummonerDataController.
     */
    @Test
    public void testGetChampionMasteryByRegion() throws Exception {
        System.out.println("getChampionMastery");
        given(riotApiServiceMock.getChampionMastery(Region.EUNE, 27673684))
                .willReturn("Fake masteries...?");
        mockMvc.perform(get("/api/summoner/27673684/champion-mastery/all/EUNE")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    /**
     * Test of getChampionMastery method, of class SummonerDataController.
     */
    @Test
    public void testGetChampionMastery() throws Exception {
        System.out.println("getChampionMastery");
        given(riotApiServiceMock.getChampionMastery(Region.NA, 27673684))
                .willReturn("Fake masteries...?");
        mockMvc.perform(get("/api/summoner/27673684/champion-mastery/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    /**
     * Test of getCurrentGame method, of class SummonerDataController.
     */
    @Test
    public void testGetCurrentGameByRegion() throws Exception {
        System.out.println("getCurrentGame");
        given(riotApiServiceMock.getCurrentGame(Region.EUNE, 27673684))
                .willReturn("Fake game...?");
        mockMvc.perform(get("/api/summoner/27673684/current-game/EUNE")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    /**
     * Test of getCurrentGame method, of class SummonerDataController.
     */
    @Test
    public void testGetCurrentGame() throws Exception {
        System.out.println("getCurrentGame");
        given(riotApiServiceMock.getCurrentGame(Region.NA, 27673684))
                .willReturn("Fake game...?");
        mockMvc.perform(get("/api/summoner/27673684/current-game")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

}
