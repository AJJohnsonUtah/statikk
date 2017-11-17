/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.dao;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import statikk.domain.entity.ChampSpecWinRate;
import statikk.domain.entity.ChampSpecWinRatePK;
import statikk.domain.riotapi.model.QueueType;
import statikk.domain.stats.model.ChampionWinRate;

/**
 *
 * @author AJ
 */
public interface ChampSpecWinRateDao extends CrudRepository<ChampSpecWinRate, ChampSpecWinRatePK> {

    @Query("SELECT NEW statikk.domain.stats.model.ChampionWinRate(c.champSpecWinRatePK.champSpec.championId, SUM(c.playedCount), SUM(c.winCount)) FROM ChampSpecWinRate c WHERE c.champSpecWinRatePK.champSpec.matchType = ?1 GROUP BY c.champSpecWinRatePK.champSpec.championId")
    public List<ChampionWinRate> findWinCountAndPlayedCountGroupedByChampionId(QueueType queueType);
}
