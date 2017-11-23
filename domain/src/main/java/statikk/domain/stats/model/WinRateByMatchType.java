/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.stats.model;

import statikk.domain.riotapi.model.QueueType;

/**
 *
 * @author AJ
 */
public class WinRateByMatchType extends BaseWinRate {

    private final QueueType queueType;

    public WinRateByMatchType(QueueType queueType, long playedCount, long winCount) {
        this.queueType = queueType;
        this.playedCount = playedCount;
        this.winCount = winCount;
    }

    public QueueType getMatchType() {
        return this.queueType;
    }
}
