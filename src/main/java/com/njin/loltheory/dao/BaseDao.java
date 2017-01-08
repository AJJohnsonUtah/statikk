/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.dao;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author AJ
 */
public abstract class BaseDao <E> {
    @PersistenceContext
    EntityManager em;

    public void create(E entity) {
        try {
            em.persist(entity);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void update(E entity) {
        em.merge(entity);
    }
}
