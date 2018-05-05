/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AJ
 */
@Entity
@Table(name = "final_build_order")
@XmlRootElement
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

    @Basic(optional = false)
    @Column(name = "item_count")
    private int itemCount;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "champFinalBuildPK.finalBuildOrder", fetch = FetchType.LAZY)
    private List<ChampFinalBuild> champFinalBuildList;

    public FinalBuildOrder() {
    }

    public FinalBuildOrder(List<Integer> buildItems) {
        this.itemCount = buildItems.size();
        switch (buildItems.size()) {
            case 0:
                this.buildOrder = "";
                break;
            case 1:
                this.buildOrder = buildItems.get(0).toString();
                break;
            default:
                this.buildOrder = buildItems.stream()
                        .map((val) -> String.valueOf(val))
                        .reduce("", (a, b) -> a +  b + ",");
                this.buildOrder = this.buildOrder.substring(0, this.buildOrder.length() - 1);
                break;
        }
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

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
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

    @JsonIgnore
    public List<ChampFinalBuild> getChampFinalBuildList() {
        return champFinalBuildList;
    }

    public void setChampFinalBuildList(List<ChampFinalBuild> champFinalBuildList) {
        this.champFinalBuildList = champFinalBuildList;
    }

    public List<Integer> getBuildItemIds() {
        if (this.buildOrder == null || this.buildOrder.length() == 0) {
            return Collections.EMPTY_LIST;
        }
        return Arrays.asList(this.buildOrder.split(",")).stream().map(itemString -> Integer.parseInt(itemString)).collect(Collectors.toList());
    }
}
