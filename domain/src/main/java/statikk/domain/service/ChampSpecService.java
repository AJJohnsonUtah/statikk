/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.service;

import java.util.HashMap;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import statikk.domain.dao.ChampSpecDao;
import statikk.domain.entity.ChampSpec;
import statikk.domain.entity.LolVersion;

/**
 *
 * @author AJ
 */
@Service
@Transactional
public class ChampSpecService extends BaseService<ChampSpec> {

    @Autowired
    ChampSpecDao champSpecDao;

    private final HashMap<ChampSpec, ChampSpec> cachedChampSpecs = new HashMap<>();

    @Override
    public void create(ChampSpec champSpec) {
        champSpecDao.create(champSpec);
    }

    @Override
    public void update(ChampSpec champSpec) {
        champSpecDao.update(champSpec);
    }

    public ChampSpec find(ChampSpec champSpec) {
        return champSpecDao.find(champSpec);
    }

    public ChampSpec loadEntity(ChampSpec champSpec) {
        if (cachedChampSpecs.containsKey(champSpec)) {
            return cachedChampSpecs.get(champSpec);
        }
        LolVersion lolVersion = champSpec.getLolVersionId();
        ChampSpec foundSpec = find(champSpec);        
        if (foundSpec == null) {
            create(champSpec);
            champSpec.setLolVersionId(lolVersion);
            cachedChampSpecs.put(champSpec, champSpec);
            return champSpec;
        }
        foundSpec.setLolVersionId(lolVersion);
        return foundSpec;
    }
}
