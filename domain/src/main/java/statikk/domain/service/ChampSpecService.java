/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.service;

import java.util.HashMap;
import javax.transaction.Transactional;
import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
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
public class ChampSpecService {

    @Autowired
    private ChampSpecDao champSpecDao;

    @Autowired
    private LolVersionService lolVersionService;

    private final HashMap<ChampSpec, ChampSpec> cachedChampSpecs = new HashMap<>();

    public ChampSpec find(ChampSpec champSpec) {
        if (champSpec.getLolVersion().getLolVersionId() == null) {
            champSpec.setLolVersion(lolVersionService.find(champSpec.getLolVersion()));
        }
        return champSpecDao.find(
                champSpec.getChampionId(),
                champSpec.getMatchType(),
                champSpec.getLolVersion(),
                champSpec.getLane(),
                champSpec.getRole(),
                champSpec.getRank(),
                champSpec.getRegion()
        );
    }

    public ChampSpec findOrCreate(ChampSpec champSpec) {
        ChampSpec foundInstance = find(champSpec);
        if (foundInstance != null) {
            return foundInstance;
        }
        champSpec.setLolVersion(lolVersionService.findOrCreate(champSpec.getLolVersion()));
        return create(champSpec);
    }

    public ChampSpec create(ChampSpec champSpec) {
        try {
            return champSpecDao.save(champSpec);
        } catch (ConstraintViolationException e) {
            // This record has already been created; return the existing record.
            return find(champSpec);
        }
    }
}
