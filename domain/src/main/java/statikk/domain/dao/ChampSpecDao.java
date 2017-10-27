/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.dao;

import java.util.List;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import statikk.domain.entity.ChampSpec;

/**
 *
 * @author AJ
 */
@Repository
public class ChampSpecDao extends BaseDao<ChampSpec> {

    public ChampSpec find(ChampSpec champSpec) {
        TypedQuery<ChampSpec> nq = em.createNamedQuery("ChampSpec.find", ChampSpec.class)
                .setParameter("championId", champSpec.getChampionId())
                .setParameter("matchType", champSpec.getMatchType())
                .setParameter("lolVersionId", champSpec.getLolVersionId())
                .setParameter("lane", champSpec.getLane())
                .setParameter("role", champSpec.getRole())
                .setParameter("rank", champSpec.getRank());
        List<ChampSpec> champSpecs = nq.getResultList();
        return champSpecs.isEmpty() ? null : champSpecs.get(0);
    }

}
