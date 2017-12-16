/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.service;

import java.util.Collection;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import statikk.domain.dao.ChampSummonerSpellsDao;
import statikk.domain.entity.ChampSummonerSpells;
import statikk.domain.entity.ChampSummonerSpellsPK;

/**
 *
 * @author AJ
 */
@Service
@Transactional
public class ChampSummonerSpellsService extends BaseWinRateService<ChampSummonerSpells, ChampSummonerSpellsPK> {

    @Override
    public ChampSummonerSpells find(ChampSummonerSpells champSummonerSpells) {
        return dao.findOne(champSummonerSpells.getChampSummonerSpellsPK());
    }

}
