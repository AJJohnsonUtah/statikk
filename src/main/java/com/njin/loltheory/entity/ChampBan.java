/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.entity;

import com.njin.loltheory.entity.converter.QueueTypeConverter;
import com.njin.loltheory.riotapi.model.QueueType;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "champ_ban")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ChampBan.findAll", query = "SELECT c FROM ChampBan c"),
    @NamedQuery(name = "ChampBan.findByChampBanId", query = "SELECT c FROM ChampBan c WHERE c.champBanId = :champBanId"),
    @NamedQuery(name = "ChampBan.findByChampId", query = "SELECT c FROM ChampBan c WHERE c.champId = :champId"),
    @NamedQuery(name = "ChampBan.findByLolVersionId", query = "SELECT c FROM ChampBan c WHERE c.lolVersionId = :lolVersionId"),
    @NamedQuery(name = "ChampBan.findByMatchType", query = "SELECT c FROM ChampBan c WHERE c.matchType = :matchType"),
    @NamedQuery(name = "ChampBan.findByBanOrder", query = "SELECT c FROM ChampBan c WHERE c.banOrder = :banOrder"),
    @NamedQuery(name = "ChampBan.findByBanCount", query = "SELECT c FROM ChampBan c WHERE c.banCount = :banCount")})
public class ChampBan implements Serializable {

    @JoinColumn(name = "lol_version_id", referencedColumnName = "lol_version_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private LolVersion lolVersionId;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "champ_ban_id")
    private Integer champBanId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "champ_id")
    private int champId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "match_type")
    @Convert(converter = QueueTypeConverter.class)
    private QueueType matchType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ban_order")
    private int banOrder;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ban_count")
    private int banCount;

    public ChampBan() {
    }

    public ChampBan(Integer champBanId) {
        this.champBanId = champBanId;
    }

    public ChampBan(Integer champBanId, int champId, LolVersion lolVersionId, QueueType matchType, int banOrder, int banCount) {
        this.champBanId = champBanId;
        this.champId = champId;
        this.lolVersionId = lolVersionId;
        this.matchType = matchType;
        this.banOrder = banOrder;
        this.banCount = banCount;
    }

    public Integer getChampBanId() {
        return champBanId;
    }

    public void setChampBanId(Integer champBanId) {
        this.champBanId = champBanId;
    }

    public int getChampId() {
        return champId;
    }

    public void setChampId(int champId) {
        this.champId = champId;
    }

    public QueueType getMatchType() {
        return matchType;
    }

    public void setMatchType(QueueType matchType) {
        this.matchType = matchType;
    }

    public int getBanOrder() {
        return banOrder;
    }

    public void setBanOrder(int banOrder) {
        this.banOrder = banOrder;
    }

    public int getBanCount() {
        return banCount;
    }

    public void setBanCount(int banCount) {
        this.banCount = banCount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (champBanId != null ? champBanId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChampBan)) {
            return false;
        }
        ChampBan other = (ChampBan) object;
        if ((this.champBanId == null && other.champBanId != null) || (this.champBanId != null && !this.champBanId.equals(other.champBanId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.njin.loltheory.model.ChampBan[ champBanId=" + champBanId + " ]";
    }

    public LolVersion getLolVersionId() {
        return lolVersionId;
    }

    public void setLolVersionId(LolVersion lolVersionId) {
        this.lolVersionId = lolVersionId;
    }

}
