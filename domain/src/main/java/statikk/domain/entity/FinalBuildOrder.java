/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author AJ
 */
@Entity
@Table(name = "final_build_order")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FinalBuildOrder.findAll", query = "SELECT f FROM FinalBuildOrder f"),
    @NamedQuery(name = "FinalBuildOrder.findByFinalBuildOrderId", query = "SELECT f FROM FinalBuildOrder f WHERE f.finalBuildOrderId = :finalBuildOrderId"),
    @NamedQuery(name = "FinalBuildOrder.findByBuildOrder", query = "SELECT f FROM FinalBuildOrder f WHERE f.buildOrder = :buildOrder")})
public class FinalBuildOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "final_build_order_id")
    private Long finalBuildOrderId;

    @Basic(optional = false)
    @Column(name = "build_order", nullable = false, length = 255)
    private String buildOrder;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "champFinalBuildPK.finalBuildOrder", fetch = FetchType.LAZY)
    private List<ChampFinalBuild> champFinalBuildList;

    public FinalBuildOrder() {
    }

    public FinalBuildOrder(Long finalBuildOrderId) {
        this.finalBuildOrderId = finalBuildOrderId;
    }

    public FinalBuildOrder(Long finalBuildOrderId, String buildOrder) {
        this.finalBuildOrderId = finalBuildOrderId;
        this.buildOrder = buildOrder;
    }

    public FinalBuildOrder(String buildOrder) {
        this.buildOrder = buildOrder;
    }

    public Long getFinalBuildOrderId() {
        return finalBuildOrderId;
    }

    public void setFinalBuildOrderId(Long finalBuildOrderId) {
        this.finalBuildOrderId = finalBuildOrderId;
    }

    public String getBuildOrder() {
        return buildOrder;
    }

    public void setBuildOrder(String buildOrder) {
        this.buildOrder = buildOrder;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (finalBuildOrderId != null ? finalBuildOrderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinalBuildOrder)) {
            return false;
        }
        FinalBuildOrder other = (FinalBuildOrder) object;
        if ((this.finalBuildOrderId == null && other.finalBuildOrderId != null) || (this.finalBuildOrderId != null && !this.finalBuildOrderId.equals(other.finalBuildOrderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "statikk.framework.model.FinalBuildOrder[ finalBuildOrderId=" + finalBuildOrderId + " ]";
    }

    @XmlTransient
    public List<ChampFinalBuild> getChampFinalBuildList() {
        return champFinalBuildList;
    }   

    public void setChampFinalBuildList(List<ChampFinalBuild> champFinalBuildList) {
        this.champFinalBuildList = champFinalBuildList;
    }

    public List<Integer> getBuildItemIds() {
        if(this.buildOrder == null || this.buildOrder.length() == 0) {
            return Collections.EMPTY_LIST;
        }
        return Arrays.asList(this.buildOrder.split(",")).stream().map(itemString -> Integer.parseInt(itemString)).collect(Collectors.toList());
    }
}
