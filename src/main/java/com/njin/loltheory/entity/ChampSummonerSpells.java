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
import javax.persistence.EmbeddedId;
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
    @NamedQuery(name = "ChampSummonerSpells.findByChampSummonerSpellsId", query = "SELECT c FROM ChampSummonerSpells c WHERE c.champSummonerSpellsPK.champSpec = :champSpec"),
    @NamedQuery(name = "ChampSummonerSpells.findBySpellA", query = "SELECT c FROM ChampSummonerSpells c WHERE c.champSummonerSpellsPK.spellA = :spellA"),
    @NamedQuery(name = "ChampSummonerSpells.findBySpellB", query = "SELECT c FROM ChampSummonerSpells c WHERE c.champSummonerSpellsPK.spellB = :spellB")})
public class ChampSummonerSpells implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ChampSummonerSpellsPK champSummonerSpellsPK;

    @Basic(optional = false)
    @NotNull
    @Column(name = "win_count")
    private long winCount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "played_count")
    private long playedCount;

    public ChampSummonerSpells() {
    }

    public ChampSummonerSpells(ChampSummonerSpellsPK champSummonerSpellsPK) {
        this.champSummonerSpellsPK = champSummonerSpellsPK;
        this.winCount = 0;
        this.playedCount = 0;
    }

    public ChampSummonerSpells(ChampSummonerSpellsPK champSummonerSpellsPK, long winCount, long playedCount) {
        this.champSummonerSpellsPK = champSummonerSpellsPK;
        this.winCount = winCount;
        this.playedCount = playedCount;
    }

    public ChampSummonerSpellsPK getChampSummonerSpellsPK() {
        return champSummonerSpellsPK;
    }

    public void setChampSummonerSpellsPK(ChampSummonerSpellsPK champSummonerSpellsPK) {
        this.champSummonerSpellsPK = champSummonerSpellsPK;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.champSummonerSpellsPK);
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
        final ChampSummonerSpells other = (ChampSummonerSpells) obj;
        if (!Objects.equals(this.champSummonerSpellsPK, other.champSummonerSpellsPK)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ChampSummonerSpells{" + "champSummonerSpellsPK=" + champSummonerSpellsPK + ", winCount=" + winCount + ", playedCount=" + playedCount + '}';
    }

}
