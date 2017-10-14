/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.framework.dao;

import statikk.framework.entity.ChampBan;
import statikk.framework.entity.ChampBanPK;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 *
 * @author AJ
 */
@Repository
public class ChampBanDao extends BaseDao<ChampBan> {

    public ChampBan find(ChampBan entity) {
        return em.find(ChampBan.class, entity.getChampBanPK());
    }

    public void batchInsertOrUpdate(Map<ChampBanPK, ChampBan> champBans) {
        int counter = 0;
        for (ChampBan winRateEntity : champBans.values()) {
            ChampBan existingEntity = find(winRateEntity);
            if (existingEntity == null) {
                em.persist(winRateEntity);
            } else {
                existingEntity.combine(winRateEntity);
            }

            counter++;
            if (counter % 100 == 0) {
                em.flush();
                em.clear();
            }
        }
    }
}
