/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import statikk.domain.dao.LolSummonerDao;
import statikk.domain.entity.LolSummoner;
import statikk.domain.riotapi.model.Region;

/**
 *
 * @author Grann
 */
@Service
@Transactional
public class LolSummonerService {

    @Autowired
    LolSummonerDao lolSummonerDao;

    @Transactional
    private LolSummoner create(LolSummoner lolSummoner) {
        try {
            return lolSummonerDao.save(lolSummoner);
        } catch (ConstraintViolationException e) {
            // This record has already been created; return the existing record.
            return lolSummonerDao.findOne(lolSummoner.getAccountId());
        }
    }

    @Transactional
    public LolSummoner findOrCreate(LolSummoner lolSummoner) {
        LolSummoner foundInstance = lolSummonerDao.findOne(lolSummoner.getAccountId());
        if (foundInstance != null) {
            return foundInstance;
        }
        return create(lolSummoner);
    }

    public List<LolSummoner> getRecentSummonersToMine(Region region) {
        Date oneWeekAgo = new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 24L * 7L);
        return lolSummonerDao.findTop10ByRegionAndLastPlayedDateLessThanOrderByLastMinedDate(region, oneWeekAgo);
    }

    public void addOrUpdate(List<LolSummoner> summonersFromMatches) {
        lolSummonerDao.save(summonersFromMatches);
    }

}
