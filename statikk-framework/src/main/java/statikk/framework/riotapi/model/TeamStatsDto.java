/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.framework.riotapi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author AJ
 */
public class TeamStatsDto implements Serializable {

    private List<TeamBansDto> bans;
    private int baronKills;
    private long dominionVictoryScore;
    private int dragonKills;
    private boolean firstBaron;
    private boolean firstBlood;
    private boolean firstDragon;
    private boolean firstInhibitor;
    private boolean firstRiftHerald;
    private boolean firstTower;
    private int inhibitorKills;
    private int riftHeraldKills;
    private LolTeam teamId;
    private int towerKills;
    private int vilemawKills;
    private WinStatus win;

    public TeamStatsDto() {
        bans = new ArrayList<>();
    }

    public List<TeamBansDto> getBans() {
        return bans;
    }

    public int getBaronKills() {
        return baronKills;
    }

    public long getDominionVictoryScore() {
        return dominionVictoryScore;
    }

    public int getDragonKills() {
        return dragonKills;
    }

    public boolean isFirstBaron() {
        return firstBaron;
    }

    public boolean isFirstBlood() {
        return firstBlood;
    }

    public boolean isFirstDragon() {
        return firstDragon;
    }

    public boolean isFirstInhibitor() {
        return firstInhibitor;
    }

    public boolean isFirstRiftHerald() {
        return firstRiftHerald;
    }

    public boolean isFirstTower() {
        return firstTower;
    }

    public int getInhibitorKills() {
        return inhibitorKills;
    }

    public int getRiftHeraldKills() {
        return riftHeraldKills;
    }

    public LolTeam getTeamId() {
        return teamId;
    }

    public int getTowerKills() {
        return towerKills;
    }

    public int getVilemawKills() {
        return vilemawKills;
    }

    public WinStatus getWin() {
        return win;
    }

    public boolean isWinner() {
        return win.equals(WinStatus.Win);
    }

}
