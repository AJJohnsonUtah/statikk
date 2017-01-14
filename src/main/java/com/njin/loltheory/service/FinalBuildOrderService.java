/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.service;

import com.njin.loltheory.dao.FinalBuildOrderDao;
import com.njin.loltheory.entity.FinalBuildOrder;
import com.njin.loltheory.entity.LolVersion;
import java.util.HashMap;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author AJ
 */
@Service
@Transactional
public class FinalBuildOrderService extends BaseService<FinalBuildOrder> {

    @Autowired
    FinalBuildOrderDao finalBuildOrderDao;
    
    private final HashMap<FinalBuildOrder, FinalBuildOrder> cachedFinalBuildOrders = new HashMap<>();

    @Override
    public void create(FinalBuildOrder finalBuildOrder) {
        finalBuildOrderDao.create(finalBuildOrder);
    }

    @Override
    public void update(FinalBuildOrder finalBuildOrder) {
        finalBuildOrderDao.update(finalBuildOrder);
    }

    public FinalBuildOrder find(FinalBuildOrder finalBuildOrder) {
        return finalBuildOrderDao.find(finalBuildOrder);
    }

    public FinalBuildOrder loadEntity(FinalBuildOrder finalBuildOrder) {
        if (cachedFinalBuildOrders.containsKey(finalBuildOrder)) {
            return cachedFinalBuildOrders.get(finalBuildOrder);
        }
        FinalBuildOrder foundFinalBuildOrder = find(finalBuildOrder);
        if(foundFinalBuildOrder == null) {
            create(finalBuildOrder);            
            cachedFinalBuildOrders.put(finalBuildOrder, finalBuildOrder);
            return finalBuildOrder;
        }
        return foundFinalBuildOrder;
    }
}
