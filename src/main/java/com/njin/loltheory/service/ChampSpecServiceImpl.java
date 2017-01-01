/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.service;

import com.njin.loltheory.dao.ChampSpecDao;
import com.njin.loltheory.model.ChampSpec;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author AJ
 */
@Service
@Transactional
public class ChampSpecServiceImpl implements ChampSpecService {

    @Autowired
    ChampSpecDao champSpecDao;

    @Override
    public void createChampSpec(ChampSpec champSpec) {
        champSpecDao.createChampSpec(champSpec);
    }

    @Override
    public ChampSpec findChampSpec(Long id) {
        return champSpecDao.findChampSpec(id);
    }

}
