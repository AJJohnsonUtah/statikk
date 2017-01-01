/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.dao;

import com.njin.loltheory.model.ChampMatchup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author AJ
 */
public class ChampMatchupDaoImpl implements ChampMatchupDao {

    @PersistenceContext
    EntityManager em;

    @Override
    public void createChampMatchup(ChampMatchup champMatchup) {
        em.persist(champMatchup);
    }

    @Override
    public void updateChampMatchup(ChampMatchup champMatchup) {
        em.merge(champMatchup);
    }

}
