/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.framework.dao;

import statikk.framework.entity.ChampMatchup;
import statikk.framework.entity.ChampMatchupPK;
import org.springframework.stereotype.Repository;

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
