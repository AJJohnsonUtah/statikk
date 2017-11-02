/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.service;

import java.util.Collection;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import statikk.domain.dao.ChampMatchupDao;
import statikk.domain.entity.ChampMatchup;

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
    public ChampMatchup create(ChampMatchup champMatchup) {
        return champMatchupDao.save(champMatchup);
    }

    @Override
    public ChampMatchup update(ChampMatchup champMatchup) {
        return champMatchupDao.save(champMatchup);
    }

    public void batchInsertOrUpdate(Collection<ChampMatchup> champMatchups) {
        champMatchups.forEach((champMatchup) -> {
            champMatchup.combine(champMatchupDao.findOne(champMatchup.getChampMatchupPK()));
        });
        champMatchupDao.save(champMatchups);
    }
}
