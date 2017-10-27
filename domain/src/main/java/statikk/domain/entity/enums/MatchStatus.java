/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.entity.enums;

import java.util.HashMap;

/**
 *
 * @author AJ
 */
public enum MatchStatus {
    COMPLETED(0), IN_PROGRESS(1), READY(2);

    private int matchStatusId;

    MatchStatus(int id) {
        this.matchStatusId = id;
    }

    private static final HashMap<Integer, MatchStatus> matchStatusMap;

    public static MatchStatus getMatchStatus(Integer id) {
        return matchStatusMap.get(id);
    }

    static {
        matchStatusMap = new HashMap<>();
        for (MatchStatus matchStatus : values()) {
            matchStatusMap.put(matchStatus.matchStatusId, matchStatus);
        }
    }

    public int getMatchStatusId() {
        return matchStatusId;
    }

}
