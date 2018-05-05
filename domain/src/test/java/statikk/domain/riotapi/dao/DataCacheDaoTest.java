/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.riotapi.dao;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import statikk.domain.riotapi.model.DataCacheItem;

/**
 *
 * @author Grann
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class DataCacheDaoTest {

    @Autowired
    DataCacheDao dataCacheDao;

    public DataCacheDaoTest() {
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
    public void testSaveAndFind() {
        DataCacheItem item = new DataCacheItem("123", "cached content is great!");
        assertNull("An item that has not been persisted should not be found", dataCacheDao.findOne("123"));
        dataCacheDao.save(item);
        assertNotNull("An item that has been persisted should be found", dataCacheDao.findOne("123"));
    }

}
