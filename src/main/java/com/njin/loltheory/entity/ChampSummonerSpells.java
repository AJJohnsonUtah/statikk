/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
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
public class ChampSummonerSpells extends BaseWinRateEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ChampSummonerSpellsPK champSummonerSpellsPK;

    public ChampSummonerSpells() {
    }

    public ChampSummonerSpells(ChampSummonerSpellsPK champSummonerSpellsPK) {
        super();
        this.champSummonerSpellsPK = champSummonerSpellsPK;
    }

    public ChampSummonerSpellsPK getChampSummonerSpellsPK() {
        return champSummonerSpellsPK;
    }

    public void setChampSummonerSpellsPK(ChampSummonerSpellsPK champSummonerSpellsPK) {
        this.champSummonerSpellsPK = champSummonerSpellsPK;
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
