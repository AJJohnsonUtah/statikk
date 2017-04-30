/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.framework.riotapi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.HashMap;

/**
 *
 * @author AJ
 */
public enum LolTeam {
    BLUE(100), PURPLE(200);

    private final int teamId;

    LolTeam(int id) {
        this.teamId = id;
    }

    public int getTeamId() {
        return teamId;
    }

    private static final HashMap<Integer, LolTeam> lolTeamMap;
    
    static {
        lolTeamMap = new HashMap<>();
        for (LolTeam lolTeam : values()) {
            lolTeamMap.put(lolTeam.teamId, lolTeam);
        }
    }

    @JsonCreator
    public static LolTeam fromId(int id) {
        return lolTeamMap.get(id);
    }
}
