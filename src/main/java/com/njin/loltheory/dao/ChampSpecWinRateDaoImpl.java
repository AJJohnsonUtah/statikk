/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.dao;

import com.njin.loltheory.model.ChampSpecWinRate;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author AJ
 */
public class ChampSpecWinRateDaoImpl implements ChampSpecWinRateDao {

    @PersistenceContext
    EntityManager em;

    @Override
    public void createChampSpecWinRate(ChampSpecWinRate champSpecWinRate) {
        em.persist(champSpecWinRate);
    }

    @Override
    public void updateChampSpecWinRate(ChampSpecWinRate champSpecWinRate) {
        em.merge(champSpecWinRate);
    }

}
