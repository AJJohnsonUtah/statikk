/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import statikk.domain.entity.converter.RankConverter;
import statikk.domain.entity.converter.RegionConverter;
import statikk.domain.riotapi.model.Rank;
import statikk.domain.riotapi.model.Region;

/**
 *
 * @author Grann
 */
@Entity
public class LolSummoner implements Serializable {

    @Id
    private long accountId;

    private Long summonerId;

    @Convert(converter = RankConverter.class)
    private Rank highestRank;

    @Convert(converter = RegionConverter.class)
    private Region region;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastMinedDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPlayedDate;

    public LolSummoner() {
    }

    public LolSummoner(long accountId, Long summonerId, Region region, Date lastMinedDate, Date lastPlayedDate) {
        this.accountId = accountId;
        this.summonerId = summonerId;
        this.region = region;
        this.lastMinedDate = lastMinedDate;
        this.lastPlayedDate = lastPlayedDate;
    }

    public LolSummoner(long accountId, Long summonerId, Rank highestRank, Region region) {
        this.accountId = accountId;
        this.summonerId = summonerId;
        this.highestRank = highestRank;
        this.region = region;
        this.lastMinedDate = new Date(1000000L);
        this.lastPlayedDate = new Date(1000000L);
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public Long getSummonerId() {
        return summonerId;
    }

    public void setSummonerId(Long summonerId) {
        this.summonerId = summonerId;
    }

    public Rank getHighestRank() {
        return highestRank;
    }

    public void setHighestRank(Rank highestRank) {
        this.highestRank = highestRank;
    }

    public Date getLastMinedDate() {
        return lastMinedDate;
    }

    public void setLastMinedDate(Date lastMinedDate) {
        this.lastMinedDate = lastMinedDate;
    }

    public Date getLastPlayedDate() {
        return lastPlayedDate;
    }

    public void setLastPlayedDate(Date lastPlayedDate) {
        this.lastPlayedDate = lastPlayedDate;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

}
