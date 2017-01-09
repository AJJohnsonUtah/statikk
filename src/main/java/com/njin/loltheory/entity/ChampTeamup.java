/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AJ
 */
@Entity
@Table(name = "champ_teamup")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ChampTeamup.findAll", query = "SELECT c FROM ChampTeamup c"),
    @NamedQuery(name = "ChampTeamup.findByChampSpec", query = "SELECT c FROM ChampTeamup c WHERE c.champTeamupPK.champSpecA = :champSpec")})
public class ChampTeamup implements Serializable {

    @EmbeddedId
    protected ChampTeamupPK champTeamupPK;

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @NotNull
    @Column(name = "win_count")
    private long winCount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "played_count")
    private long playedCount;

    public ChampTeamup() {
    }

    public ChampTeamup(ChampTeamupPK champTeamupPK) {
        this.champTeamupPK = champTeamupPK;
    }

    public ChampTeamup(ChampTeamupPK champTeamupPK, long winCount, long playedCount) {
        this.champTeamupPK = champTeamupPK;
        this.winCount = winCount;
        this.playedCount = playedCount;
    }

    public long getWinCount() {
        return winCount;
    }

    public void setWinCount(long winCount) {
        this.winCount = winCount;
    }

    public long getPlayedCount() {
        return playedCount;
    }

    public void setPlayedCount(long playedCount) {
        this.playedCount = playedCount;
    }

    public ChampTeamupPK getChampTeamupPK() {
        return champTeamupPK;
    }

    public void setChampTeamupPK(ChampTeamupPK champTeamupPK) {
        this.champTeamupPK = champTeamupPK;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.champTeamupPK);
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
        final ChampTeamup other = (ChampTeamup) obj;
        if (!Objects.equals(this.champTeamupPK, other.champTeamupPK)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ChampTeamup{" + "champTeamupPK=" + champTeamupPK + ", winCount=" + winCount + ", playedCount=" + playedCount + '}';
    }

}
