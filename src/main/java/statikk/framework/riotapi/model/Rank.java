/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.framework.riotapi.model;

import java.util.HashMap;

/**
 *
 * @author AJ
 */
public enum Rank {
    CHALLENGER(1), MASTER(2), DIAMOND(3), PLATINUM(4), GOLD(5), SILVER(6), BRONZE(7), UNRANKED(8);
    private final int rankId;

    Rank(int id) {
        this.rankId = id;
    }

    public int getRankId() {
        return rankId;
    }

    private final static HashMap<Integer, Rank> rankMap;

    static {
        rankMap = new HashMap<>();
        for (Rank rank : values()) {
            rankMap.put(rank.rankId, rank);
        }
    }

    public static Rank fromId(int rankId) {
        return rankMap.get(rankId);
    }

}
