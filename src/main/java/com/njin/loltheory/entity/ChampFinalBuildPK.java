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
public class ChampFinalBuildPK implements Serializable {

    @JoinColumn(name = "champ_spec_id", referencedColumnName = "champ_spec_id")
    @Basic(optional = false)
    @NotNull
    @ManyToOne(optional = false)
    private ChampSpec champSpec;

    @Basic(optional = false)
    @NotNull
    @JoinColumn(name = "final_build_order_id", referencedColumnName = "final_build_order_id")
    @ManyToOne(optional = false)
    private FinalBuildOrder finalBuildOrder;

    public ChampSpec getChampSpec() {
        return champSpec;
    }

    public void setChampSpec(ChampSpec champSpec) {
        this.champSpec = champSpec;
    }

    public FinalBuildOrder getFinalBuildOrder() {
        return finalBuildOrder;
    }

    public void setFinalBuildOrder(FinalBuildOrder finalBuildOrder) {
        this.finalBuildOrder = finalBuildOrder;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.champSpec);
        hash = 97 * hash + Objects.hashCode(this.finalBuildOrder);
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
        final ChampFinalBuildPK other = (ChampFinalBuildPK) obj;
        if (!Objects.equals(this.champSpec, other.champSpec)) {
            return false;
        }
        if (!Objects.equals(this.finalBuildOrder, other.finalBuildOrder)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ChampFinalBuildPK{" + "champSpec=" + champSpec + ", finalBuildOrder=" + finalBuildOrder + '}';
    }

}
