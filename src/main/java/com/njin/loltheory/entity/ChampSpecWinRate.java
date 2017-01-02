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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AJ
 */
@Entity
@Table(name = "champ_spec_win_rate")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ChampSpecWinRate.findAll", query = "SELECT c FROM ChampSpecWinRate c"),
    @NamedQuery(name = "ChampSpecWinRate.findByChampSpeciWinRateId", query = "SELECT c FROM ChampSpecWinRate c WHERE c.champSpeciWinRateId = :champSpeciWinRateId"),
    @NamedQuery(name = "ChampSpecWinRate.findByWinCount", query = "SELECT c FROM ChampSpecWinRate c WHERE c.winCount = :winCount"),
    @NamedQuery(name = "ChampSpecWinRate.findByPlayedCount", query = "SELECT c FROM ChampSpecWinRate c WHERE c.playedCount = :playedCount")})
public class ChampSpecWinRate implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "champ_speci_win_rate_id")
    private Long champSpeciWinRateId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "win_count")
    private long winCount;
    @Basic(optional = false)
    @NotNull
    @Column(name = "played_count")
    private long playedCount;
    @JoinColumn(name = "champ_spec_id", referencedColumnName = "champ_spec_id")
    @OneToOne(optional = false)
    private ChampSpec champSpecId;

    public ChampSpecWinRate() {
    }

    public ChampSpecWinRate(Long champSpeciWinRateId) {
        this.champSpeciWinRateId = champSpeciWinRateId;
    }

    public ChampSpecWinRate(Long champSpeciWinRateId, long winCount, long playedCount) {
        this.champSpeciWinRateId = champSpeciWinRateId;
        this.winCount = winCount;
        this.playedCount = playedCount;
    }

    public Long getChampSpeciWinRateId() {
        return champSpeciWinRateId;
    }

    public void setChampSpeciWinRateId(Long champSpeciWinRateId) {
        this.champSpeciWinRateId = champSpeciWinRateId;
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

    public ChampSpec getChampSpecId() {
        return champSpecId;
    }

    public void setChampSpecId(ChampSpec champSpecId) {
        this.champSpecId = champSpecId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (champSpeciWinRateId != null ? champSpeciWinRateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChampSpecWinRate)) {
            return false;
        }
        ChampSpecWinRate other = (ChampSpecWinRate) object;
        if ((this.champSpeciWinRateId == null && other.champSpeciWinRateId != null) || (this.champSpeciWinRateId != null && !this.champSpeciWinRateId.equals(other.champSpeciWinRateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.njin.loltheory.model.ChampSpecWinRate[ champSpeciWinRateId=" + champSpeciWinRateId + " ]";
    }
    
}
