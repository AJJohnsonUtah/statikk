/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.dao;

import com.njin.loltheory.model.FinalBuildOrder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author AJ
 */
public class FinalBuildOrderDaoImpl implements FinalBuildOrderDao {

    @PersistenceContext
    EntityManager em;

    @Override
    public void createFinalBuildOrder(FinalBuildOrder finalBuildOrder) {
        em.persist(finalBuildOrder);
    }

    @Override
    public void updateFinalBuildOrder(FinalBuildOrder finalBuildOrder) {
        em.merge(finalBuildOrder);
    }

}
