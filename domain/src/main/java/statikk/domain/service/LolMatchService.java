/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import statikk.domain.dao.LolMatchDao;
import statikk.domain.entity.LolMatch;
import statikk.domain.entity.enums.MatchStatus;
import statikk.domain.riotapi.model.Region;

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
        try {
            return lolMatchDao.save(lolMatch);
        } catch (ConstraintViolationException e) {
            // This record has already been created; return the existing record.
            return find(lolMatch);
        }
    }

    @Override
    public LolMatch update(LolMatch lolMatch) {
        return lolMatchDao.save(lolMatch);
    }

    /**
     * Inserts any matches that did not already exist in the database
     *
     * @param lolMatches
     * @return The number of added matches.
     */
    public int batchInsert(Collection<LolMatch> lolMatches) {
        for (Iterator<LolMatch> iter = lolMatches.iterator(); iter.hasNext();) {
            LolMatch match = iter.next();
            if (lolMatchDao.find(match.getMatchId(), match.getRegion()) != null) {
                iter.remove();
            }
        }

        try {
            lolMatchDao.save(lolMatches);
        } catch (ConstraintViolationException e) {
            Logger.getLogger(LolMatchService.class.getName())
                    .log(Level.INFO, "Match ids must have been added by another client; will try again!");
            return batchInsert(lolMatches);
        }
        return lolMatches.size();
    }

    public List<LolMatch> findMatchesToAnalyzeByRegion(int matchesToFind, Region region, int limit) {
        List<LolMatch> matchesFound = lolMatchDao.findTopLimitByStatusAndRegion(MatchStatus.READY, region, limit);
        updateMatchesToStatus(MatchStatus.IN_PROGRESS, matchesFound);
        return matchesFound;
    }

    private void updateMatchesToStatus(MatchStatus status, List<LolMatch> matches) {
        matches.forEach((match) -> {
            match.setStatus(status);
        });
        if (!matches.isEmpty()) {
            lolMatchDao.save(matches);
        }
    }

    public void update(Iterable<LolMatch> matches) {
        lolMatchDao.save(matches);
    }

    @Override
    public LolMatch find(LolMatch lolMatch) {
        return lolMatchDao.find(lolMatch.getMatchId(), lolMatch.getRegion());
    }
}
