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
import statikk.domain.dao.ChampFinalBuildDao;
import statikk.domain.entity.ChampFinalBuild;
import statikk.domain.entity.ChampFinalBuildPK;

/**
 *
 * @author AJ
 */
@Service
@Transactional
public class ChampFinalBuildService extends BaseService<ChampFinalBuild> {

    @Autowired
    ChampFinalBuildDao champFinalBuildDao;

    @Override
    public void create(ChampFinalBuild champFinalBuild) {
        champFinalBuildDao.create(champFinalBuild);
    }

    @Override
    public void update(ChampFinalBuild champFinalBuild) {
        champFinalBuildDao.update(champFinalBuild);
    }

    public void batchInsertOrUpdate(Map<ChampFinalBuildPK, ChampFinalBuild> champMatchups) {
        champFinalBuildDao.batchInsertOrUpdate(champMatchups);
    }

}
