/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.service;

import com.njin.loltheory.dao.FinalBuildOrderDao;
import com.njin.loltheory.model.FinalBuildOrder;
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

    @Override
    public void create(FinalBuildOrder finalBuildOrder) {
        finalBuildOrderDao.create(finalBuildOrder);
    }

    @Override
    public void update(FinalBuildOrder finalBuildOrder) {
        finalBuildOrderDao.update(finalBuildOrder);
    }
}
