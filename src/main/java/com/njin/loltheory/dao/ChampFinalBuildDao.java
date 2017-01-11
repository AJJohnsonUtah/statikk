/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.dao;

import com.njin.loltheory.entity.ChampFinalBuild;
import com.njin.loltheory.entity.ChampFinalBuildPK;
import org.springframework.stereotype.Repository;

/**
 *
 * @author AJ
 */
@Repository
public class ChampFinalBuildDao extends BaseWinRateEntityDao<ChampFinalBuild, ChampFinalBuildPK> {

    @Override
    public ChampFinalBuild find(ChampFinalBuild entity) {
        return em.find(ChampFinalBuild.class, entity);
    }

}
