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
import statikk.domain.dao.ChampTeamupDao;
import statikk.domain.entity.ChampTeamup;

/**
 *
 * @author AJ
 */
@Service
@Transactional
public class ChampTeamupService extends BaseService<ChampTeamup> {

    @Autowired
    ChampTeamupDao champTeamupDao;

    @Override
    public ChampTeamup create(ChampTeamup champTeamup) {
        return champTeamupDao.save(champTeamup);
    }

    @Override
    public ChampTeamup update(ChampTeamup champTeamup) {
        return champTeamupDao.save(champTeamup);
    }

    public void batchInsertOrUpdate(Collection<ChampTeamup> champTeamups) {
        champTeamups.forEach((champTeamup) -> {
            champTeamup.combine(champTeamupDao.findOne(champTeamup.getChampTeamupPK()));
        });
        champTeamupDao.save(champTeamups);
    }

}
