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
import statikk.domain.dao.ChampBanDao;
import statikk.domain.entity.ChampBan;

/**
 *
 * @author AJ
 */
@Service
@Transactional
public class ChampBanService extends BaseService<ChampBan> {

    @Autowired
    ChampBanDao champBanDao;

    @Override
    public ChampBan create(ChampBan champBan) {
        try {
            return champBanDao.save(champBan);
        } catch (ConstraintViolationException e) {
            // This record has already been created; return the existing record.
            return find(champBan);
        }
    }

    @Override
    public ChampBan update(ChampBan champBan) {
        return champBanDao.save(champBan);
    }

    public void batchInsertOrUpdate(Collection<ChampBan> champBans) {
        champBans.forEach((champBan) -> {
            champBan.combine(champBanDao.findOne(champBan.getChampBanPK()));
        });
        champBanDao.save(champBans);
    }

    @Override
    public ChampBan find(ChampBan champBan) {
        return champBanDao.findOne(champBan.getChampBanPK());
    }
}
