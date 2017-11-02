/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.service;

import java.util.HashMap;
import javax.transaction.Transactional;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import statikk.domain.dao.ChampSpecDao;
import statikk.domain.entity.ChampSpec;

/**
 *
 * @author AJ
 */
@Service
@Transactional
public class ChampSpecService extends BaseService<ChampSpec> {

    @Autowired
    private ChampSpecDao champSpecDao;

    @Autowired
    private LolVersionService lolVersionService;

    private final HashMap<ChampSpec, ChampSpec> cachedChampSpecs = new HashMap<>();

    public ChampSpec find(ChampSpec champSpec) {
        if (champSpec.getLolVersion().getLolVersionId() == null) {
            champSpec.setLolVersion(lolVersionService.find(champSpec.getLolVersion()));
        }
        return champSpecDao.findByChampionIdAndMatchTypeAndLolVersionAndLaneAndRoleAndRank(
                champSpec.getChampionId(), champSpec.getMatchType(), champSpec.getLolVersion(), champSpec.getLane(), champSpec.getRole(), champSpec.getRank());
    }

    public ChampSpec findOrCreate(ChampSpec champSpec) {
        Logger.getLogger(ChampSpecService.class).info("Finding champ spec to find/create " + champSpec);
        ChampSpec foundInstance = find(champSpec);
        if (foundInstance != null) {
            return foundInstance;
        }
        champSpec.setLolVersion(lolVersionService.findOrCreate(champSpec.getLolVersion()));
        Logger.getLogger(ChampSpecService.class).info("Creating champ spec to find/create " + champSpec);
        return create(champSpec);
    }

    @Override
    public ChampSpec create(ChampSpec champSpec) {
        return champSpecDao.save(champSpec);
    }

    @Override
    public ChampSpec update(ChampSpec champSpec) {
        return champSpecDao.save(champSpec);
    }
}
