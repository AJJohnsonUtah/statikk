/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.dao;

import com.njin.loltheory.model.ChampFinalBuild;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author AJ
 */
public class ChampFinalBuildDaoImpl implements ChampFinalBuildDao {

    @PersistenceContext
    EntityManager em;

    @Override
    public void createChampFinalBuild(ChampFinalBuild champFinalBuild) {
        em.persist(champFinalBuild);
    }

    @Override
    public void updateChampFinalBuild(ChampFinalBuild champFinalBuild) {
        em.merge(champFinalBuild);
    }

}
