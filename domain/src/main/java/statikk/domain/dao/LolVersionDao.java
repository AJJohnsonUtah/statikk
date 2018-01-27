/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.dao;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import statikk.domain.entity.LolVersion;

/**
 *
 * @author AJ
 */
public interface LolVersionDao extends CrudRepository<LolVersion, Long> {

    public LolVersion findByMajorVersionAndMinorVersion(int majorVersion, int minorVersion);

    @Query("SELECT DISTINCT c.lolVersion FROM ChampSpec c ORDER BY c.lolVersion.majorVersion DESC, c.lolVersion.minorVersion DESC")
    public List<LolVersion> findVersionsWithData();

    public List<LolVersion> findTop2ByOrderByMajorVersionDescMinorVersionDesc();

    public List<LolVersion> findTop1ByOrderByMajorVersionDescMinorVersionDesc();

}
