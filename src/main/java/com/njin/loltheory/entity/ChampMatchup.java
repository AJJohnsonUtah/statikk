/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.entity;

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
@Table(name = "champ_matchup")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ChampMatchup.findAll", query = "SELECT c FROM ChampMatchup c"),
    @NamedQuery(name = "ChampMatchup.findByChampMatchupId", query = "SELECT c FROM ChampMatchup c WHERE c.champMatchupId = :champMatchupId"),
    @NamedQuery(name = "ChampMatchup.findByWinCount", query = "SELECT c FROM ChampMatchup c WHERE c.winCount = :winCount"),
    @NamedQuery(name = "ChampMatchup.findByPlayedCount", query = "SELECT c FROM ChampMatchup c WHERE c.playedCount = :playedCount")})
public class ChampMatchup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "champ_matchup_id")
    private Long champMatchupId;
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

    public ChampMatchup() {
    }

    public ChampMatchup(Long champMatchupId) {
        this.champMatchupId = champMatchupId;
    }

    public ChampMatchup(Long champMatchupId, long winCount, long playedCount) {
        this.champMatchupId = champMatchupId;
        this.winCount = winCount;
        this.playedCount = playedCount;
    }

    public Long getChampMatchupId() {
        return champMatchupId;
    }

    public void setChampMatchupId(Long champMatchupId) {
        this.champMatchupId = champMatchupId;
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
        hash += (champMatchupId != null ? champMatchupId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChampMatchup)) {
            return false;
        }
        ChampMatchup other = (ChampMatchup) object;
        if ((this.champMatchupId == null && other.champMatchupId != null) || (this.champMatchupId != null && !this.champMatchupId.equals(other.champMatchupId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.njin.loltheory.model.ChampMatchup[ champMatchupId=" + champMatchupId + " ]";
    }
    
}
