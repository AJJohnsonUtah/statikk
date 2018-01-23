/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import statikk.domain.entity.ChampSpecWinRate;
import statikk.domain.entity.ChampSpecWinRatePK;

/**
 *
 * @author AJ
 */
@Service
@Transactional
public class ChampSpecWinRateService extends BaseWinRateService<ChampSpecWinRate, ChampSpecWinRatePK> {

    @Override
    public ChampSpecWinRate find(ChampSpecWinRate champSpecWinRate) {
        return dao.findOne(champSpecWinRate.getChampSpecWinRatePK());
    }        
}
