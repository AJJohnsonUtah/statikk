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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import statikk.domain.entity.enums.Role;
import statikk.domain.riotapi.model.ItemListDto;
import statikk.domain.riotapi.model.ItemStat;
import statikk.domain.riotapi.model.MatchDetail;
import statikk.domain.service.FinalBuildOrderService;

/**
 *
 * @author Grann
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpecVersionItemService.class)
public class SpecVersionItemServiceTest {
   
    @MockBean
    FinalBuildOrderService finalBuildOrderService;

    private static SpecVersionItemService instance;
    
    private static ItemListDto mockItems;

    public SpecVersionItemServiceTest() {
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
        instance = new SpecVersionItemService("1.2.3", mockItems.getData(), finalBuildOrderService);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of isFinalItem method, of class ItemAnalysisService.
     */
    @Test
    public void testIsFinalItem() {
        System.out.println("isFinalItem");
        Integer finalItemId = 3031; // Infinity Edge
        Integer nonFinalItemId = 1036; // Long Sword
        assertTrue("Item should be final item", instance.isFinalItem(finalItemId));
        assertFalse("Item should not be final item", instance.isFinalItem(nonFinalItemId));
    }

    /**
     * Test of toBaseFinalItem method, of class ItemAnalysisService.
     */
    @Test
    public void testToBaseFinalItem() {
        System.out.println("toBaseFinalItem");
        Integer superFinalItemId = 3040; // Seraph's Embrace
        Integer finalItemId = 3003; // Archangel's Staff
        assertEquals("A 'transformed' final item should reduce to its base final item id", finalItemId, instance.toBaseFinalItem(superFinalItemId));
    }

    /**
     * Test of loadParticipantRoles method, of class ItemAnalysisService.
     */
    @Test
    public void testLoadParticipantRoles() {
        System.out.println("loadParticipantRoles");
        MatchDetail match = new MatchDetail();
        instance.loadParticipantRoles(match);
    }

    /**
     * Test of loadFinalBuildOrders method, of class ItemAnalysisService.
     */
    @Test
    public void testLoadFinalBuildOrders() {
        System.out.println("loadFinalBuildOrders");
        MatchDetail match = new MatchDetail();
        instance.loadFinalBuildOrders(match);
        // Only fails if there is an error calling this method.
    }

    /**
     * Test of generateAverageCostPerGold method, of class ItemAnalysisService.
     */
    @Test
    public void testGenerateAverageCostPerGold() {
        System.out.println("generateAverageCostPerGold");
        HashMap<ItemStat, Double> result = instance.generateAverageCostPerGold();
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
        Double result = instance.getAverageStatCost(ItemStat.AbilityPower);
        assertTrue("Stat has been initialized to some positive average cost values", result > 0);
    }

    /**
     * Test of calculateRoleFromBuild method, of class ItemAnalysisService.
     */
    @Test
    public void testCalculateRoleFromBuild() {
        System.out.println("calculateRoleFromBuild");
        Collection<Integer> buildItems = new LinkedList<>();
        buildItems.add(3048);
        Role result = instance.calculateRoleFromBuild(buildItems);
        assertEquals("A simple AP build should be determined to be AP Carry", Role.AP_CARRY, result);
    }

    /**
     * Test of calculateRoleFromBuild method, of class ItemAnalysisService.
     */
    @Test
    public void testCalculateRoleFromBuild2() {
        System.out.println("calculateRoleFromBuild");
        Collection<Integer> buildItems = new LinkedList<>();
        buildItems.add(2301); // Eye of the watchers
        buildItems.add(3124); // Guinsoo's Rageblade
        buildItems.add(3020); // Sorcerer's Shoes
        buildItems.add(3146); // Hextech Gunblade
        Role result = instance.calculateRoleFromBuild(buildItems);
        assertEquals("A simple AP build should be determined to be AP Carry", Role.HYBRID, result);
    }

}
