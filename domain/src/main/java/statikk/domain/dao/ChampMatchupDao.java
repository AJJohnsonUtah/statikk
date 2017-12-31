/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.dao;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import statikk.domain.entity.ChampMatchup;
import statikk.domain.entity.ChampMatchupPK;
import statikk.domain.entity.LolVersion;
import statikk.domain.riotapi.model.QueueType;
import statikk.domain.stats.model.WinRateByChampion;

/**
 *
 * @author AJ
 */
public interface ChampMatchupDao extends CrudRepository<ChampMatchup, ChampMatchupPK> {

    @Query("SELECT NEW statikk.domain.stats.model.WinRateByChampion(c.champMatchupPK.champSpecB.championId, SUM(c.playedCount), sum(c.winCount)) FROM ChampMatchup c WHERE c.champMatchupPK.champSpecA.championId = ?1 AND c.champMatchupPK.champSpecA.matchType IN (?2) AND c.champMatchupPK.champSpecA.lolVersion IN (?3) GROUP BY c.champMatchupPK.champSpecB.championId")
    public List<WinRateByChampion> findWinRatesByGroupedByEnemyChampion(Integer championId, Iterable<QueueType> matchTypes, Iterable<LolVersion> lolVersions);

}
