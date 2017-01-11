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
@Table(name = "champ_final_build")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ChampFinalBuild.findAll", query = "SELECT c FROM ChampFinalBuild c"),
    @NamedQuery(name = "ChampFinalBuild.findByChampSpecId", query = "SELECT c FROM ChampFinalBuild c WHERE c.champFinalBuildPK.champSpec = :champSpec"),
    @NamedQuery(name = "ChampFinalBuild.findByFinalBuildOrder", query = "SELECT c FROM ChampFinalBuild c WHERE c.champFinalBuildPK.finalBuildOrder = :finalBuildOrder")})
public class ChampFinalBuild extends BaseWinRateEntity implements Serializable {

    @EmbeddedId
    protected ChampFinalBuildPK champFinalBuildPK;

    private static final long serialVersionUID = 1L;

    public ChampFinalBuild() {
    }

    public ChampFinalBuild(ChampFinalBuildPK champFinalBuildPK) {
        super();
        this.champFinalBuildPK = champFinalBuildPK;
    }

    public ChampFinalBuildPK getChampFinalBuildPK() {
        return champFinalBuildPK;
    }

    public void setChampFinalBuildPK(com.njin.loltheory.entity.ChampFinalBuildPK champFinalBuildPK) {
        this.champFinalBuildPK = champFinalBuildPK;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.champFinalBuildPK);
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
        final ChampFinalBuild other = (ChampFinalBuild) obj;
        if (!Objects.equals(this.champFinalBuildPK, other.champFinalBuildPK)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ChampFinalBuild{" + "champFinalBuildPK=" + champFinalBuildPK + ", playedCount=" + playedCount + ", winCount=" + winCount + '}';
    }

}
