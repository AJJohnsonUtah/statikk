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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AJ
 */
@Entity
@Table(name = "champ_matchup")
@XmlRootElement
public class ChampMatchup extends BaseWinRateEntity implements Serializable {

    @EmbeddedId
    protected ChampMatchupPK champMatchupPK;

    private static final long serialVersionUID = 1L;

    public ChampMatchup() {
    }

    public ChampMatchup(ChampMatchupPK champMatchupPK) {
        super();
        this.champMatchupPK = champMatchupPK;
    }

    public ChampMatchup(ChampMatchupPK champMatchupPK, long winCount, long playedCount) {
        this.champMatchupPK = champMatchupPK;
        this.winCount = winCount;
        this.playedCount = playedCount;
    }

    public ChampMatchupPK getChampMatchupPK() {
        return champMatchupPK;
    }

    public void setChampMatchupPK(ChampMatchupPK champMatchupPK) {
        this.champMatchupPK = champMatchupPK;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.champMatchupPK);
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
        final ChampMatchup other = (ChampMatchup) obj;
        if (!Objects.equals(this.champMatchupPK, other.champMatchupPK)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ChampMatchup{" + "champMatchupPK=" + champMatchupPK + ", winCount=" + winCount + ", playedCount=" + playedCount + '}';
    }

}
