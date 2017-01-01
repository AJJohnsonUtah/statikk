/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.dao;

import com.njin.loltheory.model.LolMatch;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author AJ
 */
public class LolMatchDaoImpl implements LolMatchDao {

    @PersistenceContext
    EntityManager em;

    @Override
    public void createLolMatch(LolMatch lolMatch) {
        em.persist(lolMatch);
    }

    @Override
    public void updateLolMatch(LolMatch lolMatch) {
        em.merge(lolMatch);
    }
}
