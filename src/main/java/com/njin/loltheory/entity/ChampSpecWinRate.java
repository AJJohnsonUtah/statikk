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
import javax.persistence.Id;
import javax.persistence.IdClass;
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
    @NamedQuery(name = "ChampSpecWinRate.findAll", query = "SELECT c FROM ChampSpecWinRate c")})
public class ChampSpecWinRate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @JoinColumn(name = "champ_spec_id", referencedColumnName = "champ_spec_id")
    @OneToOne(optional = false)
    private ChampSpec champSpec;

    @Basic(optional = false)
    @NotNull
    @Column(name = "win_count")
    private long winCount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "played_count")
    private long playedCount;

    public ChampSpecWinRate() {
    }

    public ChampSpecWinRate(ChampSpec champSpec) {
        this.champSpec = champSpec;
        this.playedCount = 0;
        this.winCount = 0;
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
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.champSpec);
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
        final ChampSpecWinRate other = (ChampSpecWinRate) obj;
        if (!Objects.equals(this.champSpec, other.champSpec)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ChampSpecWinRate{" + "winCount=" + winCount + ", playedCount=" + playedCount + ", champSpec=" + champSpec + '}';
    }

}
