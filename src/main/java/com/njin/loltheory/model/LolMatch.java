/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AJ
 */
@Entity
@Table(name = "lol_match")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LolMatch.findAll", query = "SELECT l FROM LolMatch l"),
    @NamedQuery(name = "LolMatch.findByMatchId", query = "SELECT l FROM LolMatch l WHERE l.matchId = :matchId"),
    @NamedQuery(name = "LolMatch.findByStatus", query = "SELECT l FROM LolMatch l WHERE l.status = :status"),
    @NamedQuery(name = "LolMatch.findByBeginTime", query = "SELECT l FROM LolMatch l WHERE l.beginTime = :beginTime"),
    @NamedQuery(name = "LolMatch.findByInsertTime", query = "SELECT l FROM LolMatch l WHERE l.insertTime = :insertTime"),
    @NamedQuery(name = "LolMatch.findByProcessedTime", query = "SELECT l FROM LolMatch l WHERE l.processedTime = :processedTime")})
public class LolMatch implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "match_id")
    private Long matchId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private short status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "begin_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date beginTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "insert_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insertTime;
    @Column(name = "processed_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date processedTime;

    public LolMatch() {
    }

    public LolMatch(Long matchId) {
        this.matchId = matchId;
    }

    public LolMatch(Long matchId, short status, Date beginTime, Date insertTime) {
        this.matchId = matchId;
        this.status = status;
        this.beginTime = beginTime;
        this.insertTime = insertTime;
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getProcessedTime() {
        return processedTime;
    }

    public void setProcessedTime(Date processedTime) {
        this.processedTime = processedTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (matchId != null ? matchId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LolMatch)) {
            return false;
        }
        LolMatch other = (LolMatch) object;
        if ((this.matchId == null && other.matchId != null) || (this.matchId != null && !this.matchId.equals(other.matchId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.njin.loltheory.model.LolMatch[ matchId=" + matchId + " ]";
    }
    
}
