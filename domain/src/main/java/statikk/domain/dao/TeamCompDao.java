/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import statikk.domain.entity.TeamComp;
import statikk.domain.entity.TeamCompPK;
import statikk.domain.stats.model.BaseWinRate;

/**
 *
 * @author Grann
 */
public interface TeamCompDao extends CrudRepository<TeamComp, TeamCompPK> {

    @Query("SELECT new statikk.domain.stats.model.BaseWinRate(SUM(t.winCount), SUM(t.playedCount)) FROM TeamComp t WHERE "
            + "SUBSTRING(t.teamCompPK.allyTeamComp, ?1, 1) >= ?2 "
            + "AND SUBSTRING(t.teamCompPK.allyTeamComp, ?3, 1) >= ?4 "
            + "AND SUBSTRING(t.teamCompPK.allyTeamComp, ?5, 1) >= ?6 "
            + "AND SUBSTRING(t.teamCompPK.allyTeamComp, ?7, 1) >= ?8 "
            + "AND SUBSTRING(t.teamCompPK.allyTeamComp, ?9, 1) >= ?10 "
            + "AND SUBSTRING(t.teamCompPK.enemyTeamComp, ?11, 1) >= ?12 "
            + "AND SUBSTRING(t.teamCompPK.enemyTeamComp, ?13, 1) >= ?14 "
            + "AND SUBSTRING(t.teamCompPK.enemyTeamComp, ?15, 1) >= ?16 "
            + "AND SUBSTRING(t.teamCompPK.enemyTeamComp, ?17, 1) >= ?18 "
            + "AND SUBSTRING(t.teamCompPK.enemyTeamComp, ?19, 1) >= ?20 ")
    public BaseWinRate findTeamCompsWithMatchingRoles(
            int allyRole1, String allyRole1Count,
            int allyRole2, String allyRole2Count,
            int allyRole3, String allyRole3Count,
            int allyRole4, String allyRole4Count,
            int allyRole5, String allyRole5Count,
            int enemyRole1, String enemyRole1Count,
            int enemyRole2, String enemyRole2Count,
            int enemyRole3, String enemyRole3Count,
            int enemyRole4, String enemyRole4Count,
            int enemyRole5, String enemyRole15Count
    );

}
