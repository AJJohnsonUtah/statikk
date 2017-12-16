/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.service;

import java.util.Collection;
import javax.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import statikk.domain.dao.ChampSummonerSpellsDao;
import statikk.domain.entity.ChampSummonerSpells;

/**
 *
 * @author AJ
 */
@Service
@Transactional
public class ChampSummonerSpellsService extends BaseService<ChampSummonerSpells> {

    @Autowired
    ChampSummonerSpellsDao champSummonerSpellsDao;

    public void batchInsertOrUpdate(Collection<ChampSummonerSpells> champSummonerSpells) {
        champSummonerSpells.forEach((champSummonerSpell) -> {
            champSummonerSpell.combine(find(champSummonerSpell));
        });
        champSummonerSpellsDao.save(champSummonerSpells);
    }

    public ChampSummonerSpells find(ChampSummonerSpells champSummonerSpells) {
        return champSummonerSpellsDao.findOne(champSummonerSpells.getChampSummonerSpellsPK());
    }

}
