/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.dao;

import com.njin.loltheory.model.ChampTeamup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author AJ
 */
public class ChampTeamupDaoImpl implements ChampTeamupDao {

    @PersistenceContext
    EntityManager em;

    @Override
    public void createChampTeamup(ChampTeamup champTeamup) {
        em.persist(champTeamup);
    }

    @Override
    public void updateChampTeamup(ChampTeamup champTeamup) {
        em.merge(champTeamup);
    }
}
