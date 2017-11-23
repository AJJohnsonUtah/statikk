/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.stats.model;

import statikk.domain.entity.enums.Role;

/**
 *
 * @author AJ
 */
public class WinRateByRole extends BaseWinRate {

    private final Role role;

    public WinRateByRole(Role role, long playedCount, long winCount) {
        this.playedCount = playedCount;
        this.winCount = winCount;
        this.role = role;
    }

    public Role getRole() {
        return this.role;
    }

}
