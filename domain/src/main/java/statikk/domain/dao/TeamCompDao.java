/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.dao;

import org.springframework.data.repository.CrudRepository;
import statikk.domain.entity.TeamComp;
import statikk.domain.entity.TeamCompPK;

/**
 *
 * @author Grann
 */
public interface TeamCompDao extends CrudRepository<TeamComp, TeamCompPK> {

}
