/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    @NamedQuery(name = "ChampTeamup.findByChampTeamupId", query = "SELECT c FROM ChampTeamup c WHERE c.champTeamupId = :champTeamupId"),
    @NamedQuery(name = "ChampTeamup.findByWinCount", query = "SELECT c FROM ChampTeamup c WHERE c.winCount = :winCount"),
    @NamedQuery(name = "ChampTeamup.findByPlayedCount", query = "SELECT c FROM ChampTeamup c WHERE c.playedCount = :playedCount")})
public class ChampTeamup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "champ_teamup_id")
    private Long champTeamupId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "win_count")
    private long winCount;
    @Basic(optional = false)
    @NotNull
    @Column(name = "played_count")
    private long playedCount;
    @JoinColumn(name = "champ_spec_id_a", referencedColumnName = "champ_spec_id")
    @ManyToOne(optional = false)
    private ChampSpec champSpecIdA;
    @JoinColumn(name = "champ_spec_id_b", referencedColumnName = "champ_spec_id")
    @ManyToOne(optional = false)
    private ChampSpec champSpecIdB;

    public ChampTeamup() {
    }

    public ChampTeamup(Long champTeamupId) {
        this.champTeamupId = champTeamupId;
    }

    public ChampTeamup(Long champTeamupId, long winCount, long playedCount) {
        this.champTeamupId = champTeamupId;
        this.winCount = winCount;
        this.playedCount = playedCount;
    }

    public Long getChampTeamupId() {
        return champTeamupId;
    }

    public void setChampTeamupId(Long champTeamupId) {
        this.champTeamupId = champTeamupId;
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

    public ChampSpec getChampSpecIdA() {
        return champSpecIdA;
    }

    public void setChampSpecIdA(ChampSpec champSpecIdA) {
        this.champSpecIdA = champSpecIdA;
    }

    public ChampSpec getChampSpecIdB() {
        return champSpecIdB;
    }

    public void setChampSpecIdB(ChampSpec champSpecIdB) {
        this.champSpecIdB = champSpecIdB;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (champTeamupId != null ? champTeamupId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChampTeamup)) {
            return false;
        }
        ChampTeamup other = (ChampTeamup) object;
        if ((this.champTeamupId == null && other.champTeamupId != null) || (this.champTeamupId != null && !this.champTeamupId.equals(other.champTeamupId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.njin.loltheory.model.ChampTeamup[ champTeamupId=" + champTeamupId + " ]";
    }
    
}
