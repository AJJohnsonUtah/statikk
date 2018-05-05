/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author AJ
 */
@Entity
@Table(name = "team_comp")
public class TeamComp extends BaseWinRateEntity implements Serializable {

    @EmbeddedId
    private TeamCompPK teamCompPK;

    public TeamComp() {
    }

    public TeamComp(TeamCompPK teamCompPK) {
        super();
        this.teamCompPK = teamCompPK;
    }

    public TeamCompPK getTeamCompPK() {
        return teamCompPK;
    }

    public void setTeamCompPK(TeamCompPK teamCompPK) {
        this.teamCompPK = teamCompPK;
    }

    @Override
    public String toString() {
        return "TeamComp{" + teamCompPK.toString() + "}";
    }

}
