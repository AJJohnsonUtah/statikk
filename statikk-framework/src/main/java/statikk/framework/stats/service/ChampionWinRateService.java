/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.framework.stats.service;

import statikk.framework.dao.ChampSpecWinRateDao;
import statikk.framework.stats.model.ChampionWinRate;
import java.util.Map;
import java.util.stream.Collectors;
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

    public Map<Long, ChampionWinRate> getChampionWinRates() {
        return champSpecWinRateDao.findAllGrouped().stream().collect(Collectors.toMap(ChampionWinRate::getChampionId, c -> c));
    }
}
