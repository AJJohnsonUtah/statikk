/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.dao;

import com.njin.loltheory.entity.LolVersion;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author AJ
 */
@Repository
public class LolVersionDao extends BaseDao<LolVersion> {

    public List<LolVersion> findAll() {
        Query nq = em.createNamedQuery("LolVersion.findAll");
        return nq.getResultList();
    }
}
