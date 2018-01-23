/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.entity.converter;

import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import statikk.domain.entity.enums.Role;

/**
 *
 * @author Grann
 */
public class TeamCompMapConverterTest {
    
    public TeamCompMapConverterTest() {
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
     * Test of convertToString method, of class TeamCompMapConverter.
     */
    @Test
    public void testConvertToString() {
        System.out.println("convertToString");
        Map<Role, Integer> roleMap = new HashMap<>();
        roleMap.put(Role.TANK, 2);
        roleMap.put(Role.AD_CARRY, 3);
        String expResult = "203";
        String result = TeamCompMapConverter.convertToString(roleMap);
        assertEquals(expResult, result);
    }
    
        /**
     * Test of convertToString method, of class TeamCompMapConverter.
     */
    @Test
    public void testConvertToString2() {
        System.out.println("convertToString");
        Map<Role, Integer> roleMap = new HashMap<>();
        roleMap.put(Role.TANK, 5);
        roleMap.put(Role.AD_CARRY, 0);
        String expResult = "5";
        String result = TeamCompMapConverter.convertToString(roleMap);
        assertEquals(expResult, result);
    }

    /**
     * Test of convertToMap method, of class TeamCompMapConverter.
     */
    @Test
    public void testConvertToMap() {
        System.out.println("convertToMap");
        String val = "2030000";
        Map<Role, Integer> roleMap = new HashMap<>();
        roleMap.put(Role.TANK, 2);
        roleMap.put(Role.AD_CARRY, 3);
        Map<Role, Integer> result = TeamCompMapConverter.convertToMap(val);
        assertEquals(roleMap, result);
    }
    
        /**
     * Test of convertToMap method, of class TeamCompMapConverter.
     */
    @Test
    public void testConvertToMap2() {
        System.out.println("convertToMap");
        String val = "203000000";
        Map<Role, Integer> roleMap = new HashMap<>();
        roleMap.put(Role.TANK, 2);
        roleMap.put(Role.AD_CARRY, 3);
        Map<Role, Integer> result = TeamCompMapConverter.convertToMap(val);
        assertEquals(roleMap, result);
    }
    
    
}
