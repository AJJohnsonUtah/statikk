/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.dao;

import com.njin.loltheory.model.LolVersion;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author AJ
 */
public class LolVersionDaoImpl implements LolVersionDao{
    @PersistenceContext
    EntityManager em;

    @Override
    public void createLolVersion(LolVersion lolVersion) {
        em.persist(lolVersion);
    }

    @Override
    public void updateLolVersion(LolVersion lolVersion) {
        em.merge(lolVersion);
    }
}
