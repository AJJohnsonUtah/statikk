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
import statikk.domain.entity.LolVersion;
import statikk.domain.entity.enums.Lane;
import statikk.domain.entity.enums.Role;
import statikk.domain.riotapi.model.QueueType;
import statikk.domain.riotapi.model.Rank;
import statikk.domain.stats.model.WinRateByChampion;
import statikk.domain.stats.model.WinRateByChampionLane;
import statikk.domain.stats.model.WinRateByChampionRole;
import statikk.domain.stats.model.WinRateByMatchType;
import statikk.domain.stats.model.WinRateByLane;
import statikk.domain.stats.model.WinRateByRole;

/**
 *
 * @author AJ
 */
public interface ChampSpecWinRateDao extends CrudRepository<ChampSpecWinRate, ChampSpecWinRatePK> {

    @Query("SELECT NEW statikk.domain.stats.model.WinRateByChampion(c.champSpecWinRatePK.champSpec.championId, SUM(c.playedCount), SUM(c.winCount)) FROM ChampSpecWinRate c WHERE c.champSpecWinRatePK.champSpec.matchType = ?1 AND c.champSpecWinRatePK.champSpec.lolVersion = ?2 GROUP BY c.champSpecWinRatePK.champSpec.championId")
    public List<WinRateByChampion> findWinRatesGroupedByChampionId(QueueType queueType, LolVersion lolVersion);

    @Query("SELECT NEW statikk.domain.stats.model.WinRateByChampion(c.champSpecWinRatePK.champSpec.championId, SUM(c.playedCount), SUM(c.winCount)) FROM ChampSpecWinRate c WHERE c.champSpecWinRatePK.champSpec.matchType = ?1 AND c.champSpecWinRatePK.champSpec.lane = ?2 AND c.champSpecWinRatePK.champSpec.lolVersion = ?3 GROUP BY c.champSpecWinRatePK.champSpec.championId")
    public List<WinRateByChampion> findWinRatesGroupedByChampionId(QueueType queueType, Lane lane, LolVersion lolVersion);

    @Query("SELECT NEW statikk.domain.stats.model.WinRateByChampion(c.champSpecWinRatePK.champSpec.championId, SUM(c.playedCount), SUM(c.winCount)) FROM ChampSpecWinRate c WHERE c.champSpecWinRatePK.champSpec.matchType = ?1 AND c.champSpecWinRatePK.champSpec.rank = ?2 AND c.champSpecWinRatePK.champSpec.lolVersion = ?3 GROUP BY c.champSpecWinRatePK.champSpec.championId")
    public List<WinRateByChampion> findWinRatesGroupedByChampionId(QueueType queueType, Rank rank, LolVersion lolVersion);

    @Query("SELECT NEW statikk.domain.stats.model.WinRateByChampion(c.champSpecWinRatePK.champSpec.championId, SUM(c.playedCount), SUM(c.winCount)) FROM ChampSpecWinRate c WHERE c.champSpecWinRatePK.champSpec.matchType = ?1 AND c.champSpecWinRatePK.champSpec.rank = ?2 AND c.champSpecWinRatePK.champSpec.lane = ?3 AND c.champSpecWinRatePK.champSpec.lolVersion = ?4 GROUP BY c.champSpecWinRatePK.champSpec.championId")
    public List<WinRateByChampion> findWinRatesGroupedByChampionId(QueueType queueType, Rank rank, Lane lane, LolVersion lolVersion);

    @Query("SELECT NEW statikk.domain.stats.model.WinRateByMatchType(c.champSpecWinRatePK.champSpec.matchType, SUM(c.playedCount), SUM(c.winCount)) FROM ChampSpecWinRate c WHERE c.champSpecWinRatePK.champSpec.championId = ?1 AND c.champSpecWinRatePK.champSpec.lolVersion = ?2 GROUP BY c.champSpecWinRatePK.champSpec.matchType")
    public List<WinRateByMatchType> findChampionWinRatesGroupedByMatchType(int championId, LolVersion lolVersion);

    @Query("SELECT NEW statikk.domain.stats.model.WinRateByLane(c.champSpecWinRatePK.champSpec.lane, SUM(c.playedCount), SUM(c.winCount)) FROM ChampSpecWinRate c WHERE c.champSpecWinRatePK.champSpec.championId = ?1 AND c.champSpecWinRatePK.champSpec.matchType IN (?2) AND c.champSpecWinRatePK.champSpec.lolVersion = ?3 GROUP BY c.champSpecWinRatePK.champSpec.lane")
    public List<WinRateByLane> findChampionWinRatesGroupedByLane(int championId, Iterable<QueueType> matchTypes, LolVersion lolVersion);
    
    @Query("SELECT NEW statikk.domain.stats.model.WinRateByLane(c.champSpecWinRatePK.champSpec.lane, SUM(c.playedCount), SUM(c.winCount)) FROM ChampSpecWinRate c WHERE c.champSpecWinRatePK.champSpec.championId = ?1 AND  c.champSpecWinRatePK.champSpec.role = ?2 AND c.champSpecWinRatePK.champSpec.matchType IN (?3) AND c.champSpecWinRatePK.champSpec.lolVersion = ?4 GROUP BY c.champSpecWinRatePK.champSpec.lane")
    public List<WinRateByLane> findChampionWinRatesByRoleGroupedByLane(int championId, Role role, Iterable<QueueType> matchTypes, LolVersion lolVersion);
    
    @Query("SELECT NEW statikk.domain.stats.model.WinRateByRole(c.champSpecWinRatePK.champSpec.role, SUM(c.playedCount), SUM(c.winCount)) FROM ChampSpecWinRate c WHERE c.champSpecWinRatePK.champSpec.championId = ?1 AND c.champSpecWinRatePK.champSpec.matchType IN (?2) AND c.champSpecWinRatePK.champSpec.lolVersion IN (?3) GROUP BY c.champSpecWinRatePK.champSpec.role")
    public List<WinRateByRole> findChampionWinRatesGroupedByRole(int championId, Iterable<QueueType> matchTypes, List<LolVersion> lolVersions);
    
    @Query("SELECT NEW statikk.domain.stats.model.WinRateByRole(c.champSpecWinRatePK.champSpec.role, SUM(c.playedCount), SUM(c.winCount)) FROM ChampSpecWinRate c WHERE c.champSpecWinRatePK.champSpec.championId = ?1 AND  c.champSpecWinRatePK.champSpec.lane = ?2 AND c.champSpecWinRatePK.champSpec.matchType IN (?3) AND c.champSpecWinRatePK.champSpec.lolVersion = ?4 GROUP BY c.champSpecWinRatePK.champSpec.role")
    public List<WinRateByRole> findChampionWinRatesByLaneGroupedByRole(int championId, Lane lane, Iterable<QueueType> matchTypes, LolVersion lolVersion);
    
    @Query("SELECT NEW statikk.domain.stats.model.WinRateByChampionLane(c.champSpecWinRatePK.champSpec.championId, c.champSpecWinRatePK.champSpec.lane, SUM(c.playedCount), sum(c.winCount)) FROM ChampSpecWinRate c WHERE c.champSpecWinRatePK.champSpec.matchType IN (?1) AND c.champSpecWinRatePK.champSpec.lolVersion IN (?2) GROUP BY c.champSpecWinRatePK.champSpec.championId, c.champSpecWinRatePK.champSpec.lane")
    public List<WinRateByChampionLane> findWinRatesByGroupedByChampionLane(Iterable<QueueType> matchTypes, Iterable<LolVersion> lolVersions);
    
    @Query("SELECT NEW statikk.domain.stats.model.WinRateByChampionRole(c.champSpecWinRatePK.champSpec.championId, c.champSpecWinRatePK.champSpec.role, SUM(c.playedCount), sum(c.winCount)) FROM ChampSpecWinRate c WHERE c.champSpecWinRatePK.champSpec.matchType IN (?1) AND c.champSpecWinRatePK.champSpec.lolVersion IN (?2) GROUP BY c.champSpecWinRatePK.champSpec.championId, c.champSpecWinRatePK.champSpec.role")
    public List<WinRateByChampionRole> findWinRatesByGroupedByChampionRole(Iterable<QueueType> matchTypes, Iterable<LolVersion> lolVersions);
}
