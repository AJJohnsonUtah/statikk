/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.dao;

import org.springframework.stereotype.Repository;
import statikk.domain.entity.ChampTeamup;
import statikk.domain.entity.ChampTeamupPK;

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