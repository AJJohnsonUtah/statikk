/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.service;

import java.util.HashMap;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import statikk.domain.dao.FinalBuildOrderDao;
import statikk.domain.entity.FinalBuildOrder;

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
    public FinalBuildOrder create(FinalBuildOrder finalBuildOrder) {
        return finalBuildOrderDao.save(finalBuildOrder);
    }

    @Override
    public FinalBuildOrder update(FinalBuildOrder finalBuildOrder) {
        return finalBuildOrderDao.save(finalBuildOrder);
    }

    public FinalBuildOrder find(FinalBuildOrder finalBuildOrder) {
        return finalBuildOrderDao.findByBuildOrder(finalBuildOrder.getBuildOrder());
    }

    public FinalBuildOrder findOrCreate(FinalBuildOrder finalBuildOrder) {
        FinalBuildOrder foundInstance = find(finalBuildOrder);
        if (foundInstance != null) {
            return foundInstance;
        }
        return create(finalBuildOrder);
    }
}
