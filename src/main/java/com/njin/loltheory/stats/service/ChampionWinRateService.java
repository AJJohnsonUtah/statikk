/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.stats.service;

import com.njin.loltheory.dao.ChampSpecWinRateDao;
import com.njin.loltheory.stats.model.ChampionWinRate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author AJ
 */
@Service
public class ChampionWinRateService {

    @Autowired
    ChampSpecWinRateDao champSpecWinRateDao;

    public List<ChampionWinRate> getChampionWinRates() {
        return champSpecWinRateDao.findAllGrouped();
    }
}
