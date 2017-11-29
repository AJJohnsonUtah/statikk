/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.entity;

import java.io.Serializable;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import statikk.domain.entity.converter.QueueTypeConverter;
import statikk.domain.entity.enums.Role;
import statikk.domain.riotapi.model.QueueType;

/**
 *
 * @author AJ
 */
@Entity
@Table(name = "team_comp", uniqueConstraints = {
    @UniqueConstraint(columnNames = "team_comp, match_type, lol_version_id")})
public class TeamComp extends BaseWinRateEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_comp_id")
    private long TeamCompId;

    @Column(name = "team_comp")
    private Map<Role, Integer> teamComp;

    @Column(name = "match_type")
    @Convert(converter = QueueTypeConverter.class)
    private QueueType matchType;

    @JoinColumn(name = "lol_version_id", referencedColumnName = "lol_version_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private LolVersion lolVersion;

    public long getTeamCompId() {
        return TeamCompId;
    }

    public void setTeamCompId(long TeamCompId) {
        this.TeamCompId = TeamCompId;
    }

    public Map<Role, Integer> getTeamComp() {
        return teamComp;
    }

    public void setTeamComp(Map<Role, Integer> teamComp) {
        this.teamComp = teamComp;
    }

    public QueueType getMatchType() {
        return matchType;
    }

    public void setMatchType(QueueType matchType) {
        this.matchType = matchType;
    }

    public LolVersion getLolVersion() {
        return lolVersion;
    }

    public void setLolVersion(LolVersion lolVersion) {
        this.lolVersion = lolVersion;
    }

}
