/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.entity;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author AJ
 */
public class LolVersionTest {

    public LolVersionTest() {
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
    public void lolVersionConstructor() {
        System.out.println("LolVersion Constructor");
        LolVersion instance = new LolVersion("1.5");
        assertEquals("Major version should be set to 1", 1, instance.getMajorVersion());
        assertEquals("Minor version should be set to 5", 5, instance.getMinorVersion());
    }

    /**
     * Test of equals method, of class LolVersion.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        LolVersion instance = new LolVersion("1.1");
        assertEquals("equals method returns false when compared to null", false, instance.equals(null));
        LolVersion almostEqual = new LolVersion("1.2");
        assertEquals("equals method returns false when compared to same major, different minor version",
                false, instance.equals(almostEqual));
        almostEqual = new LolVersion("2.1");
        assertEquals("equals method returns false when compared to same minor, different major version",
                false, instance.equals(almostEqual));
        LolVersion sameVersion = new LolVersion("1.1");
        assertEquals("equals method returns true when compared to same major and minor version", true, instance.equals(sameVersion));

    }

    /**
     * Test of toString method, of class LolVersion.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        LolVersion instance = new LolVersion("1.5");
        String expResult = "1.5";
        String result = instance.toString();
        assertTrue("LolVersion's toString should output the version in standard format", result.contains(expResult));
    }

}
