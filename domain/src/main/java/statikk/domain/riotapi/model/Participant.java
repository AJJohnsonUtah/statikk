/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.riotapi.model;

/**
 *
 * @author AJ
 */
public class Participant {

    private boolean bot;
    private long championId;
    private long profileIconId;
    private long spell1Id;
    private long spell2Id;
    private String summonerName;
    private long teamId;

    public boolean isBot() {
        return bot;
    }

    public long getChampionId() {
        return championId;
    }

    public long getProfileIconId() {
        return profileIconId;
    }

    public long getSpell1Id() {
        return spell1Id;
    }

    public long getSpell2Id() {
        return spell2Id;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public long getTeamId() {
        return teamId;
    }

}
