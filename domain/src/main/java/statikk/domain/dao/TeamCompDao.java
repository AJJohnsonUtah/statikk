/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.dao;

import java.util.Map;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import statikk.domain.entity.LolVersion;
import statikk.domain.entity.TeamComp;
import statikk.domain.entity.enums.Role;
import statikk.domain.riotapi.model.QueueType;

/**
 *
 * @author Grann
 */
public interface TeamCompDao extends CrudRepository<TeamComp, Long> {

    @Query("SELECT t FROM TeamComp t WHERE t.allyTeamComp = ?1 AND t.enemyTeamComp = ?2 AND t.matchType = ?3 AND t.lolVersion = ?4")
    TeamComp find(Map<Role, Integer> allyTeamComp, Map<Role, Integer> enemyTeamComp,
            QueueType matchType, LolVersion lolVersion);

}
