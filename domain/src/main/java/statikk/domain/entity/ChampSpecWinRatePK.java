/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author AJ
 */
@Embeddable
public class ChampSpecWinRatePK implements Serializable {

    @JoinColumn(name = "champ_spec_id", referencedColumnName = "champ_spec_id")
    @ManyToOne(optional = false)
    private ChampSpec champSpec;

    public ChampSpecWinRatePK() {
    }

    public ChampSpecWinRatePK(ChampSpec champSpec) {
        this.champSpec = champSpec;
    }

    public ChampSpec getChampSpec() {
        return champSpec;
    }

    public void setChampSpec(ChampSpec champSpec) {
        this.champSpec = champSpec;
    }

    @Override
    public int hashCode() {
        return champSpec.hashCode();
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
        final ChampSpecWinRatePK other = (ChampSpecWinRatePK) obj;
        if (!Objects.equals(this.champSpec, other.champSpec)) {
            return false;
        }
        return true;
    }

}
