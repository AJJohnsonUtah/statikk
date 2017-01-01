/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.service;

import com.njin.loltheory.dao.ChampBanDao;
import com.njin.loltheory.model.ChampBan;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author AJ
 */
@Service
@Transactional
public class ChampBanService extends BaseService<ChampBan> {

    @Autowired
    ChampBanDao champBanDao;

    @Override
    public void create(ChampBan champBan) {
        champBanDao.create(champBan);
    }

    @Override
    public void update(ChampBan champBan) {
        champBanDao.update(champBan);
    }
}
