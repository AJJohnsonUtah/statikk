/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.service;

import com.njin.loltheory.dao.ChampSpecWinRateDao;
import com.njin.loltheory.entity.ChampSpec;
import com.njin.loltheory.entity.ChampSpecWinRate;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    
    public void batchUpdateOrInsert(Map<ChampSpec, ChampSpecWinRate> champSpecWinRates) {
        champSpecWinRateDao.batchUpdateOrInsert(champSpecWinRates);
    }
}
