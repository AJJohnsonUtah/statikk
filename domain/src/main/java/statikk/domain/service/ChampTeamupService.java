/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import statikk.domain.entity.ChampTeamup;
import statikk.domain.entity.ChampTeamupPK;

/**
 *
 * @author AJ
 */
@Service
@Transactional
public class ChampTeamupService extends BaseWinRateService<ChampTeamup, ChampTeamupPK> {

    @Override
    public ChampTeamup find(ChampTeamup champTeamup) {
        return dao.findOne(champTeamup.getChampTeamupPK());
    }

}
