/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.dao;

import com.njin.loltheory.entity.ChampTeamup;
import com.njin.loltheory.entity.ChampTeamupPK;
import org.springframework.stereotype.Repository;

/**
 *
 * @author AJ
 */
@Repository
public class ChampTeamupDao extends BaseWinRateEntityDao<ChampTeamup, ChampTeamupPK> {

    @Override
    public ChampTeamup find(ChampTeamup entity) {
        return em.find(ChampTeamup.class, entity.getChampTeamupPK());
    }

}
