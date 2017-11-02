/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import statikk.domain.entity.LolMatch;
import statikk.domain.entity.enums.MatchStatus;

/**
 *
 * @author AJ
 */
public interface LolMatchDao extends CrudRepository<LolMatch, Long> {

    public List<LolMatch> findTop10ByStatus(MatchStatus status);

}
