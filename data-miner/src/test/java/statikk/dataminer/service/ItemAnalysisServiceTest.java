/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.dataminer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import statikk.domain.entity.enums.Role;
import statikk.domain.riotapi.model.ItemListDto;
import statikk.domain.riotapi.model.ItemStat;
import statikk.domain.riotapi.model.MatchDetail;
import statikk.domain.riotapi.model.Region;
import statikk.domain.riotapi.service.RiotApiService;
import statikk.domain.service.FinalBuildOrderService;

/**
 *
 * @author Grann
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItemAnalysisService.class)
public class ItemAnalysisServiceTest {

    @Autowired
    ItemAnalysisService itemAnalysisService;

    @MockBean
    RiotApiService riotApiService;

    @MockBean
    FinalBuildOrderService finalBuildOrderService;

    private static ItemListDto mockItems;

    public ItemAnalysisServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mockItems = mapper.readValue(new File("src/test/resources/mock-data/item-data.json"), ItemListDto.class);
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        given(riotApiService.getItemsData(Region.NA)).willReturn(mockItems);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of loadItems method, of class ItemAnalysisService.
     */
    @Test
    public void testLoadItems() {
        System.out.println("loadItems");
        itemAnalysisService.loadItems();
        // Test will pass if item data is returned
    }

    /**
     * Test of isFinalItem method, of class ItemAnalysisService.
     */
    @Test
    public void testIsFinalItem() {
        System.out.println("isFinalItem");
        itemAnalysisService.loadItems();
        Integer finalItemId = 3031; // Infinity Edge
        Integer nonFinalItemId = 1036; // Long Sword
        assertTrue("Item should be final item", itemAnalysisService.isFinalItem(finalItemId));
        assertFalse("Item should not be final item", itemAnalysisService.isFinalItem(nonFinalItemId));
    }

    /**
     * Test of toBaseFinalItem method, of class ItemAnalysisService.
     */
    @Test
    public void testToBaseFinalItem() {
        System.out.println("toBaseFinalItem");
        itemAnalysisService.loadItems();
        Integer superFinalItemId = 3040; // Seraph's Embrace
        Integer finalItemId = 3003; // Archangel's Staff
        assertEquals("A 'transformed' final item should reduce to its base final item id", finalItemId, itemAnalysisService.toBaseFinalItem(superFinalItemId));
    }

    /**
     * Test of loadParticipantRoles method, of class ItemAnalysisService.
     */
    @Test
    public void testLoadParticipantRoles() {
        System.out.println("loadParticipantRoles");
        MatchDetail match = null;
        itemAnalysisService.loadParticipantRoles(match);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadFinalBuildOrders method, of class ItemAnalysisService.
     */
    @Test
    public void testLoadFinalBuildOrders() {
        System.out.println("loadFinalBuildOrders");
        MatchDetail match = null;
        itemAnalysisService.loadFinalBuildOrders(match);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateAverageCostPerGold method, of class ItemAnalysisService.
     */
    @Test
    public void testGenerateAverageCostPerGold() {
        System.out.println("generateAverageCostPerGold");
        itemAnalysisService.loadItems();
        HashMap<ItemStat, Double> result = itemAnalysisService.generateAverageCostPerGold();
        result.keySet().forEach((stat) -> {
            assertTrue("Stats have been initialized to some positive average cost values", result.get(stat) > 0);
        });
    }

    /**
     * Test of getAverageStatCost method, of class ItemAnalysisService.
     */
    @Test
    public void testGetAverageStatCost() {
        System.out.println("getAverageStatCost");
        itemAnalysisService.loadItems();
        Double result = itemAnalysisService.getAverageStatCost(ItemStat.AbilityPower);
        assertTrue("Stat has been initialized to some positive average cost values", result > 0);
    }

    /**
     * Test of calculateRoleFromBuild method, of class ItemAnalysisService.
     */
    @Test
    public void testCalculateRoleFromBuild() {
        System.out.println("calculateRoleFromBuild");
        itemAnalysisService.loadItems();
        Collection<Integer> buildItems = new LinkedList<>();
        buildItems.add(3048);
        Role result = itemAnalysisService.calculateRoleFromBuild(buildItems);
        assertEquals("A simple AP build should be determined to be AP Carry", Role.AP_CARRY, result);
    }

}
