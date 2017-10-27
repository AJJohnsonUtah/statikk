/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.dao;

import org.springframework.stereotype.Repository;
import statikk.domain.entity.ChampMatchup;
import statikk.domain.entity.ChampMatchupPK;

/**
 *
 * @author AJ
 */
@Repository
public class ChampMatchupDao extends BaseWinRateEntityDao<ChampMatchup, ChampMatchupPK> {

    @Override
    public ChampMatchup find(ChampMatchup entity) {
        return em.find(ChampMatchup.class, entity.getChampMatchupPK());
    }

}