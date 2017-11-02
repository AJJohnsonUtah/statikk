/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.riotapi.model;

import java.util.HashMap;

/**
 *
 * @author AJ
 */
public enum Rank {
    CHALLENGER(1), MASTER(2), DIAMOND(3), PLATINUM(4), GOLD(5), SILVER(6), BRONZE(7), UNRANKED(8);
    private final Integer rankId;

    Rank(Integer id) {
        this.rankId = id;
    }

    public Integer getRankId() {
        return rankId;
    }

    private final static HashMap<Integer, Rank> rankMap;

    static {
        rankMap = new HashMap<>();
        for (Rank rank : values()) {
            rankMap.put(rank.rankId, rank);
        }
    }

    public static Rank fromId(Integer rankId) {
        return rankMap.get(rankId);
    }

}
