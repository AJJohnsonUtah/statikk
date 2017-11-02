/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.service;

import java.util.Collection;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import statikk.domain.dao.LolMatchDao;
import statikk.domain.entity.LolMatch;
import statikk.domain.entity.enums.MatchStatus;

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
    public LolMatch create(LolMatch lolMatch) {
        return lolMatchDao.save(lolMatch);
    }

    @Override
    public LolMatch update(LolMatch lolMatch) {
        return lolMatchDao.save(lolMatch);
    }

    public void batchInsert(Collection<LolMatch> lolMatches) {
        lolMatchDao.save(lolMatches);
    }

    public List<LolMatch> findMatchesToAnalyze(int matchesToFind) {
        List<LolMatch> matchesFound = lolMatchDao.findTop10ByStatus(MatchStatus.READY);
        updateMatchesToStatus(MatchStatus.IN_PROGRESS, matchesFound);
        return matchesFound;
    }

    public void markMatchesAsCompleted(List<LolMatch> matches) {
        updateMatchesToStatus(MatchStatus.COMPLETED, matches);
    }

    private void updateMatchesToStatus(MatchStatus status, List<LolMatch> matches) {
        matches.forEach((match) -> {
            match.setStatus(status);
        });
        if (!matches.isEmpty()) {
            lolMatchDao.save(matches);
        }
    }
}
