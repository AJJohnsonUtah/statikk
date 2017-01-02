/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.service;

import com.njin.loltheory.dao.ChampMatchupDao;
import com.njin.loltheory.entity.ChampMatchup;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author AJ
 */
@Service
@Transactional
public class ChampMatchupService extends BaseService<ChampMatchup> {

    @Autowired
    ChampMatchupDao champMatchupDao;
    
    @Override
    public void create(ChampMatchup champMatchup) {
        champMatchupDao.create(champMatchup);
    }

    @Override
    public void update(ChampMatchup champMatchup) {
        champMatchupDao.update(champMatchup);
    }
    
}
