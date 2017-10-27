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
import statikk.domain.dao.ChampTeamupDao;
import statikk.domain.entity.ChampTeamup;
import statikk.domain.entity.ChampTeamupPK;

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
    public void create(ChampTeamup champTeamup) {
        champTeamupDao.create(champTeamup);
    }

    @Override
    public void update(ChampTeamup champTeamup) {
        champTeamupDao.update(champTeamup);
    }

    public void batchInsertOrUpdate(Map<ChampTeamupPK, ChampTeamup> champTeamups) {
        champTeamupDao.batchInsertOrUpdate(champTeamups);
    }

}
