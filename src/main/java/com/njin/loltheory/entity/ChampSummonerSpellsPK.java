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
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author AJ
 */
@Embeddable
public class ChampSummonerSpellsPK implements Serializable {

    @JoinColumn(name = "champ_spec_id", referencedColumnName = "champ_spec_id")
    @ManyToOne(optional = false)
    private ChampSpec champSpec;

    @Basic(optional = false)
    @NotNull
    @Column(name = "spell_a")
    private int spellA;

    @Basic(optional = false)
    @NotNull
    @Column(name = "spell_b")
    private int spellB;

    public ChampSummonerSpellsPK() {
    }

    public ChampSummonerSpellsPK(ChampSpec champSpec, int spell1Id, int spell2Id) {
        this.champSpec = champSpec;
        this.spellA = spell1Id;
        this.spellB = spell2Id;
    }

    public ChampSpec getChampSpec() {
        return champSpec;
    }

    public void setChampSpec(ChampSpec champSpec) {
        this.champSpec = champSpec;
    }

    public int getSpellA() {
        return spellA;
    }

    public void setSpellA(int spellA) {
        this.spellA = spellA;
    }

    public int getSpellB() {
        return spellB;
    }

    public void setSpellB(int spellB) {
        this.spellB = spellB;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.champSpec);
        hash = 23 * hash + this.spellA;
        hash = 23 * hash + this.spellB;
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
        final ChampSummonerSpellsPK other = (ChampSummonerSpellsPK) obj;
        if (this.spellA != other.spellA) {
            return false;
        }
        if (this.spellB != other.spellB) {
            return false;
        }
        if (!Objects.equals(this.champSpec, other.champSpec)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ChampSummonerSpellsPK{" + "champSpecId=" + champSpec + ", spellA=" + spellA + ", spellB=" + spellB + '}';
    }

}
