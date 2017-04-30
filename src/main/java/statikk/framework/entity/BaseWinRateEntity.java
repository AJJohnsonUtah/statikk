/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.framework.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 *
 * @author AJ
 */
@MappedSuperclass
public abstract class BaseWinRateEntity {

    @Basic(optional = false)
    @NotNull
    @Column(name = "win_count")
    protected long winCount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "played_count")
    protected long playedCount;

    protected BaseWinRateEntity() {
        this.playedCount = 0;
        this.winCount = 0;
    }

    public long getWinCount() {
        return winCount;
    }

    public void setWinCount(long winCount) {
        this.winCount = winCount;
    }

    public long getPlayedCount() {
        return playedCount;
    }

    public void setPlayedCount(long playedCount) {
        this.playedCount = playedCount;
    }

    public void addWin() {
        this.playedCount++;
        this.winCount++;
    }

    public void addLoss() {
        this.playedCount++;
    }

    public void combine(BaseWinRateEntity baseWinRateEntity) {
        this.winCount += baseWinRateEntity.winCount;
        this.playedCount += baseWinRateEntity.playedCount;
    }

}
