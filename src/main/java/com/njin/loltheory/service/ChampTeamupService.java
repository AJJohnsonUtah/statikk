/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.service;

import com.njin.loltheory.dao.ChampTeamupDao;
import com.njin.loltheory.model.ChampTeamup;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author AJ
 */
@Service
@Transactional
public class ChampTeamupService extends BaseService<ChampTeamup> {

    @Autowired
    ChampTeamupDao champTeamupDao;

    @Override
    public void create(ChampTeamup champTeamup) {
        champTeamupDao.create(champTeamup);
    }

    @Override
    public void update(ChampTeamup champTeamup) {
        champTeamupDao.update(champTeamup);
    }
}
