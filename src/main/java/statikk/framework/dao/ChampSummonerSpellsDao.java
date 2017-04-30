/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.framework.dao;

import statikk.framework.entity.ChampSummonerSpells;
import statikk.framework.entity.ChampSummonerSpellsPK;
import org.springframework.stereotype.Repository;

/**
 *
 * @author AJ
 */
@Repository
public class ChampSummonerSpellsDao extends BaseWinRateEntityDao<ChampSummonerSpells, ChampSummonerSpellsPK> {

    @Override
    public ChampSummonerSpells find(ChampSummonerSpells entity) {
        return em.find(ChampSummonerSpells.class, entity.getChampSummonerSpellsPK());
    }

}
