/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.service;

import com.njin.loltheory.dao.LolVersionDao;
import com.njin.loltheory.entity.LolVersion;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author AJ
 */
@Service
@Transactional
public class LolVersionService extends BaseService<LolVersion> {

    @Autowired
    LolVersionDao lolVersionDao;

    @Override
    public void create(LolVersion lolVersion) {
        lolVersionDao.create(lolVersion);
    }

    @Override
    public void update(LolVersion lolVersion) {
        lolVersionDao.update(lolVersion);
    }
    
    public List<LolVersion> findAll() {
        return lolVersionDao.findAll();
    }
}
