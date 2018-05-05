/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import statikk.domain.dao.TeamCompDao;
import statikk.domain.entity.TeamComp;
import statikk.domain.entity.TeamCompPK;
import statikk.domain.entity.enums.Lane;
import statikk.domain.entity.enums.Role;
import statikk.domain.stats.model.BaseWinRate;

/**
 *
 * @author Grann
 */
@Service
@Transactional
public class TeamCompService extends BaseWinRateService<TeamComp, TeamCompPK> {

    @Autowired
    TeamCompDao teamCompDao;

    @Override
    public TeamComp find(TeamComp teamComp) {
        return dao.findOne(teamComp.getTeamCompPK());
    }

    public Map<Role, BaseWinRate> findWinRatesByRoleForLaneWithComps(Lane lane, HashMap<Role, Integer> allyRoleCounts, HashMap<Role, Integer> enemyRoleCounts) {
        Map<Role, BaseWinRate> winRates = new HashMap<>();
        Stream.of(Role.values()).filter((role) -> role.isPlayable()).forEach((role) -> {
            HashMap<Role, Integer> potentialAllyRoleCounts = (HashMap<Role, Integer>) allyRoleCounts.clone();
            if (potentialAllyRoleCounts.containsKey(role)) {
                potentialAllyRoleCounts.put(role, potentialAllyRoleCounts.get(role) + 1);
            } else {
                potentialAllyRoleCounts.put(role, 1);
            }
            List<RoleCount> roleCounts = getRoleCountsFromPartialTeamComps(potentialAllyRoleCounts, enemyRoleCounts);
            BaseWinRate winRate = teamCompDao.findTeamCompsWithMatchingRoles(
                    roleCounts.get(0).roleId, roleCounts.get(0).roleCount,
                    roleCounts.get(1).roleId, roleCounts.get(1).roleCount,
                    roleCounts.get(2).roleId, roleCounts.get(2).roleCount,
                    roleCounts.get(3).roleId, roleCounts.get(3).roleCount,
                    roleCounts.get(4).roleId, roleCounts.get(4).roleCount,
                    roleCounts.get(5).roleId, roleCounts.get(5).roleCount,
                    roleCounts.get(6).roleId, roleCounts.get(6).roleCount,
                    roleCounts.get(7).roleId, roleCounts.get(7).roleCount,
                    roleCounts.get(8).roleId, roleCounts.get(8).roleCount,
                    roleCounts.get(9).roleId, roleCounts.get(9).roleCount,
                    role, lane
            );
            winRates.put(role, winRate);
        });

        return winRates;
    }

    private List<RoleCount> getRoleCountsFromPartialTeamComps(HashMap<Role, Integer> allyRoleCounts, HashMap<Role, Integer> enemyRoleCounts) {
        ArrayList<RoleCount> roleCounts = new ArrayList<>();
        allyRoleCounts.keySet().forEach((role) -> {
            RoleCount roleCount = new RoleCount();
            roleCount.roleId = role.getRoleId();
            roleCount.roleCount = allyRoleCounts.get(role).toString();
            roleCounts.add(roleCount);
        });
        while (roleCounts.size() < 5) {
            roleCounts.add(roleCounts.get(roleCounts.size() - 1));
        }
        enemyRoleCounts.keySet().forEach((role) -> {
            RoleCount roleCount = new RoleCount();
            roleCount.roleId = role.getRoleId();
            roleCount.roleCount = enemyRoleCounts.get(role).toString();
            roleCounts.add(roleCount);
        });
        while (roleCounts.size() < 10) {
            roleCounts.add(roleCounts.get(roleCounts.size() - 1));
        }

        return roleCounts;
    }

    class RoleCount {
        int roleId;
        String roleCount;
    }

}
