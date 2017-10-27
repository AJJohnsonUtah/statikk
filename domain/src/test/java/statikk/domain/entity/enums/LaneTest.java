/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.entity.enums;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author AJ
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class LaneTest {

    public LaneTest() {
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
     * Test of values method, of class Lane.
     */
    @Test
    public void testValues() {
        System.out.println("values");
        int expResult = 6;
        Lane[] result = Lane.values();
        assertEquals(expResult, result.length);
    }

    /**
     * Test of valueOf method, of class Lane.
     */
    @Test
    public void testValueOf() {
        System.out.println("valueOf");
        String name = "BOTTOM";
        Lane expResult = Lane.BOTTOM;
        Lane result = Lane.valueOf(name);
        assertEquals(expResult, result);
    }

    /**
     * Test of getLaneId method, of class Lane.
     */
    @Test
    public void testGetLaneId() {

        System.out.println("getLaneId");
        Lane instance = Lane.BOTTOM;
        int expResult = 2;
        int result = instance.getLaneId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLane method, of class Lane.
     */
    @Test
    public void testGetLane() {
        System.out.println("getLane");
        int id = 2;
        Lane expResult = Lane.BOTTOM;
        Lane result = Lane.getLane(id);
        assertEquals(expResult, result);        
    }

}
