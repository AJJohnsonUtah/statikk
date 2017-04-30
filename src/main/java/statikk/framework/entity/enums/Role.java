/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.framework.entity.enums;

import java.util.HashMap;

/**
 *
 * @author AJ
 */
public enum Role {

    TANK(1), AP_CARRY(2), AD_CARRY(3), AP_TANK(4), AD_TANK(5), HYBRID(6), ASSASSIN(7), AP_ASSASSIN(8), AD_ASSASSIN(9), SUPPORT(10);

    private int roleId;

    public int getRoleId() {
        return roleId;
    }

    Role(int id) {
        this.roleId = id;
    }

    private static final HashMap<Integer, Role> roleMap;

    static {
        roleMap = new HashMap<>();
        for (Role role : values()) {
            roleMap.put(role.roleId, role);
        }
    }

    public static Role getRole(int id) {
        return roleMap.get(id);
    }
}
