/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.dao;

import com.njin.loltheory.model.ChampMatchup;
import com.njin.loltheory.model.ChampSummonerSpells;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author AJ
 */
public class ChampSummonerSpellsDaoImpl implements ChampSummonerSpellsDao {

    @PersistenceContext
    EntityManager em;

    @Override
    public void createChampSummonerSpells(ChampSummonerSpells champSummonerSpells) {
        em.persist(champSummonerSpells);
    }

    @Override
    public void updateChampSummonerSpells(ChampSummonerSpells champSummonerSpells) {
        em.merge(champSummonerSpells);
    }

}
