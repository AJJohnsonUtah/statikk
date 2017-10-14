/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.framework.service;

import statikk.framework.dao.ChampFinalBuildDao;
import statikk.framework.entity.ChampFinalBuild;
import statikk.framework.entity.ChampFinalBuildPK;
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
