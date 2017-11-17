/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.stats.model;

/**
 *
 * @author AJ
 */
public class BaseWinRate {

    protected long winCount;
    protected long playedCount;

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

    public Double getWinRate() {
        if (this.playedCount == 0 || this.winCount == 0) {
            return null;
        } else {
            return ((double) winCount) / playedCount;
        }
    }
}
