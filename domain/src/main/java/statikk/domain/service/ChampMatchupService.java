/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.service;

import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import statikk.domain.dao.ChampMatchupDao;
import statikk.domain.entity.ChampMatchup;
import statikk.domain.entity.ChampMatchupPK;

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
