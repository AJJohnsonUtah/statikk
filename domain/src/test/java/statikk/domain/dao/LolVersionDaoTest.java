/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.dao;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import statikk.domain.entity.LolVersion;

/**
 *
 * @author AJ
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class LolVersionDaoTest {

    @Autowired
    LolVersionDao lolVersionDao;

    public LolVersionDaoTest() {
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
     * Test of findByMajorVersionAndMinorVersion method, of class LolVersionDao.
     */
    @Test
    public void testFindByMajorVersionAndMinorVersion() {
        System.out.println("findByMajorVersionAndMinorVersion");
        int majorVersion = 1;
        int minorVersion = 2;
        LolVersion instance = new LolVersion("1.2");
        lolVersionDao.save(instance);
        LolVersion result = lolVersionDao.findByMajorVersionAndMinorVersion(majorVersion, minorVersion);
        assertNotNull("Newly created version should have non-null id", result.getLolVersionId());
        assertEquals("Newly created version should have matching major version", majorVersion, result.getMajorVersion());
        assertEquals("Newly created version should have matching minor version", minorVersion, result.getMinorVersion());
    }

}
