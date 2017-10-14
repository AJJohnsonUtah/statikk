/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.framework.dao;

import statikk.framework.entity.LolMatch;
import statikk.framework.entity.enums.MatchStatus;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author AJ
 */
@Repository
public class LolMatchDao extends BaseDao<LolMatch> {

    public void batchInsert(LolMatch[] lolMatches) {
        for (int i = 0; i < lolMatches.length; i++) {
            em.persist(lolMatches[i]);
            if (i % 100 == 0) {
                em.flush();
                em.clear();
            }
        }
    }

    public List<Long> findMatchesToAnalyze(int matchesToFind) {        
        Query query = em.createNamedQuery("LolMatch.findMatchesToAnalyze").setParameter("status", MatchStatus.READY);
        query.setMaxResults(matchesToFind);
        return query.getResultList();
    }
    
    public void markMatchesAsInProgress(List<Long> matchIds) {
        Query query = em.createNamedQuery("LolMatch.updateMatchListStatus").setParameter("status", MatchStatus.IN_PROGRESS).setParameter("matchIds", matchIds);
        query.executeUpdate();
    }
    
    public void markMatchesAsCompleted(List<Long> matchIds) {
        Query query = em.createNamedQuery("LolMatch.updateMatchListStatus").setParameter("status", MatchStatus.COMPLETED).setParameter("matchIds", matchIds);
        query.executeUpdate();
    }

}
