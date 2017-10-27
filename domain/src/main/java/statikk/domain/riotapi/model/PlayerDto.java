/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.riotapi.model;

import java.io.Serializable;

/**
 *
 * @author AJ
 */
public class PlayerDto implements Serializable {

    private String matchHistoryUri;
    private int profileIcon;
    private Long summonerId;
    private String summonerName;
    private Region currentPlatformId;
    private Region platformId;
    private Long currentAccountId;
    private Long accountId;

    public String getMatchHistoryUri() {
        return matchHistoryUri;
    }

    public int getProfileIcon() {
        return profileIcon;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public Long getSummonerId() {
        return summonerId;
    }

    public Region getCurrentPlatformId() {
        return currentPlatformId;
    }

    public Region getPlatformId() {
        return platformId;
    }

    public Long getCurrentAccountId() {
        return currentAccountId;
    }

    public Long getAccountId() {
        return accountId;
    }

}
