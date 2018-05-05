/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.riotapi.service;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import statikk.domain.riotapi.dao.DataCacheDao;
import statikk.domain.riotapi.model.DataCacheItem;

/**
 *
 * @author Grann
 */
@Service
@Transactional
public class DataCacheService {
    
    @Autowired
    DataCacheDao dataCacheDao;
    
    public DataCacheItem find(String id) {
        return dataCacheDao.findOne(id);
    }
    
    public void save(String id, String value) {
        dataCacheDao.save(new DataCacheItem(id, value));
    }
}
