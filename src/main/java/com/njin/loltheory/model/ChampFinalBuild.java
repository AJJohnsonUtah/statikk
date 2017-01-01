/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.model;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "champ_final_build")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ChampFinalBuild.findAll", query = "SELECT c FROM ChampFinalBuild c"),
    @NamedQuery(name = "ChampFinalBuild.findByChampFinalBuildId", query = "SELECT c FROM ChampFinalBuild c WHERE c.champFinalBuildId = :champFinalBuildId"),
    @NamedQuery(name = "ChampFinalBuild.findByChampSpecId", query = "SELECT c FROM ChampFinalBuild c WHERE c.champSpecId = :champSpecId"),
    @NamedQuery(name = "ChampFinalBuild.findByFinalBuildOrder", query = "SELECT c FROM ChampFinalBuild c WHERE c.finalBuildOrder = :finalBuildOrder"),
    @NamedQuery(name = "ChampFinalBuild.findByPlayedCount", query = "SELECT c FROM ChampFinalBuild c WHERE c.playedCount = :playedCount"),
    @NamedQuery(name = "ChampFinalBuild.findByWinCount", query = "SELECT c FROM ChampFinalBuild c WHERE c.winCount = :winCount")})
public class ChampFinalBuild implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "champ_final_build_id")
    private Long champFinalBuildId;
    @Column(name = "champ_spec_id")
    private BigInteger champSpecId;
    @Column(name = "final_build_order")
    private BigInteger finalBuildOrder;
    @Column(name = "played_count")
    private BigInteger playedCount;
    @Column(name = "win_count")
    private BigInteger winCount;

    public ChampFinalBuild() {
    }

    public ChampFinalBuild(Long champFinalBuildId) {
        this.champFinalBuildId = champFinalBuildId;
    }

    public Long getChampFinalBuildId() {
        return champFinalBuildId;
    }

    public void setChampFinalBuildId(Long champFinalBuildId) {
        this.champFinalBuildId = champFinalBuildId;
    }

    public BigInteger getChampSpecId() {
        return champSpecId;
    }

    public void setChampSpecId(BigInteger champSpecId) {
        this.champSpecId = champSpecId;
    }

    public BigInteger getFinalBuildOrder() {
        return finalBuildOrder;
    }

    public void setFinalBuildOrder(BigInteger finalBuildOrder) {
        this.finalBuildOrder = finalBuildOrder;
    }

    public BigInteger getPlayedCount() {
        return playedCount;
    }

    public void setPlayedCount(BigInteger playedCount) {
        this.playedCount = playedCount;
    }

    public BigInteger getWinCount() {
        return winCount;
    }

    public void setWinCount(BigInteger winCount) {
        this.winCount = winCount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (champFinalBuildId != null ? champFinalBuildId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChampFinalBuild)) {
            return false;
        }
        ChampFinalBuild other = (ChampFinalBuild) object;
        if ((this.champFinalBuildId == null && other.champFinalBuildId != null) || (this.champFinalBuildId != null && !this.champFinalBuildId.equals(other.champFinalBuildId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.njin.loltheory.model.ChampFinalBuild[ champFinalBuildId=" + champFinalBuildId + " ]";
    }
    
}
