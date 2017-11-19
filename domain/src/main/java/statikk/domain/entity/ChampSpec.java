/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import statikk.domain.entity.converter.LaneConverter;
import statikk.domain.entity.converter.QueueTypeConverter;
import statikk.domain.entity.converter.RankConverter;
import statikk.domain.entity.converter.RegionConverter;
import statikk.domain.entity.converter.RoleConverter;
import statikk.domain.entity.enums.Lane;
import statikk.domain.entity.enums.Role;
import statikk.domain.riotapi.model.MatchDetail;
import statikk.domain.riotapi.model.ParticipantDto;
import statikk.domain.riotapi.model.QueueType;
import statikk.domain.riotapi.model.Rank;
import statikk.domain.riotapi.model.Region;
import statikk.domain.riotapi.model.TeamBansDto;

/**
 *
 * @author AJ
 */
@Entity
@Table(name = "champ_spec", uniqueConstraints = @UniqueConstraint(columnNames = {"champion_id", "lol_version_id", "match_type", "lane", "role", "rank", "region"}))
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ChampSpec.findAll", query = "SELECT c FROM ChampSpec c")
    ,
    @NamedQuery(name = "ChampSpec.findByChampSpecId", query = "SELECT c FROM ChampSpec c WHERE c.champSpecId = :champSpecId")
    ,
    @NamedQuery(name = "ChampSpec.findByChampionId", query = "SELECT c FROM ChampSpec c WHERE c.championId = :championId")
    ,
    @NamedQuery(name = "ChampSpec.findByMatchType", query = "SELECT c FROM ChampSpec c WHERE c.matchType = :matchType")
    ,
    @NamedQuery(name = "ChampSpec.findByLolVersionId", query = "SELECT c FROM ChampSpec c WHERE c.lolVersion = :lolVersionId")
    ,
    @NamedQuery(name = "ChampSpec.findByLane", query = "SELECT c FROM ChampSpec c WHERE c.lane = :lane")
    ,
    @NamedQuery(name = "ChampSpec.findByRole", query = "SELECT c FROM ChampSpec c WHERE c.role = :role")
    ,
    @NamedQuery(name = "ChampSpec.findByRank", query = "SELECT c FROM ChampSpec c WHERE c.rank = :rank")
    ,
    @NamedQuery(name = "ChampSpec.find", query = "SELECT c FROM ChampSpec c WHERE c.championId = :championId AND c.matchType = :matchType AND c.lolVersion = :lolVersionId AND c.lane = :lane AND c.role = :role AND c.rank = :rank")})
public class ChampSpec implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "champ_spec_id")
    private Long champSpecId;

    @Basic(optional = false)
    @Column(name = "champion_id", nullable = false)
    private int championId;

    @JoinColumn(name = "lol_version_id", referencedColumnName = "lol_version_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private LolVersion lolVersion;

    @Basic(optional = false)
    @Column(name = "match_type", nullable = false)
    @Convert(converter = QueueTypeConverter.class)
    private QueueType matchType;

    @Convert(converter = LaneConverter.class)
    @Column(name = "lane")
    private Lane lane;

    @Convert(converter = RoleConverter.class)
    @Column(name = "role")
    private Role role;

    @Convert(converter = RankConverter.class)
    @Column(name = "rank")
    private Rank rank;

    @Convert(converter = RegionConverter.class)
    @Column(name = "region", nullable = false)
    private Region region;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "champSpecWinRatePK.champSpec", fetch = FetchType.LAZY)
    private ChampSpecWinRate champSpecWinRate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "champMatchupPK.champSpecA", fetch = FetchType.LAZY)
    private List<ChampMatchup> champMatchupList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "champSummonerSpellsPK.champSpec", fetch = FetchType.LAZY)
    private List<ChampSummonerSpells> champSummonerSpellsList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "champTeamupPK.champSpecA", fetch = FetchType.LAZY)
    private List<ChampTeamup> champTeamupList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "champFinalBuildPK.champSpec", fetch = FetchType.LAZY)
    private List<ChampFinalBuild> champFinalBuildList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "champBanPK.champSpec", fetch = FetchType.LAZY)
    private List<ChampBan> champBanList;

    public ChampSpec() {
    }

    public ChampSpec(int championId, LolVersion lolVersion, QueueType matchType, Lane lane, Role role, Rank rank, Region region) {
        this.championId = championId;
        this.lolVersion = lolVersion;
        this.matchType = matchType;
        this.lane = lane;
        this.role = role;
        this.rank = rank;
        this.region = region;
    }

    public ChampSpec(Long champSpecId) {
        this.champSpecId = champSpecId;
    }

    public ChampSpec(MatchDetail match, ParticipantDto matchParticipant) {
        this.championId = matchParticipant.getChampionId();
        this.matchType = match.getQueueId();
        this.lolVersion = match.getGameVersion();
        this.lane = matchParticipant.getTimeline().getLane().toLane();
        this.rank = matchParticipant.getHighestAchievedSeasonTier();
        this.region = match.getPlatformId();
    }

    public ChampSpec(MatchDetail match, TeamBansDto bannedChamp) {
        this.championId = bannedChamp.getChampionId();
        this.matchType = match.getQueueId();
        this.lolVersion = match.getGameVersion();
        this.rank = null;
        this.lane = null;
        this.role = null;
        this.region = match.getPlatformId();
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

    public QueueType getMatchType() {
        return matchType;
    }

    public void setMatchType(QueueType matchType) {
        this.matchType = matchType;
    }

    public LolVersion getLolVersion() {
        return lolVersion;
    }

    public void setLolVersion(LolVersion lolVersion) {
        this.lolVersion = lolVersion;
    }

    public ChampSpecWinRate getChampSpecWinRate() {
        return champSpecWinRate;
    }

    public void setChampSpecWinRate(ChampSpecWinRate champSpecWinRate) {
        this.champSpecWinRate = champSpecWinRate;
    }

    @XmlTransient
    public List<ChampSummonerSpells> getChampSummonerSpellsList() {
        return champSummonerSpellsList;
    }

    public void setChampSummonerSpellsList(List<ChampSummonerSpells> champSummonerSpellsList) {
        this.champSummonerSpellsList = champSummonerSpellsList;
    }

    public void setLane(Lane lane) {
        this.lane = lane;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Lane getLane() {
        return lane;
    }

    public Role getRole() {
        return role;
    }

    public Rank getRank() {
        return rank;
    }

    public Region getRegion() {
        return region;
    }

    public List<ChampMatchup> getChampMatchupList() {
        return champMatchupList;
    }

    public List<ChampTeamup> getChampTeamupList() {
        return champTeamupList;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.championId;
        hash = 97 * hash + this.matchType.getQueueTypeId();
        hash = 97 * hash + this.lolVersion.getMajorVersion();
        hash = 97 * hash + this.lolVersion.getMinorVersion();
        hash = 97 * hash + Objects.hashCode(this.lane);
        hash = 97 * hash + Objects.hashCode(this.role);
        hash = 97 * hash + Objects.hashCode(this.rank);
        hash = 97 * hash + Objects.hashCode(this.region);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChampSpec)) {
            return false;
        }
        ChampSpec other = (ChampSpec) object;
        if (this.champSpecId != null && other.champSpecId != null) {
            return this.champSpecId.equals(other.champSpecId);
        }

        return this.championId == other.championId
                && Objects.equals(this.role, other.role)
                && this.lolVersion == other.lolVersion
                && Objects.equals(this.lane, other.lane)
                && Objects.equals(this.rank, other.rank)
                && Objects.equals(this.matchType, other.matchType)
                && Objects.equals(this.region, other.region);
    }

    @Override
    public String toString() {
        return "ChampSpec[ champSpecId=" + champSpecId + " ,"
                + "championId=" + this.championId
                + " lolVersion=" + this.lolVersion
                + " matchTpe=" + this.matchType
                + " role=" + this.role
                + " rank=" + this.rank
                + " lane=" + this.lane
                + " region=" + this.region
                + "]";
    }

    @XmlTransient
    public List<ChampFinalBuild> getChampFinalBuildList() {
        return champFinalBuildList;
    }

    public void setChampFinalBuildList(List<ChampFinalBuild> champFinalBuildList) {
        this.champFinalBuildList = champFinalBuildList;
    }

    @XmlTransient
    public List<ChampBan> getChampBanList() {
        return champBanList;
    }

    public void setChampBanList(List<ChampBan> champBanList) {
        this.champBanList = champBanList;
    }

}
