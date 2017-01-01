/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.service;

import com.njin.loltheory.dao.LolMatchDao;
import com.njin.loltheory.model.LolMatch;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author AJ
 */
@Service
@Transactional
public class LolMatchService extends BaseService<LolMatch> {

    @Autowired
    LolMatchDao lolMatchDao;

    @Override
    public void create(LolMatch lolMatch) {
        lolMatchDao.create(lolMatch);
    }

    @Override
    public void update(LolMatch lolMatch) {
        lolMatchDao.update(lolMatch);
    }
}
