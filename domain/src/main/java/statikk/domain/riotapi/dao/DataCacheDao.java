/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.riotapi.dao;

import org.springframework.data.repository.CrudRepository;
import statikk.domain.riotapi.model.DataCacheItem;

/**
 *
 * @author Grann
 */
public interface DataCacheDao extends CrudRepository<DataCacheItem, String> {

}
