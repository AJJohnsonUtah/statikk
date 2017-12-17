/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.service;

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
public class FinalBuildOrderService {

    @Autowired
    FinalBuildOrderDao finalBuildOrderDao;

    public FinalBuildOrder findOrCreate(FinalBuildOrder finalBuildOrder) {
        FinalBuildOrder foundFinalBuildOrder = find(finalBuildOrder);
        if (foundFinalBuildOrder == null) {
            return finalBuildOrderDao.save(finalBuildOrder);
        } else {
            return foundFinalBuildOrder;
        }
    }

    public FinalBuildOrder find(FinalBuildOrder finalBuildOrder) {
        return finalBuildOrderDao.findByBuildOrder(finalBuildOrder.getBuildOrder());
    }
}
