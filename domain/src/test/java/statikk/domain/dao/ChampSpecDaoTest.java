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
import statikk.domain.entity.ChampSpec;
import statikk.domain.entity.LolVersion;
import statikk.domain.entity.enums.Lane;
import statikk.domain.entity.enums.Role;
import statikk.domain.riotapi.model.QueueType;
import statikk.domain.riotapi.model.Rank;

/**
 *
 * @author AJ
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class ChampSpecDaoTest {

    @Autowired
    private ChampSpecDao champSpecDao;

    @Autowired
    private LolVersionDao lolVersionDao;

    public ChampSpecDaoTest() {
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
     * Test of find method, of class ChampSpecDao.
     */
    @Test
    public void testFind() {
        System.out.println("find");
        ChampSpec champSpec = new ChampSpec(
                101,
                new LolVersion("6.6"),
                QueueType.CUSTOM,
                Lane.BOTTOM,
                Role.AD_CARRY,
                Rank.UNRANKED
        );
        champSpecDao.save(champSpec);

        ChampSpec foundChampSpec = champSpecDao.findByChampionIdAndMatchTypeAndLolVersionAndLaneAndRoleAndRank(
                champSpec.getChampionId(),
                champSpec.getMatchType(),
                champSpec.getLolVersion(),
                champSpec.getLane(),
                champSpec.getRole(),
                champSpec.getRank()
        );
        assertNotNull("A newly created/found LolVersion should have an id of 1", foundChampSpec.getLolVersion().getLolVersionId());
        assertNotNull("A newly created/found ChampSpec should have an id of 1", foundChampSpec.getChampSpecId());
    }

    @Test
    public void saveDuplicate() {
        System.out.println("Save Duplicate ChampSpec");

        LolVersion lolVersion = lolVersionDao.save(new LolVersion("6.6"));

        ChampSpec champSpecA = new ChampSpec(
                101,
                lolVersion,
                QueueType.CUSTOM,
                Lane.BOTTOM,
                Role.AD_CARRY,
                Rank.UNRANKED
        );
        ChampSpec champSpecB = new ChampSpec(
                101,
                lolVersion,
                QueueType.CUSTOM,
                Lane.BOTTOM,
                Role.AD_CARRY,
                Rank.UNRANKED
        );
        // Save entity
        champSpecDao.save(champSpecA);

        // Search for identical entity and verify ids match
        ChampSpec found = champSpecDao.findByChampionIdAndMatchTypeAndLolVersionAndLaneAndRoleAndRank(
                champSpecB.getChampionId(),
                champSpecB.getMatchType(),
                champSpecB.getLolVersion(),
                champSpecB.getLane(),
                champSpecB.getRole(),
                champSpecB.getRank()
        );
        assertEquals("The two saved ChampSpecs should have the same id, since they are the same", champSpecA.getChampSpecId(), found.getChampSpecId());

        // Save found entity, just to make sure no new record is added
        champSpecDao.save(found);

        found = champSpecDao.findByChampionIdAndMatchTypeAndLolVersionAndLaneAndRoleAndRank(
                champSpecB.getChampionId(),
                champSpecB.getMatchType(),
                champSpecB.getLolVersion(),
                champSpecB.getLane(),
                champSpecB.getRole(),
                champSpecB.getRank()
        );
        assertEquals("The two saved ChampSpecs should have the same id, since they are the same", champSpecA.getChampSpecId(), found.getChampSpecId());

    }
}
