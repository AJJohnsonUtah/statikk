/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.dao;

import org.springframework.data.repository.CrudRepository;
import statikk.domain.entity.LolVersion;

/**
 *
 * @author AJ
 */
public interface LolVersionDao extends CrudRepository<LolVersion, Long> {

    public LolVersion findByMajorVersionAndMinorVersion(int majorVersion, int minorVersion);

}
