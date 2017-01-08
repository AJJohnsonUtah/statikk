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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.SQLInsert;

/**
 *
 * @author AJ
 */
@Entity
@Table(name = "champ_spec_win_rate")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ChampSpecWinRate.findAll", query = "SELECT c FROM ChampSpecWinRate c"),
    @NamedQuery(name = "ChampSpecWinRate.findByChampSpecWinRateId", query = "SELECT c FROM ChampSpecWinRate c WHERE c.champSpecWinRateId = :champSpecWinRateId"),
    @NamedQuery(name = "ChampSpecWinRate.findByWinCount", query = "SELECT c FROM ChampSpecWinRate c WHERE c.winCount = :winCount"),
    @NamedQuery(name = "ChampSpecWinRate.findByPlayedCount", query = "SELECT c FROM ChampSpecWinRate c WHERE c.playedCount = :playedCount")})
@SQLInsert(sql = "INSERT INTO champ_spec_win_rate (champ_spec_id, played_count, win_count) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE win_count = VALUES(win_count), played_count = VALUES(played_count)")

public class ChampSpecWinRate implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "champ_spec_win_rate_id")
    private Long champSpecWinRateId;
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
    private ChampSpec champSpec;

    public ChampSpecWinRate() {
    }

    public ChampSpecWinRate(Long champSpecWinRateId) {
        this.champSpecWinRateId = champSpecWinRateId;
    }

    public ChampSpecWinRate(ChampSpec champSpec) {
        this.champSpec = champSpec;
        this.playedCount = 0;
        this.winCount = 0;
    }

    public ChampSpecWinRate(Long champSpecWinRateId, long winCount, long playedCount) {
        this.champSpecWinRateId = champSpecWinRateId;
        this.winCount = winCount;
        this.playedCount = playedCount;
    }

    public Long getChampSpecWinRateId() {
        return champSpecWinRateId;
    }

    public void setChampSpecWinRateId(Long champSpecWinRateId) {
        this.champSpecWinRateId = champSpecWinRateId;
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

    public ChampSpec getChampSpec() {
        return champSpec;
    }

    public void setChampSpec(ChampSpec champSpec) {
        this.champSpec = champSpec;
    }

    public void addWin() {
        this.playedCount++;
        this.winCount++;
    }

    public void addLoss() {
        this.playedCount++;
    }

    public void combine(ChampSpecWinRate champSpecWinRate) {
        this.winCount += champSpecWinRate.winCount;
        this.playedCount += champSpecWinRate.playedCount;
    }

    @Override
    public int hashCode() {
        return this.getChampSpec().hashCode();
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChampSpecWinRate)) {
            return false;
        }
        ChampSpecWinRate other = (ChampSpecWinRate) object;
        return Objects.equals(this.champSpec, other.champSpec);
    }

    @Override
    public String toString() {
        return "com.njin.loltheory.model.ChampSpecWinRate[ champSpecWinRateId=" + champSpecWinRateId + " ]";
    }

}
