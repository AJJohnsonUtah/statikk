/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.framework.dao;

import statikk.framework.entity.ChampSpecWinRate;
import statikk.framework.entity.ChampSpecWinRatePK;
import statikk.framework.stats.model.ChampionWinRate;
import java.util.List;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

/**
 *
 * @author AJ
 */
@Repository
public class ChampSpecWinRateDao extends BaseWinRateEntityDao<ChampSpecWinRate, ChampSpecWinRatePK> {

    @Override
    public ChampSpecWinRate find(ChampSpecWinRate champSpecWinRate) {
        return em.find(ChampSpecWinRate.class, champSpecWinRate.getChampSpecWinRatePK());
    }

    public List<ChampionWinRate> findAllGrouped() {
        TypedQuery<ChampionWinRate> nq = em.createNamedQuery("ChampSpecWinRate.findAllGrouped", ChampionWinRate.class);                
        return nq.getResultList();
    }
}
