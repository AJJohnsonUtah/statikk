/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author AJ
 */
@Entity
@Table(name = "champ_spec")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ChampSpec.findAll", query = "SELECT c FROM ChampSpec c"),
    @NamedQuery(name = "ChampSpec.findByChampSpecId", query = "SELECT c FROM ChampSpec c WHERE c.champSpecId = :champSpecId"),
    @NamedQuery(name = "ChampSpec.findByChampionId", query = "SELECT c FROM ChampSpec c WHERE c.championId = :championId"),
    @NamedQuery(name = "ChampSpec.findByMatchType", query = "SELECT c FROM ChampSpec c WHERE c.matchType = :matchType"),
    @NamedQuery(name = "ChampSpec.findByLolVersionId", query = "SELECT c FROM ChampSpec c WHERE c.lolVersionId = :lolVersionId"),
    @NamedQuery(name = "ChampSpec.findByLane", query = "SELECT c FROM ChampSpec c WHERE c.lane = :lane"),
    @NamedQuery(name = "ChampSpec.findByRole", query = "SELECT c FROM ChampSpec c WHERE c.role = :role"),
    @NamedQuery(name = "ChampSpec.findByRank", query = "SELECT c FROM ChampSpec c WHERE c.rank = :rank")})
public class ChampSpec implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "champ_spec_id")
    private Long champSpecId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "champion_id")
    private int championId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "match_type")
    private int matchType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lol_version_id")
    private int lolVersionId;
    @Column(name = "lane")
    private Integer lane;
    @Column(name = "role")
    private Integer role;
    @Column(name = "rank")
    private Integer rank;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "champSpecId")
    private ChampSpecWinRate champSpecWinRate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "champSpecIdA")
    private Collection<ChampMatchup> champMatchupCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "champSpecIdB")
    private Collection<ChampMatchup> champMatchupCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "champSpecId")
    private Collection<ChampSummonerSpells> champSummonerSpellsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "champSpecIdA")
    private Collection<ChampTeamup> champTeamupCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "champSpecIdB")
    private Collection<ChampTeamup> champTeamupCollection1;

    public ChampSpec() {
    }

    public ChampSpec(Long champSpecId) {
        this.champSpecId = champSpecId;
    }

    public ChampSpec(Long champSpecId, int championId, int matchType, int lolVersionId) {
        this.champSpecId = champSpecId;
        this.championId = championId;
        this.matchType = matchType;
        this.lolVersionId = lolVersionId;
    }

    public Long getChampSpecId() {
        return champSpecId;
    }

    public void setChampSpecId(Long champSpecId) {
        this.champSpecId = champSpecId;
    }

    public int getChampionId() {
        return championId;
    }

    public void setChampionId(int championId) {
        this.championId = championId;
    }

    public int getMatchType() {
        return matchType;
    }

    public void setMatchType(int matchType) {
        this.matchType = matchType;
    }

    public int getLolVersionId() {
        return lolVersionId;
    }

    public void setLolVersionId(int lolVersionId) {
        this.lolVersionId = lolVersionId;
    }

    public Integer getLane() {
        return lane;
    }

    public void setLane(Integer lane) {
        this.lane = lane;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public ChampSpecWinRate getChampSpecWinRate() {
        return champSpecWinRate;
    }

    public void setChampSpecWinRate(ChampSpecWinRate champSpecWinRate) {
        this.champSpecWinRate = champSpecWinRate;
    }

    @XmlTransient
    public Collection<ChampMatchup> getChampMatchupCollection() {
        return champMatchupCollection;
    }

    public void setChampMatchupCollection(Collection<ChampMatchup> champMatchupCollection) {
        this.champMatchupCollection = champMatchupCollection;
    }

    @XmlTransient
    public Collection<ChampMatchup> getChampMatchupCollection1() {
        return champMatchupCollection1;
    }

    public void setChampMatchupCollection1(Collection<ChampMatchup> champMatchupCollection1) {
        this.champMatchupCollection1 = champMatchupCollection1;
    }

    @XmlTransient
    public Collection<ChampSummonerSpells> getChampSummonerSpellsCollection() {
        return champSummonerSpellsCollection;
    }

    public void setChampSummonerSpellsCollection(Collection<ChampSummonerSpells> champSummonerSpellsCollection) {
        this.champSummonerSpellsCollection = champSummonerSpellsCollection;
    }

    @XmlTransient
    public Collection<ChampTeamup> getChampTeamupCollection() {
        return champTeamupCollection;
    }

    public void setChampTeamupCollection(Collection<ChampTeamup> champTeamupCollection) {
        this.champTeamupCollection = champTeamupCollection;
    }

    @XmlTransient
    public Collection<ChampTeamup> getChampTeamupCollection1() {
        return champTeamupCollection1;
    }

    public void setChampTeamupCollection1(Collection<ChampTeamup> champTeamupCollection1) {
        this.champTeamupCollection1 = champTeamupCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (champSpecId != null ? champSpecId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChampSpec)) {
            return false;
        }
        ChampSpec other = (ChampSpec) object;
        if ((this.champSpecId == null && other.champSpecId != null) || (this.champSpecId != null && !this.champSpecId.equals(other.champSpecId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.njin.loltheory.model.ChampSpec[ champSpecId=" + champSpecId + " ]";
    }
    
}
