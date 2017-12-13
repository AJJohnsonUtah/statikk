/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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
import statikk.domain.entity.converter.TeamCompMapConverter;
import statikk.domain.entity.enums.Role;
import statikk.domain.riotapi.model.ParticipantDto;
import statikk.domain.riotapi.model.QueueType;

/**
 *
 * @author AJ
 */
@Entity
@Table(name = "team_comp", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"team_comp_id", "enemy_team_comp", "match_type", "lol_version_id"})})
public class TeamComp extends BaseWinRateEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_comp_id")
    private long TeamCompId;

    @Column(name = "ally_team_comp")
    @Convert(converter = TeamCompMapConverter.class)
    private Map<Role, Integer> allyTeamComp;

    @Column(name = "enemy_team_comp")
    @Convert(converter = TeamCompMapConverter.class)
    private Map<Role, Integer> enemyTeamComp;

    @Column(name = "match_type")
    @Convert(converter = QueueTypeConverter.class)
    private QueueType matchType;

    @JoinColumn(name = "lol_version_id", referencedColumnName = "lol_version_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private LolVersion lolVersion;
    
    public TeamComp() {
    }

    public TeamComp(Collection<ParticipantDto> allyTeamParticipants, Collection<ParticipantDto> enemyTeamParticipants, QueueType matchType, LolVersion lolVersion) {
        this.allyTeamComp = new HashMap<>();
        allyTeamParticipants.forEach((participant) -> {
            if (allyTeamComp.containsKey(participant.getRole())) {
                allyTeamComp.put(participant.getRole(), allyTeamComp.get(participant.getRole()) + 1);
            } else {
                allyTeamComp.put(participant.getRole(), 1);
            }
        });

        this.enemyTeamComp = new HashMap<>();
        enemyTeamParticipants.forEach((participant) -> {
            if (enemyTeamComp.containsKey(participant.getRole())) {
                enemyTeamComp.put(participant.getRole(), enemyTeamComp.get(participant.getRole()) + 1);
            } else {
                enemyTeamComp.put(participant.getRole(), 1);
            }
        });

        this.matchType = matchType;
        this.lolVersion = lolVersion;
    }

    public long getTeamCompId() {
        return TeamCompId;
    }

    public void setTeamCompId(long TeamCompId) {
        this.TeamCompId = TeamCompId;
    }

    public Map<Role, Integer> getAllyTeamComp() {
        return allyTeamComp;
    }

    public void setAllyTeamComp(Map<Role, Integer> allyTeamComp) {
        this.allyTeamComp = allyTeamComp;
    }

    public Map<Role, Integer> getEnemyTeamComp() {
        return enemyTeamComp;
    }

    public void setEnemyTeamComp(Map<Role, Integer> enemyTeamComp) {
        this.enemyTeamComp = enemyTeamComp;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(TeamCompMapConverter.convertToString(this.allyTeamComp));
        hash = 29 * hash + Objects.hashCode(TeamCompMapConverter.convertToString(this.enemyTeamComp));
        hash = 29 * hash + Objects.hashCode(this.matchType);
        hash = 29 * hash + Objects.hashCode(this.lolVersion);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TeamComp other = (TeamComp) obj;
        if (!Objects.equals(TeamCompMapConverter.convertToString(this.allyTeamComp), TeamCompMapConverter.convertToString(other.allyTeamComp))) {
            return false;
        }
        if (!Objects.equals(TeamCompMapConverter.convertToString(this.enemyTeamComp), TeamCompMapConverter.convertToString(other.enemyTeamComp))) {
            return false;
        }
        if (this.matchType != other.matchType) {
            return false;
        }
        if (!Objects.equals(this.lolVersion, other.lolVersion)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TeamComp{" + "TeamCompId=" + TeamCompId + ", allyTeamComp=" + TeamCompMapConverter.convertToString(allyTeamComp) + ", enemyTeamComp=" + TeamCompMapConverter.convertToString(enemyTeamComp) + ", matchType=" + matchType + ", lolVersion=" + lolVersion + '}';
    }

    
    
    
}
