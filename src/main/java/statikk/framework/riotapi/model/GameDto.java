/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.framework.riotapi.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author AJ
 */
public class GameDto implements Serializable {

    private int championId;
    private long createDate;
    private List<PlayerDto> fellowPlayers;
    private long gameId;
    private GameMode gameMode;
    private GameType gameType;
    private boolean invalid;
    private int ipEarned;
    private int level;
    private MapType mapType;
    private int spell1;
    private int spell2;
    private SubType subType;
    private int teamId;
    
    public GameDto() {
        fellowPlayers = Collections.EMPTY_LIST;
    };

    public long getGameId() {
        return gameId;
    }

    public int getChampionId() {
        return championId;
    }

    public long getCreateDate() {
        return createDate;
    }

    public List<PlayerDto> getFellowPlayers() {
        return fellowPlayers;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public GameType getGameType() {
        return gameType;
    }

    public boolean isInvalid() {
        return invalid;
    }

    public int getIpEarned() {
        return ipEarned;
    }

    public int getLevel() {
        return level;
    }

    public MapType getMapType() {
        return mapType;
    }

    public int getSpell1() {
        return spell1;
    }

    public int getSpell2() {
        return spell2;
    }

    public SubType getSubType() {
        return subType;
    }

    public int getTeamId() {
        return teamId;
    }
}
