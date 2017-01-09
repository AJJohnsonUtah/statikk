/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.dao;

import com.njin.loltheory.entity.ChampSpecWinRate;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.stereotype.Repository;

/**
 *
 * @author AJ
 */
@Repository
public class ChampSpecWinRateDao extends BaseDao<ChampSpecWinRate> {

    public ChampSpecWinRate find(ChampSpecWinRate champSpecWinRate) {
        return em.find(ChampSpecWinRate.class, champSpecWinRate);
    }

    public void batchUpdateOrInsert(Map<ChampSpecWinRate, ChampSpecWinRate> champSpecWinRates) {
        int counter = 0;
        for (Entry<ChampSpecWinRate, ChampSpecWinRate> entry : champSpecWinRates.entrySet()) {
            ChampSpecWinRate existing = find(entry.getValue());
            if (existing == null) {
                em.persist(entry.getValue());
            } else {
                existing.combine(entry.getValue());
            }

            counter++;
            if (counter % 100 == 0) {
                em.flush();
                em.clear();
            }
        }
    }

}
