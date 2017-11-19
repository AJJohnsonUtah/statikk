/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.stats.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import statikk.domain.dao.ChampSpecWinRateDao;
import statikk.domain.riotapi.model.QueueType;
import statikk.domain.stats.model.ChampionWinRate;

/**
 *
 * @author AJ
 */
@Service
public class ChampionWinRateService {

    @Autowired
    ChampSpecWinRateDao champSpecWinRateDao;

    public Map<Long, ChampionWinRate> getChampionWinRatesById(QueueType queueType) {
        return champSpecWinRateDao.findWinCountAndPlayedCountGroupedByChampionId(queueType).stream().collect(Collectors.toMap(ChampionWinRate::getChampionId, c -> c));
    }

    public List<ChampionWinRate> getChampionWinRates(QueueType queueType) {
        return champSpecWinRateDao.findWinCountAndPlayedCountGroupedByChampionId(queueType);
    }
}
