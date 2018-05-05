/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.dao;

import java.util.Date;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import statikk.domain.entity.LolSummoner;
import statikk.domain.riotapi.model.Region;

/**
 *
 * @author Grann
 */
public interface LolSummonerDao extends CrudRepository<LolSummoner, Long> {

    public List<LolSummoner> findTop10ByRegionAndLastPlayedDateLessThanOrderByLastMinedDate(Region region, Date minimumLastPlayedDate);
}
