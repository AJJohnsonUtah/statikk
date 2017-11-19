/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.service;

import java.util.Collection;
import javax.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import statikk.domain.dao.ChampSpecWinRateDao;
import statikk.domain.entity.ChampSpecWinRate;

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
    public ChampSpecWinRate create(ChampSpecWinRate champSpecWinRate) {
        try {
            return champSpecWinRateDao.save(champSpecWinRate);
        } catch (ConstraintViolationException e) {
            // This record has already been created; return the existing record.
            return find(champSpecWinRate);
        }
    }

    @Override
    public ChampSpecWinRate update(ChampSpecWinRate champSpecWinRate) {
        return champSpecWinRateDao.save(champSpecWinRate);
    }

    public void batchInsertOrUpdate(Collection<ChampSpecWinRate> champSpecWinRates) {
        champSpecWinRates.forEach((champSpecWinRate) -> {
            champSpecWinRate.combine(champSpecWinRateDao.findOne(champSpecWinRate.getChampSpecWinRatePK()));
        });
        champSpecWinRateDao.save(champSpecWinRates);
    }

    @Override
    public ChampSpecWinRate find(ChampSpecWinRate champSpecWinRate) {
        return champSpecWinRateDao.findOne(champSpecWinRate.getChampSpecWinRatePK());
    }
}
