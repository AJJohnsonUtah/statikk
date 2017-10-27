/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.dao;

import org.springframework.stereotype.Repository;
import statikk.domain.entity.ChampFinalBuild;
import statikk.domain.entity.ChampFinalBuildPK;

/**
 *
 * @author AJ
 */
@Repository
public class ChampFinalBuildDao extends BaseWinRateEntityDao<ChampFinalBuild, ChampFinalBuildPK> {

    @Override
    public ChampFinalBuild find(ChampFinalBuild entity) {
        return em.find(ChampFinalBuild.class, entity.getChampFinalBuildPK());
    }

}