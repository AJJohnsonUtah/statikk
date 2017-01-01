/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.dao;

import com.njin.loltheory.model.ChampSpec;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author AJ
 */
@Repository
public class ChampSpecDaoImpl implements ChampSpecDao {

    @PersistenceContext
    EntityManager em;

    @Override
    public void createChampSpec(ChampSpec champSpec) {
        em.persist(champSpec);
    }

    @Override
    public ChampSpec findChampSpec(Long id) {
        return em.find(ChampSpec.class, id);
    }
}
