/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.service;

import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import statikk.domain.dao.ChampSpecWinRateDao;
import statikk.domain.entity.ChampSpecWinRate;
import statikk.domain.entity.ChampSpecWinRatePK;

/**
 *
 * @author AJ
 */
@Service
@Transactional
public class ChampSpecWinRateService extends BaseService<ChampSpecWinRate> {

    @Autowired
    ChampSpecWinRateDao champSpecWinRateDao;

    @Override
    public void create(ChampSpecWinRate champSpecWinRate) {
        champSpecWinRateDao.create(champSpecWinRate);
    }

    @Override
    public void update(ChampSpecWinRate champSpecWinRate) {
        champSpecWinRateDao.update(champSpecWinRate);
    }
    
    public void batchInsertOrUpdate(Map<ChampSpecWinRatePK, ChampSpecWinRate> champSpecWinRates) {
        champSpecWinRateDao.batchInsertOrUpdate(champSpecWinRates);
    }   
}
