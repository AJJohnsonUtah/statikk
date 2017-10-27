/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.service;

import java.util.HashMap;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import statikk.domain.dao.LolVersionDao;
import statikk.domain.entity.LolVersion;

/**
 *
 * @author AJ
 */
@Service
@Transactional
public class LolVersionService extends BaseService<LolVersion> {

    @Autowired
    LolVersionDao lolVersionDao;

    private final HashMap<LolVersion, LolVersion> cachedLolVersions = new HashMap<>();

    @Override
    public void create(LolVersion lolVersion) {
        lolVersionDao.create(lolVersion);
    }

    @Override
    public void update(LolVersion lolVersion) {
        lolVersionDao.update(lolVersion);
    }

    public LolVersion find(LolVersion lolVersion) {
        return lolVersionDao.find(lolVersion);
    }

    public LolVersion loadEntity(LolVersion lolVersion) {
        if (cachedLolVersions.containsKey(lolVersion)) {
            return cachedLolVersions.get(lolVersion);
        }
        LolVersion foundVersion = find(lolVersion);
        if(foundVersion == null) {
            create(lolVersion);            
            cachedLolVersions.put(lolVersion, lolVersion);
            return lolVersion;
        }
        return foundVersion;
    }

    public List<LolVersion> findAll() {
        return lolVersionDao.findAll();
    }
}
