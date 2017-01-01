/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.dao;

import com.njin.loltheory.model.ChampBan;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author AJ
 */
public class ChampBanDaoImpl implements ChampBanDao {

    @PersistenceContext
    EntityManager em;

    @Override
    public void createChampBan(ChampBan champBan) {
        em.persist(champBan);
    }

    @Override
    public void updateChampBan(ChampBan champBan) {
        em.merge(champBan);
    }

}
