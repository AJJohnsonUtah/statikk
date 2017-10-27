/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.entity;

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
@Table(name = "champ_spec_win_rate")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ChampSpecWinRate.findAllGrouped", query = "SELECT NEW statikk.domain.stats.model.ChampionWinRate(c.champSpecWinRatePK.champSpec.championId, SUM(c.playedCount), SUM(c.winCount)) FROM ChampSpecWinRate c GROUP BY c.champSpecWinRatePK.champSpec.championId"),
    @NamedQuery(name = "ChampSpecWinRate.findAll", query = "SELECT c FROM ChampSpecWinRate c")})
public class ChampSpecWinRate extends BaseWinRateEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ChampSpecWinRatePK champSpecWinRatePK;

    public ChampSpecWinRate() {
    }

    public ChampSpecWinRate(ChampSpecWinRatePK champSpecWinRatePK) {
        super();
        this.champSpecWinRatePK = champSpecWinRatePK;
    }

    public ChampSpecWinRatePK getChampSpecWinRatePK() {
        return champSpecWinRatePK;
    }

    public void setChampSpecWinRatePK(ChampSpecWinRatePK champSpecWinRatePK) {
        this.champSpecWinRatePK = champSpecWinRatePK;
    }

    @Override
    public int hashCode() {
        return this.champSpecWinRatePK.hashCode();
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
        final ChampSpecWinRate other = (ChampSpecWinRate) obj;
        if (!Objects.equals(this.champSpecWinRatePK, other.champSpecWinRatePK)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ChampSpecWinRate{" + "winCount=" + winCount + ", playedCount=" + playedCount + ", champSpecWinRatePK=" + champSpecWinRatePK + '}';
    }

}
