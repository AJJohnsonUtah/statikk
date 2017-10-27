/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.service;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import statikk.domain.dao.LolMatchDao;
import statikk.domain.entity.LolMatch;

/**
 *
 * @author AJ
 */
@Service
@Transactional
public class LolMatchService extends BaseService<LolMatch> {

    @Autowired
    LolMatchDao lolMatchDao;

    @Override
    public void create(LolMatch lolMatch) {
        lolMatchDao.create(lolMatch);
    }

    @Override
    public void update(LolMatch lolMatch) {
        lolMatchDao.update(lolMatch);
    }

    public void batchInsert(LolMatch[] lolMatches) {
        lolMatchDao.batchInsert(lolMatches);
    }

    public List<Long> findMatchesToAnalyze(int matchesToFind) {
        List<Long> matchesFound = lolMatchDao.findMatchesToAnalyze(matchesToFind);
        if (!matchesFound.isEmpty()) {
            lolMatchDao.markMatchesAsInProgress(matchesFound);
        }
        return matchesFound;
    }

    public void markMatchesAsCompleted(List<Long> matches) {
        lolMatchDao.markMatchesAsCompleted(matches);
    }
}
