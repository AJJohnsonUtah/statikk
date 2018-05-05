/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import statikk.domain.entity.FinalBuildOrder;

/**
 *
 * @author AJ
 */
public interface FinalBuildOrderDao extends CrudRepository<FinalBuildOrder, Long> {

    @Query(value = "SELECT f FROM FinalBuildOrder f WHERE f.buildOrder = ?1")
    public FinalBuildOrder findByBuildOrder(String buildOrder);

}
