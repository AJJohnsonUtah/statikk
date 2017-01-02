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
@Table(name = "champ_summoner_spells")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ChampSummonerSpells.findAll", query = "SELECT c FROM ChampSummonerSpells c"),
    @NamedQuery(name = "ChampSummonerSpells.findByChampSummonerSpellsId", query = "SELECT c FROM ChampSummonerSpells c WHERE c.champSummonerSpellsId = :champSummonerSpellsId"),
    @NamedQuery(name = "ChampSummonerSpells.findBySpellA", query = "SELECT c FROM ChampSummonerSpells c WHERE c.spellA = :spellA"),
    @NamedQuery(name = "ChampSummonerSpells.findBySpellB", query = "SELECT c FROM ChampSummonerSpells c WHERE c.spellB = :spellB"),
    @NamedQuery(name = "ChampSummonerSpells.findByWinCount", query = "SELECT c FROM ChampSummonerSpells c WHERE c.winCount = :winCount"),
    @NamedQuery(name = "ChampSummonerSpells.findByPlayedCount", query = "SELECT c FROM ChampSummonerSpells c WHERE c.playedCount = :playedCount")})
public class ChampSummonerSpells implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "champ_summoner_spells_id")
    private Long champSummonerSpellsId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "spell_a")
    private int spellA;
    @Basic(optional = false)
    @NotNull
    @Column(name = "spell_b")
    private int spellB;
    @Basic(optional = false)
    @NotNull
    @Column(name = "win_count")
    private long winCount;
    @Basic(optional = false)
    @NotNull
    @Column(name = "played_count")
    private long playedCount;
    @JoinColumn(name = "champ_spec_id", referencedColumnName = "champ_spec_id")
    @ManyToOne(optional = false)
    private ChampSpec champSpecId;

    public ChampSummonerSpells() {
    }

    public ChampSummonerSpells(Long champSummonerSpellsId) {
        this.champSummonerSpellsId = champSummonerSpellsId;
    }

    public ChampSummonerSpells(Long champSummonerSpellsId, int spellA, int spellB, long winCount, long playedCount) {
        this.champSummonerSpellsId = champSummonerSpellsId;
        this.spellA = spellA;
        this.spellB = spellB;
        this.winCount = winCount;
        this.playedCount = playedCount;
    }

    public Long getChampSummonerSpellsId() {
        return champSummonerSpellsId;
    }

    public void setChampSummonerSpellsId(Long champSummonerSpellsId) {
        this.champSummonerSpellsId = champSummonerSpellsId;
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
        hash += (champSummonerSpellsId != null ? champSummonerSpellsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChampSummonerSpells)) {
            return false;
        }
        ChampSummonerSpells other = (ChampSummonerSpells) object;
        if ((this.champSummonerSpellsId == null && other.champSummonerSpellsId != null) || (this.champSummonerSpellsId != null && !this.champSummonerSpellsId.equals(other.champSummonerSpellsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.njin.loltheory.model.ChampSummonerSpells[ champSummonerSpellsId=" + champSummonerSpellsId + " ]";
    }
    
}
