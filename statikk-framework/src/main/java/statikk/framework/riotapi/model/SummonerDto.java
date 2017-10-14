/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.framework.riotapi.model;

import java.io.Serializable;

/**
 *
 * @author AJ
 */
public class SummonerDto implements Serializable {

    /*
        "profileIconId": 774,
        "name": "GrannysCookies",
        "summonerLevel": 30,
        "accountId": 42264797,
        "id": 27673684,
        "revisionDate": 1493436310000
     */
    private long id;
    private long accountId;
    private String name;
    private int profileIconId;
    private long revisionDate;
    private long summonerLevel;

    public static String getKeyFromName(String summonerName) {
        return summonerName.replaceAll(" ", "").toLowerCase();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getProfileIconId() {
        return profileIconId;
    }

    public long getRevisionDate() {
        return revisionDate;
    }

    public long getSummonerLevel() {
        return summonerLevel;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

}
