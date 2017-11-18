/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.entity;

import statikk.domain.entity.converter.MatchStatusConverter;
import statikk.domain.entity.enums.MatchStatus;
import statikk.domain.riotapi.model.MatchReferenceDto;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import statikk.domain.entity.converter.QueueTypeConverter;
import statikk.domain.entity.converter.RegionConverter;
import statikk.domain.riotapi.model.QueueType;
import statikk.domain.riotapi.model.Region;

/**
 *
 * @author AJ
 */
@Entity
@Table(name = "lol_match", uniqueConstraints = @UniqueConstraint(columnNames = {"match_id", "region"}))
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LolMatch.findAll", query = "SELECT l FROM LolMatch l")
    ,
    @NamedQuery(name = "LolMatch.findByMatchId", query = "SELECT l FROM LolMatch l WHERE l.matchId = :matchId")
    ,
    @NamedQuery(name = "LolMatch.findByStatus", query = "SELECT l FROM LolMatch l WHERE l.status = :status")
    ,
    @NamedQuery(name = "LolMatch.findByBeginTime", query = "SELECT l FROM LolMatch l WHERE l.beginTime = :beginTime")
    ,
    @NamedQuery(name = "LolMatch.findByInsertTime", query = "SELECT l FROM LolMatch l WHERE l.insertTime = :insertTime")
    ,
    @NamedQuery(name = "LolMatch.findByProcessedTime", query = "SELECT l FROM LolMatch l WHERE l.processedTime = :processedTime")
    ,
    @NamedQuery(name = "LolMatch.findMatchesToAnalyze", query = "SELECT l.matchId FROM LolMatch l WHERE l.status = :status")
    ,
    @NamedQuery(name = "LolMatch.updateMatchListStatus", query = "UPDATE LolMatch SET status = :status, processed_time = NOW() WHERE matchId IN (:matchIds)")})
public class LolMatch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "lol_match_id", nullable = false)
    private Long lolMatchId;

    @Basic(optional = false)
    @Column(name = "match_id", nullable = false)
    private Long matchId;

    @Basic(optional = false)
    @Column(name = "region", nullable = false)
    @Convert(converter = RegionConverter.class)
    private Region region;

    @Basic(optional = false)
    @Column(name = "match_type", nullable = false)
    @Convert(converter = QueueTypeConverter.class)
    private QueueType matchType;

    @Basic(optional = false)
    @Column(name = "status", nullable = false)
    @Convert(converter = MatchStatusConverter.class)
    private MatchStatus status;

    @Basic(optional = false)
    @Column(name = "begin_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date beginTime;

    @Basic(optional = false)
    @Column(name = "insert_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date insertTime;

    @Column(name = "processed_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date processedTime;

    public LolMatch() {
    }

    public LolMatch(Long matchId, Region region) {
        this.matchId = matchId;
        this.region = region;
    }

    public LolMatch(Long matchId, Region region, QueueType matchType, MatchStatus status, Date beginTime, Date insertTime) {
        this.matchId = matchId;
        this.region = region;
        this.matchType = matchType;
        this.status = status;
        this.beginTime = beginTime;
        this.insertTime = insertTime;
        this.processedTime = null;
    }

    public LolMatch(MatchReferenceDto game) {
        this.matchId = game.getGameId();
        this.beginTime = new Date(game.getTimestamp());
        this.insertTime = new Date();
        this.processedTime = null;
        this.status = MatchStatus.READY;
        this.region = game.getPlatformId();
        this.matchType = game.getQueue();
    }

    public Long getLolMatchId() {
        return lolMatchId;
    }

    public void setLolMatchId(Long lolMatchId) {
        this.lolMatchId = lolMatchId;
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

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }

    public QueueType getMatchType() {
        return matchType;
    }

    public void setMatchType(QueueType matchType) {
        this.matchType = matchType;
    }        

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.matchId);
        hash = 71 * hash + Objects.hashCode(this.region);
        hash = 71 * hash + Objects.hashCode(this.matchType);
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
        if (!Objects.equals(this.matchId, other.matchId) || !Objects.equals(this.region, other.region) || !Objects.equals(this.matchType, other.matchType)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LolMatch{" + "status=" + status + ", region=" + region + ", matchType: " + matchType + ", matchId=" + matchId + ", beginTime=" + beginTime + ", insertTime=" + insertTime + ", processedTime=" + processedTime + '}';
    }

}
