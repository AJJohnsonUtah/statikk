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

    protected double winCount;
    protected double playedCount;

    public BaseWinRate(Long winCount, Long playedCount) {
        if (winCount == null) {
            this.winCount = 0;
        } else {
            this.winCount = winCount;
        }
        if (playedCount == null) {
            this.playedCount = 0;
        } else {
            this.playedCount = playedCount;
        }

    }

    public double getWinCount() {
        return winCount;
    }

    public void setWinCount(double winCount) {
        this.winCount = winCount;
    }

    public double getPlayedCount() {
        return playedCount;
    }

    public void setPlayedCount(double playedCount) {
        this.playedCount = playedCount;
    }

    public Double getWinRate() {
        if (this.playedCount == 0) {
            return null;
        } else {
            return winCount / playedCount;
        }
    }

    public int comparePlayedCount(Object o) {
        BaseWinRate other = (BaseWinRate) o;
        if(this.playedCount == other.playedCount) {
            return 0;
        } else if(this.playedCount - other.playedCount > 0) {
            return 1;
        } else {
            return -1;
        }
    }
}
