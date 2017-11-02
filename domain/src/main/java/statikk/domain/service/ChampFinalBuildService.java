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
import statikk.domain.dao.ChampFinalBuildDao;
import statikk.domain.entity.ChampFinalBuild;

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
    public ChampFinalBuild create(ChampFinalBuild champFinalBuild) {
        return champFinalBuildDao.save(champFinalBuild);
    }

    @Override
    public ChampFinalBuild update(ChampFinalBuild champFinalBuild) {
        return champFinalBuildDao.save(champFinalBuild);
    }

    public void batchInsertOrUpdate(Collection<ChampFinalBuild> champFinalBuilds) {
        champFinalBuilds.forEach((champFinalBuild) -> {
            champFinalBuild.combine(champFinalBuildDao.findOne(champFinalBuild.getChampFinalBuildPK()));
        });
        champFinalBuildDao.save(champFinalBuilds);
    }

}
