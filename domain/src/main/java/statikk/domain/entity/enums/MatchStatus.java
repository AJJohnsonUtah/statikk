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
    COMPLETED(0), IN_PROGRESS(1), READY(2), DATA_NOT_FOUND(3), ERROR_IN_ANALYSIS(4), MATCH_TOO_SHORT(5), MATCH_VERSION_NOT_CURRENT(6);

    private Integer matchStatusId;

    MatchStatus(Integer id) {
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

    public Integer getMatchStatusId() {
        return matchStatusId;
    }

}
