/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.dao;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import statikk.domain.entity.ChampSpec;
import statikk.domain.entity.LolVersion;
import statikk.domain.entity.enums.Lane;
import statikk.domain.entity.enums.Role;
import statikk.domain.riotapi.model.QueueType;
import statikk.domain.riotapi.model.Rank;
import statikk.domain.riotapi.model.Region;

/**
 *
 * @author AJ
 */
public interface ChampSpecDao extends CrudRepository<ChampSpec, Long> {

    @Query("SELECT c FROM ChampSpec c WHERE c.championId = ?1 AND c.matchType = ?2 AND c.lolVersion = ?3 AND c.lane = ?4 AND c.role = ?5 AND c.rank = ?6 AND c.region = ?7")
    public ChampSpec find(int championId,
            QueueType matchType, LolVersion lolVersion, Lane lane, Role role, Rank rank, Region region);

}
