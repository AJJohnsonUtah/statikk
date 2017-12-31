/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.dao;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import statikk.domain.entity.ChampTeamup;
import statikk.domain.entity.ChampTeamupPK;
import statikk.domain.entity.LolVersion;
import statikk.domain.riotapi.model.QueueType;
import statikk.domain.stats.model.WinRateByChampion;

/**
 *
 * @author AJ
 */
public interface ChampTeamupDao extends CrudRepository<ChampTeamup, ChampTeamupPK> {

    @Query("SELECT NEW statikk.domain.stats.model.WinRateByChampion(c.champTeamupPK.champSpecB.championId, SUM(c.playedCount), sum(c.winCount)) FROM ChampTeamup c WHERE c.champTeamupPK.champSpecA.championId = ?1 AND c.champTeamupPK.champSpecA.matchType IN (?2) AND c.champTeamupPK.champSpecA.lolVersion IN (?3) GROUP BY c.champTeamupPK.champSpecB.championId")
    public List<WinRateByChampion> findWinRatesByGroupedByAllyChampion(Integer championId, Iterable<QueueType> matchTypes, Iterable<LolVersion> lolVersions);

}
