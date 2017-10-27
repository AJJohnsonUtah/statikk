/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.service;

import java.util.HashMap;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import statikk.domain.dao.ChampBanDao;
import statikk.domain.entity.ChampBan;
import statikk.domain.entity.ChampBanPK;

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
    public void create(ChampBan champBan) {
        champBanDao.create(champBan);
    }

    @Override
    public void update(ChampBan champBan) {
        champBanDao.update(champBan);
    }

    public void batchInsertOrUpdate(HashMap<ChampBanPK, ChampBan> champBans) {
        champBanDao.batchInsertOrUpdate(champBans);
    }
}
