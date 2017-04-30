/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.framework.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author AJ
 */
@Entity
@Table(name = "lol_version")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LolVersion.findAll", query = "SELECT l FROM LolVersion l"),
    @NamedQuery(name = "LolVersion.findByLolVersionId", query = "SELECT l FROM LolVersion l WHERE l.lolVersionId = :lolVersionId"),
    @NamedQuery(name = "LolVersion.findByMajorVersion", query = "SELECT l FROM LolVersion l WHERE l.majorVersion = :majorVersion"),
    @NamedQuery(name = "LolVersion.findByMinorVersion", query = "SELECT l FROM LolVersion l WHERE l.minorVersion = :minorVersion"),
    @NamedQuery(name = "LolVersion.find", query = "SELECT l FROM LolVersion l WHERE l.minorVersion = :minorVersion AND l.majorVersion = :majorVersion")})
public class LolVersion implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lolVersionId", fetch = FetchType.LAZY)
    private List<ChampSpec> champSpecList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "lol_version_id")
    private Integer lolVersionId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "major_version")
    private int majorVersion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "minor_version")
    private int minorVersion;

    public LolVersion(String version) {
        String[] parts = version.split("\\.");
        this.majorVersion = Integer.parseInt(parts[0]);
        this.minorVersion = Integer.parseInt(parts[1]);
    }

    public LolVersion() {
    }

    public LolVersion(Integer lolVersionId) {
        this.lolVersionId = lolVersionId;
    }

    public LolVersion(Integer lolVersionId, int majorVersion, int minorVersion) {
        this.lolVersionId = lolVersionId;
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
    }

    public Integer getLolVersionId() {
        return lolVersionId;
    }

    public void setLolVersionId(Integer lolVersionId) {
        this.lolVersionId = lolVersionId;
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public void setMajorVersion(int majorVersion) {
        this.majorVersion = majorVersion;
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    public void setMinorVersion(int minorVersion) {
        this.minorVersion = minorVersion;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.majorVersion;
        hash = 29 * hash + this.minorVersion;
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
        final LolVersion other = (LolVersion) obj;
        if (this.majorVersion != other.majorVersion) {
            return false;
        }
        if (this.minorVersion != other.minorVersion) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "statikk.framework.model.LolVersion[ lolVersionId=" + lolVersionId + ", " + majorVersion + "." + minorVersion + " ]";
    }

    @XmlTransient
    public List<ChampSpec> getChampSpecList() {
        return champSpecList;
    }

    public void setChampSpecList(List<ChampSpec> champSpecList) {
        this.champSpecList = champSpecList;
    }
}
