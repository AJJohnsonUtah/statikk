/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.entity;

import com.njin.loltheory.riotapi.model.BannedChampion;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author AJ
 */
@Embeddable
public class ChampBanPK implements Serializable {

    @JoinColumn(name = "champ_spec_id", referencedColumnName = "champ_spec_id")
    @ManyToOne(optional = false)
    @Basic(optional = false)
    @NotNull
    private ChampSpec champSpec;

    @Column(name = "ban_order")
    @Basic(optional = false)
    @NotNull
    private int banOrder;

    public ChampBanPK() {
    }

    public ChampBanPK(BannedChampion bannedChamp) {
        this.champSpec = bannedChamp.getChampSpec();
        this.banOrder = bannedChamp.getPickTurn();
    }

    public ChampSpec getChampSpec() {
        return champSpec;
    }

    public void setChampSpec(ChampSpec champSpec) {
        this.champSpec = champSpec;
    }

    public int getBanOrder() {
        return banOrder;
    }

    public void setBanOrder(int banOrder) {
        this.banOrder = banOrder;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.champSpec);
        hash = 79 * hash + this.banOrder;
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
        final ChampBanPK other = (ChampBanPK) obj;
        if (this.banOrder != other.banOrder) {
            return false;
        }
        if (!Objects.equals(this.champSpec, other.champSpec)) {
            return false;
        }
        return true;
    }

}
