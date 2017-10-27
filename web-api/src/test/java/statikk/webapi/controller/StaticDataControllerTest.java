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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import statikk.domain.riotapi.model.Region;
import statikk.domain.riotapi.service.RiotApiService;

/**
 *
 * @author AJ
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = StaticDataController.class, secure = false)
public class StaticDataControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RiotApiService riotApiService;

    public StaticDataControllerTest() {
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
     * Test of getChampions method, of class StaticDataController.
     */
    @Test
    public void testGetChampionsWithRegion() throws Exception {
        System.out.println("getChampions");

        given(this.riotApiService.getStaticChampionsData(Region.NA))
                .willReturn("Hello world!");
        this.mvc.perform(get("/api/static-data/champions/NA")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    /**
     * Test of getChampions method, of class StaticDataController.
     */
    @Test
    public void testGetChampions() throws Exception {
        System.out.println("getChampions");
        given(this.riotApiService.getStaticChampionsData(Region.NA))
                .willReturn("Hello world!");
        this.mvc.perform(get("/api/static-data/champions")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    /**
     * Test of getChampion method, of class StaticDataController.
     */
    @Test
    public void testGetChampionWithRegion() throws Exception {
        System.out.println("getChampion");
        given(this.riotApiService.getStaticChampionData(Region.NA, 1))
                .willReturn("Hello world!");
        this.mvc.perform(get("/api/static-data/champion/1/NA")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    /**
     * Test of getChampion method, of class StaticDataController.
     */
    @Test
    public void testGetChampion() throws Exception {
        System.out.println("getChampion");
        given(this.riotApiService.getStaticChampionData(Region.NA, 1))
                .willReturn("Hello world!");
        this.mvc.perform(get("/api/static-data/champion/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

}
