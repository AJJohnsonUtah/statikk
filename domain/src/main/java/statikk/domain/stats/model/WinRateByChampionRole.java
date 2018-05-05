/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.stats.model;

import statikk.domain.entity.enums.Role;

/**
 *
 * @author Grann
 */
public class WinRateByChampionRole extends BaseWinRate {

    private int championId;
    private Role role;

    public WinRateByChampionRole(int championId, Role role, long playedCount, long winCount) {
        super(winCount, playedCount);
        this.championId = championId;
        this.role = role;
    }

    public int getChampionId() {
        return championId;
    }

    public void setChampionId(int championId) {
        this.championId = championId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
