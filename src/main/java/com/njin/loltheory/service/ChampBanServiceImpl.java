/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.service;

import com.njin.loltheory.dao.ChampBanDao;
import com.njin.loltheory.model.ChampBan;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author AJ
 */
public class ChampBanServiceImpl implements ChampBanService {

    @Autowired
    ChampBanDao champBanDao;
    
    @Override
    public void createChampBan(ChampBan champBan) {
        champBanDao.createChampBan(champBan);
    }

    @Override
    public void updateChampBan(ChampBan champBan) {
        champBanDao.updateChampBan(champBan);
    }
    
}
