/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.dao;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import statikk.domain.entity.LolMatch;
import statikk.domain.entity.enums.MatchStatus;
import statikk.domain.riotapi.model.Region;

/**
 *
 * @author AJ
 */
public interface LolMatchDao extends CrudRepository<LolMatch, Long> {

    @Query("SELECT m FROM LolMatch m WHERE m.status = ?1 AND m.region = ?2 LIMIT ?3")
    public List<LolMatch> findTopLimitByStatusAndRegion(MatchStatus status, Region region, int limit);

    @Query("SELECT m FROM LolMatch m WHERE m.matchId = ?1 AND m.region = ?2")
    public LolMatch find(long matchId, Region region);

}
