/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.service;

import java.util.Collection;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import statikk.domain.dao.TeamCompDao;
import statikk.domain.entity.TeamComp;
import statikk.domain.entity.TeamCompPK;

/**
 *
 * @author Grann
 */
@Service
@Transactional
public class TeamCompService extends BaseWinRateService<TeamComp, TeamCompPK> {

    @Override
    public TeamComp find(TeamComp teamComp) {
        return dao.findOne(teamComp.getTeamCompPK());
    }

}
