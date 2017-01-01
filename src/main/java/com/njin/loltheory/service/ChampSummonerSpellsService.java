/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.service;

import com.njin.loltheory.dao.ChampSummonerSpellsDao;
import com.njin.loltheory.model.ChampSummonerSpells;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author AJ
 */
@Service
@Transactional
public class ChampSummonerSpellsService extends BaseService<ChampSummonerSpells> {

    @Autowired
    ChampSummonerSpellsDao champSummonerSpellsDao;

    @Override
    public void create(ChampSummonerSpells champSummonerSpells) {
        champSummonerSpellsDao.create(champSummonerSpells);
    }

    @Override
    public void update(ChampSummonerSpells champSummonerSpells) {
        champSummonerSpellsDao.update(champSummonerSpells);
    }
}
