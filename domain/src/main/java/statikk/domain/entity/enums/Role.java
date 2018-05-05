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

    TANK(1, true),
    AP_CARRY(2, true),
    AD_CARRY(3, true),
    AP_TANK(4, true),
    AD_TANK(5, true),
    HYBRID(6, true),
    SUPPORT(7, true),
    UNDETERMINED(8, false);

    private Integer roleId;
    private boolean playable;

    public Integer getRoleId() {
        return roleId;
    }

    public boolean isPlayable() {
        return playable;
    }

    Role(Integer id, boolean playable) {
        this.roleId = id;
        this.playable = playable;
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
