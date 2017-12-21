/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.service;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import statikk.domain.entity.ChampSpec;
import statikk.domain.entity.ChampSpecWinRate;
import statikk.domain.entity.ChampSpecWinRatePK;
import statikk.domain.entity.LolVersion;
import statikk.domain.entity.enums.Lane;
import statikk.domain.riotapi.model.QueueType;
import statikk.domain.riotapi.model.Rank;
import statikk.domain.riotapi.model.Region;

/**
 *
 * @author Grann
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@AutoConfigureTestDatabase
public class ChampSpecWinRateServiceTest {

    @Autowired
    ChampSpecWinRateService champSpecWinRateService;

    @Autowired
    LolVersionService lolVersionService;

    @Autowired
    ChampSpecService champSpecService;

    LolVersion lolVersion;
    ChampSpec champSpec, champSpec2;

    public ChampSpecWinRateServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        lolVersion = lolVersionService.findOrCreate(new LolVersion("1.23"));
        champSpec = new ChampSpec();
        champSpec.setChampionId(1);
        champSpec.setLolVersion(lolVersion);
        champSpec.setLane(Lane.TOP);
        champSpec.setRank(Rank.GOLD);
        champSpec.setMatchType(QueueType.CUSTOM);
        champSpec.setRegion(Region.EUNE);
        champSpec = champSpecService.findOrCreate(champSpec);

        champSpec2 = new ChampSpec();
        champSpec2.setChampionId(2);
        champSpec2.setLolVersion(lolVersion);
        champSpec2.setLane(Lane.TOP);
        champSpec2.setRank(Rank.GOLD);
        champSpec2.setMatchType(QueueType.CUSTOM);
        champSpec2.setRegion(Region.EUNE);
        champSpec2 = champSpecService.findOrCreate(champSpec2);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of find method, of class ChampSpecWinRateService.
     */
    @Test
    public void testFind() {
        System.out.println("find");
        ChampSpecWinRate champSpecWinRate = new ChampSpecWinRate();
        ChampSpecWinRatePK pk = new ChampSpecWinRatePK();
        pk.setChampSpec(champSpec);
        champSpecWinRate.setChampSpecWinRatePK(pk);
        champSpecWinRate.setPlayedCount(10);
        champSpecWinRate.setWinCount(3);
        List<ChampSpecWinRate> winRates = new ArrayList<>();
        winRates.add(champSpecWinRate);
        champSpecWinRateService.batchInsertOrUpdate(winRates);
        ChampSpecWinRate result = champSpecWinRateService.find(champSpecWinRate);
        assertEquals(champSpecWinRate, result);
        assertEquals(champSpecWinRate.getPlayedCount(), result.getPlayedCount());
        assertEquals(champSpecWinRate.getWinCount(), result.getWinCount());
    }

    @Test
    public void testBatchInsertOrUpdate() {
        System.out.println("batchInsertOrUpdate");
        ChampSpecWinRate champSpecWinRate = new ChampSpecWinRate();
        ChampSpecWinRatePK pk = new ChampSpecWinRatePK();
        pk.setChampSpec(champSpec);
        champSpecWinRate.setChampSpecWinRatePK(pk);
        champSpecWinRate.setPlayedCount(10);
        champSpecWinRate.setWinCount(3);
        List<ChampSpecWinRate> winRates = new ArrayList<>();
        winRates.add(champSpecWinRate);
        ChampSpecWinRate champSpecWinRate2 = new ChampSpecWinRate();
        ChampSpecWinRatePK pk2 = new ChampSpecWinRatePK();
        pk2.setChampSpec(champSpec2);
        champSpecWinRate2.setChampSpecWinRatePK(pk2);
        champSpecWinRate2.setPlayedCount(11);
        champSpecWinRate2.setWinCount(4);
        winRates.add(champSpecWinRate2);
        champSpecWinRateService.batchInsertOrUpdate(winRates);
        ChampSpecWinRate result = champSpecWinRateService.find(champSpecWinRate);
        assertEquals(champSpecWinRate, result);
        assertEquals(champSpecWinRate.getPlayedCount(), result.getPlayedCount());
        assertEquals(champSpecWinRate.getWinCount(), result.getWinCount());
        result = champSpecWinRateService.find(champSpecWinRate2);
        assertEquals(champSpecWinRate2, result);
        assertEquals(champSpecWinRate2.getPlayedCount(), result.getPlayedCount());
        assertEquals(champSpecWinRate2.getWinCount(), result.getWinCount());
        winRates.clear();
        ChampSpecWinRate champSpecWinRate3 = new ChampSpecWinRate();
        ChampSpecWinRatePK pk3 = new ChampSpecWinRatePK();
        pk3.setChampSpec(champSpec);
        champSpecWinRate3.setChampSpecWinRatePK(pk3);
        champSpecWinRate3.setPlayedCount(15);
        champSpecWinRate3.setWinCount(5);
        winRates.add(champSpecWinRate3);
        champSpecWinRateService.batchInsertOrUpdate(winRates);
        result = champSpecWinRateService.find(champSpecWinRate3);
        assertEquals(champSpecWinRate3, result);
        assertEquals(champSpecWinRate3.getPlayedCount() + champSpecWinRate.getPlayedCount(), result.getPlayedCount());
        assertEquals(champSpecWinRate3.getWinCount() + champSpecWinRate.getWinCount(), result.getWinCount());
    }

}
