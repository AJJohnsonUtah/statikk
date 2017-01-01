/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.service;

import com.njin.loltheory.dao.ChampFinalBuildDao;
import com.njin.loltheory.model.ChampFinalBuild;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author AJ
 */
public class ChampFinalBuildServiceImpl implements ChampFinalBuildService {

    @Autowired
    ChampFinalBuildDao champFinalBuildDao;
    
    @Override
    public void createChampFinalBuild(ChampFinalBuild champFinalBuild) {
        champFinalBuildDao.createChampFinalBuild(champFinalBuild);
    }

    @Override
    public void updateChampFinalBuild(ChampFinalBuild champFinalBuild) {
        champFinalBuildDao.updateChampFinalBuild(champFinalBuild);
    }
    
    
}
