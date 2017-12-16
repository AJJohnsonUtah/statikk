/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import statikk.domain.entity.ChampFinalBuild;
import statikk.domain.entity.ChampFinalBuildPK;

/**
 *
 * @author AJ
 */
@Service
@Transactional
public class ChampFinalBuildService extends BaseWinRateService<ChampFinalBuild, ChampFinalBuildPK> {

    @Override
    public ChampFinalBuild find(ChampFinalBuild champFinalBuild) {
        return dao.findOne(champFinalBuild.getChampFinalBuildPK());
    }

}
