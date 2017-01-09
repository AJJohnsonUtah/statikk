/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.entity;

import com.njin.loltheory.entity.converter.MatchStatusConverter;
import com.njin.loltheory.entity.enums.MatchStatus;
import com.njin.loltheory.riotapi.model.GameDto;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.SQLInsert;

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
    @NamedQuery(name = "LolMatch.findByProcessedTime", query = "SELECT l FROM LolMatch l WHERE l.processedTime = :processedTime"),
    @NamedQuery(name = "LolMatch.findMatchesToAnalyze", query = "SELECT l.matchId FROM LolMatch l WHERE l.status = :status"),
    @NamedQuery(name = "LolMatch.updateMatchListStatus", query = "UPDATE LolMatch SET status = :status WHERE matchId IN (:matchIds)")})
@SQLInsert(sql = "INSERT INTO lol_match (begin_time, insert_time, processed_time, status, match_id) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE match_id = match_id")
public class LolMatch implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    @Convert(converter = MatchStatusConverter.class)
    private MatchStatus status;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "match_id")
    private Long matchId;
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

    public LolMatch(Long matchId, MatchStatus status, Date beginTime, Date insertTime) {
        this.matchId = matchId;
        this.status = status;
        this.beginTime = beginTime;
        this.insertTime = insertTime;
        this.processedTime = null;
    }

    public LolMatch(GameDto game) {
        this.matchId = game.getGameId();
        this.beginTime = new Date(game.getCreateDate());
        this.insertTime = new Date();
        this.processedTime = null;
        this.status = MatchStatus.READY;
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
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

    public MatchStatus getStatus() {
        return status;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.matchId);
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
        final LolMatch other = (LolMatch) obj;
        if (!Objects.equals(this.matchId, other.matchId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LolMatch{" + "status=" + status + ", matchId=" + matchId + ", beginTime=" + beginTime + ", insertTime=" + insertTime + ", processedTime=" + processedTime + '}';
    }

}
