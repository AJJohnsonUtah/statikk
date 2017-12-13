/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.service;

import java.util.Collection;
import java.util.HashMap;
import javax.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import statikk.domain.dao.TeamCompDao;
import statikk.domain.entity.TeamComp;

/**
 *
 * @author Grann
 */
@Service
@Transactional
public class TeamCompService extends BaseService<TeamComp> {

    @Autowired
    private TeamCompDao teamCompDao;

    @Autowired
    private LolVersionService lolVersionService;

    private final HashMap<TeamComp, TeamComp> cachedTeamComps = new HashMap<>();

    public TeamComp find(TeamComp teamComp) {
        if (teamComp.getLolVersion().getLolVersionId() == null) {
            teamComp.setLolVersion(lolVersionService.find(teamComp.getLolVersion()));
        }
        return teamCompDao.find(
                teamComp.getAllyTeamComp(),
                teamComp.getEnemyTeamComp(),
                teamComp.getMatchType(),
                teamComp.getLolVersion()
        );
    }

    public TeamComp findOrCreate(TeamComp teamComp) {
        TeamComp foundInstance = find(teamComp);
        if (foundInstance != null) {
            return foundInstance;
        }
        teamComp.setLolVersion(lolVersionService.findOrCreate(teamComp.getLolVersion()));
        return create(teamComp);
    }

    @Override
    public TeamComp create(TeamComp teamComp) {
        try {
            return teamCompDao.save(teamComp);
        } catch (ConstraintViolationException e) {
            // This record has already been created; return the existing record.
            return find(teamComp);
        }
    }

    @Override
    public TeamComp update(TeamComp teamComp) {
        return teamCompDao.save(teamComp);
    }

    public void batchInsertOrUpdate(Collection<TeamComp> teamComps) {
        System.out.println("Inserting " + teamComps.size() + " team comps");
        teamComps.forEach((teamComp) -> {
            teamComp.setLolVersion(lolVersionService.find(teamComp.getLolVersion()));
            try {
                TeamComp existingTeamComp = find(teamComp);
                if (existingTeamComp != null) {
                    System.out.println("COMBINING: " + teamComp);
                    teamComp.combine(existingTeamComp);
                }
                teamCompDao.save(teamComp);
            } catch (ConstraintViolationException e) {
                // This record has already been created; return the existing record.
                System.out.println(teamComp + " already exists! Trying to find it again!");
                TeamComp existingTeamComp = find(teamComp);
                if (existingTeamComp != null) {
                    teamComp.combine(existingTeamComp);
                } else {
                    System.out.println(teamComp + " we couldn't find it...?");
                }
                teamCompDao.save(teamComp);
            }
        });
    }
}
