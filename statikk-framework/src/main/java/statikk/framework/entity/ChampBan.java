/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.framework.entity;

import statikk.framework.entity.converter.QueueTypeConverter;
import statikk.framework.riotapi.model.QueueType;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NamedQuery(name = "ChampBan.findByChampSpecId", query = "SELECT c FROM ChampBan c WHERE c.champBanPK.champSpec = :champSpec"),
    @NamedQuery(name = "ChampBan.findByBanOrder", query = "SELECT c FROM ChampBan c WHERE c.champBanPK.banOrder = :banOrder"),
    @NamedQuery(name = "ChampBan.findByBanCount", query = "SELECT c FROM ChampBan c WHERE c.banCount = :banCount")})
public class ChampBan implements Serializable {

    @EmbeddedId
    protected ChampBanPK champBanPK;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ban_count")
    private int banCount;

    public ChampBan() {
    }

    public ChampBan(ChampBanPK champBanPK, int banCount) {
        this.champBanPK = champBanPK;
        this.banCount = banCount;
    }

    public ChampBanPK getChampBanPK() {
        return champBanPK;
    }

    public void setChampBanPK(ChampBanPK champBanPK) {
        this.champBanPK = champBanPK;
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
        hash += (champBanPK != null ? champBanPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChampBan)) {
            return false;
        }
        ChampBan other = (ChampBan) object;
        if ((this.champBanPK == null && other.champBanPK != null) || (this.champBanPK != null && !this.champBanPK.equals(other.champBanPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ChampBan[ champBanPK=" + champBanPK + " ]";
    }
    
    public void combine(ChampBan champBan) {
        this.banCount += champBan.getBanCount();
    }
}
