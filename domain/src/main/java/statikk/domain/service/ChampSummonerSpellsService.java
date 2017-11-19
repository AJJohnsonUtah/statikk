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

    @Override
    public ChampSummonerSpells create(ChampSummonerSpells champSummonerSpells) {
        try {
            return champSummonerSpellsDao.save(champSummonerSpells);
        } catch (ConstraintViolationException e) {
            // This record has already been created; return the existing record.
            return find(champSummonerSpells);
        }
    }

    @Override
    public ChampSummonerSpells update(ChampSummonerSpells champSummonerSpells) {
        return champSummonerSpellsDao.save(champSummonerSpells);
    }

    public void batchInsertOrUpdate(Collection<ChampSummonerSpells> champSummonerSpells) {
        champSummonerSpells.forEach((champSummonerSpell) -> {
            champSummonerSpell.combine(champSummonerSpellsDao.findOne(champSummonerSpell.getChampSummonerSpellsPK()));
        });
        champSummonerSpellsDao.save(champSummonerSpells);
    }

    @Override
    public ChampSummonerSpells find(ChampSummonerSpells champSummonerSpells) {
        return champSummonerSpellsDao.findOne(champSummonerSpells.getChampSummonerSpellsPK());
    }

}
