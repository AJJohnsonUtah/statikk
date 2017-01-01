/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.service;

import com.njin.loltheory.dao.ChampFinalBuildDao;
import com.njin.loltheory.model.ChampFinalBuild;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author AJ
 */
@Service
@Transactional
public class ChampFinalBuildService extends BaseService<ChampFinalBuild> {

    @Autowired
    ChampFinalBuildDao champFinalBuildDao;
    
    @Override
    public void create(ChampFinalBuild champFinalBuild) {
        champFinalBuildDao.create(champFinalBuild);
    }

    @Override
    public void update(ChampFinalBuild champFinalBuild) {
        champFinalBuildDao.update(champFinalBuild);
    }
    
    
}
