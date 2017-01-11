/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
public class ChampSpecWinRate extends BaseWinRateEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @JoinColumn(name = "champ_spec_id", referencedColumnName = "champ_spec_id")
    @OneToOne(optional = false)
    private ChampSpec champSpec;

    public ChampSpecWinRate() {
    }

    public ChampSpecWinRate(ChampSpec champSpec) {
        super();
        this.champSpec = champSpec;
    }

    public ChampSpec getChampSpec() {
        return champSpec;
    }

    public void setChampSpec(ChampSpec champSpec) {
        this.champSpec = champSpec;
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
