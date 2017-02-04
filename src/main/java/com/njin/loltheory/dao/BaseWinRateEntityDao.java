/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.dao;

import com.njin.loltheory.entity.BaseWinRateEntity;
import java.util.Map;

/**
 *
 * @author AJ
 * @param <E>
 * @param <EID>
 */
public abstract class BaseWinRateEntityDao<E extends BaseWinRateEntity, EID> extends BaseDao {

    public void batchInsertOrUpdate(Map<EID, E> winRateEntities) {
        int counter = 0;
        for (E winRateEntity : winRateEntities.values()) {
            E existingEntity = find(winRateEntity);
            if (existingEntity == null) {
                em.persist(winRateEntity);
            } else {
                existingEntity.combine(winRateEntity);
            }

            counter++;
            if (counter % 100 == 0) {
                em.flush();
                em.clear();
            }
        }
    }

    public abstract E find(E entity);
}
