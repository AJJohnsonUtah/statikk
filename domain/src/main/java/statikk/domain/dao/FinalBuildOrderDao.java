/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.dao;

import java.util.List;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import statikk.domain.entity.FinalBuildOrder;

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
