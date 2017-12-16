/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.service;

import java.util.Collection;
import java.util.logging.Level;
import javax.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;
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

    public void batchInsertOrUpdate(Collection<TeamComp> teamComps) {
        teamComps.forEach((teamComp) -> {
            TeamComp foundTeamComp = find(teamComp);
            if (foundTeamComp == null) {
                teamCompDao.save(teamComp);
            } else {
                foundTeamComp.combine(teamComp);
            }
        });
    }

    public TeamComp find(TeamComp teamComp) {
        return teamCompDao.findOne(teamComp.getTeamCompPK());
    }

}
