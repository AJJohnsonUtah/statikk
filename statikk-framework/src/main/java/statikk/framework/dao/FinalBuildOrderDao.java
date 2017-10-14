/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.framework.dao;

import statikk.framework.entity.ChampSpec;
import statikk.framework.entity.FinalBuildOrder;
import java.util.List;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

/**
 *
 * @author AJ
 */
@Repository
public class FinalBuildOrderDao extends BaseDao<FinalBuildOrder> {

    public FinalBuildOrder find(FinalBuildOrder finalBuildOrder) {
        TypedQuery<FinalBuildOrder> nq = em.createNamedQuery("FinalBuildOrder.findByBuildOrder", FinalBuildOrder.class)
                .setParameter("buildOrder", finalBuildOrder.getBuildOrder());
        List<FinalBuildOrder> finalBuildOrders = nq.getResultList();
        return finalBuildOrders.isEmpty() ? null : finalBuildOrders.get(0);
    }

}
