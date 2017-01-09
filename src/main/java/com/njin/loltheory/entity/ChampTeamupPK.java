/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author AJ
 */
@Embeddable
public class ChampTeamupPK implements Serializable {

    @JoinColumn(name = "champ_spec_id_a", referencedColumnName = "champ_spec_id")
    @ManyToOne(optional = false)
    @Basic(optional = false)
    @NotNull
    private ChampSpec champSpecA;

    @JoinColumn(name = "champ_spec_id_b", referencedColumnName = "champ_spec_id")
    @ManyToOne(optional = false)
    @Basic(optional = false)
    @NotNull
    private ChampSpec champSpecB;

    public ChampSpec getChampSpecA() {
        return champSpecA;
    }

    public void setChampSpecA(ChampSpec champSpecA) {
        this.champSpecA = champSpecA;
    }

    public ChampSpec getChampSpecB() {
        return champSpecB;
    }

    public void setChampSpecB(ChampSpec champSpecB) {
        this.champSpecB = champSpecB;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.champSpecA);
        hash = 67 * hash + Objects.hashCode(this.champSpecB);
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
        final ChampTeamupPK other = (ChampTeamupPK) obj;
        if (!Objects.equals(this.champSpecA, other.champSpecA)) {
            return false;
        }
        if (!Objects.equals(this.champSpecB, other.champSpecB)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ChampTeamupPK{" + "champSpecA=" + champSpecA + ", champSpecB=" + champSpecB + '}';
    }

}
