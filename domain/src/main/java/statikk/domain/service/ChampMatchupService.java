/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.service;

import java.util.Collection;
import javax.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
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

    public void batchInsertOrUpdate(Collection<ChampMatchup> champMatchups) {
        champMatchups.forEach((champMatchup) -> {
            champMatchup.combine(find(champMatchup));
        });
        champMatchupDao.save(champMatchups);
    }

    public ChampMatchup find(ChampMatchup champMatchup) {
        return champMatchupDao.findOne(champMatchup.getChampMatchupPK());
    }
}
