/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.framework.service;

import statikk.framework.dao.ChampMatchupDao;
import statikk.framework.entity.ChampMatchup;
import statikk.framework.entity.ChampMatchupPK;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author AJ
 */
@Service
@Transactional
public class ChampMatchupService extends BaseService<ChampMatchup> {

    @Autowired
    ChampMatchupDao champMatchupDao;
    
    @Override
    public void create(ChampMatchup champMatchup) {
        champMatchupDao.create(champMatchup);
    }

    @Override
    public void update(ChampMatchup champMatchup) {
        champMatchupDao.update(champMatchup);
    }
    
    public void batchInsertOrUpdate(Map<ChampMatchupPK, ChampMatchup> champMatchups) {
        champMatchupDao.batchInsertOrUpdate(champMatchups);
    }
}
