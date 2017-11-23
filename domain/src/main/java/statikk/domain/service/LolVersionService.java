/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
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
    private LolVersionDao lolVersionDao;

    private final HashMap<LolVersion, LolVersion> cachedLolVersions = new HashMap<>();

    @Override
    public LolVersion create(LolVersion lolVersion) {
        try {
            return lolVersionDao.save(lolVersion);
        } catch (ConstraintViolationException e) {
            // This record has already been created; return the existing record.
            return find(lolVersion);
        }
    }

    @Override
    public LolVersion update(LolVersion lolVersion) {
        return lolVersionDao.save(lolVersion);
    }

    @Override
    public LolVersion find(LolVersion lolVersion) {
        return lolVersionDao.findByMajorVersionAndMinorVersion(lolVersion.getMajorVersion(), lolVersion.getMinorVersion());
    }

    public LolVersion findOrCreate(LolVersion lolVersion) {
        LolVersion foundInstance = find(lolVersion);
        if (foundInstance != null) {
            return foundInstance;
        }
        return create(lolVersion);
    }
    
    public List<String> findVersionsWithData() {
        return lolVersionDao.findVersionsWithData().stream().map(v ->  v.getMajorVersion() + "." + v.getMinorVersion()).collect(Collectors.toList());
    }

    public Iterable<LolVersion> findAll() {
        return lolVersionDao.findAll();
    }
}
