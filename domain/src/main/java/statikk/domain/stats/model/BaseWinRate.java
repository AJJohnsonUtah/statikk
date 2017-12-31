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
            return  winCount / playedCount;
        }
    }
}
