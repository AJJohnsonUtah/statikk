/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.HashMap;

/**
 *
 * @author AJ
 */
public enum Role {

    TANK(1), AP_CARRY(2), AD_CARRY(3), AP_TANK(4), AD_TANK(5), HYBRID(6), ASSASSIN(7), AP_ASSASSIN(8), AD_ASSASSIN(9), SUPPORT(10), UNDETERMINED(11);

    private Integer roleId;

    public Integer getRoleId() {
        return roleId;
    }

    Role(Integer id) {
        this.roleId = id;
    }

    private static final HashMap<Integer, Role> roleMap;

    static {
        roleMap = new HashMap<>();
        for (Role role : values()) {
            roleMap.put(role.roleId, role);
        }
    }

    public static Role fromId(Integer id) {
        return roleMap.get(id);
    }

    @JsonCreator
    public static Role getRole(String id) {
        char firstChar = id.charAt(0);
        if (firstChar >= '0' && firstChar <= '9') {
            return fromId(Integer.parseInt(id));
        }
        return Role.valueOf(id);
    }
}
