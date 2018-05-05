/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.service;

import java.io.Serializable;
import java.util.Collection;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import statikk.domain.entity.BaseWinRateEntity;

/**
 *
 * @author AJ
 */
@Transactional
public abstract class BaseWinRateService<WinRateEntity extends BaseWinRateEntity, EntityId extends Serializable> {

    @Autowired
    protected CrudRepository<WinRateEntity, EntityId> dao;

    public void batchInsertOrUpdate(Collection<WinRateEntity> winRateEntities) {
        winRateEntities.forEach((winRateEntity) -> {
            WinRateEntity foundWinRateEntity = find(winRateEntity);
            if (foundWinRateEntity == null) {
                dao.save(winRateEntity);
            } else {
                foundWinRateEntity.combine(winRateEntity);
            }
        });
    }

    public abstract WinRateEntity find(WinRateEntity winRateEntity);
}
