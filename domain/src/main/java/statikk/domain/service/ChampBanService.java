/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.service;

import java.util.Collection;
import javax.transaction.Transactional;
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
        return champBanDao.save(champBan);
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
}
