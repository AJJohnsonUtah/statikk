/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "lol_version")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LolVersion.findAll", query = "SELECT l FROM LolVersion l"),
    @NamedQuery(name = "LolVersion.findByLolVersionId", query = "SELECT l FROM LolVersion l WHERE l.lolVersionId = :lolVersionId"),
    @NamedQuery(name = "LolVersion.findByMajorVersion", query = "SELECT l FROM LolVersion l WHERE l.majorVersion = :majorVersion"),
    @NamedQuery(name = "LolVersion.findByMinorVersion", query = "SELECT l FROM LolVersion l WHERE l.minorVersion = :minorVersion")})
public class LolVersion implements Serializable {

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
        int hash = 0;
        hash += (lolVersionId != null ? lolVersionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LolVersion)) {
            return false;
        }
        LolVersion other = (LolVersion) object;
        if ((this.lolVersionId == null && other.lolVersionId != null) || (this.lolVersionId != null && !this.lolVersionId.equals(other.lolVersionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.njin.loltheory.model.LolVersion[ lolVersionId=" + lolVersionId + " ]";
    }
    
}
