/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.dao;

import org.springframework.stereotype.Repository;
import statikk.domain.entity.ChampSummonerSpells;
import statikk.domain.entity.ChampSummonerSpellsPK;

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
