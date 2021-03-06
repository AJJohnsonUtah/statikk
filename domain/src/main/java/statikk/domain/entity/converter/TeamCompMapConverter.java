/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.entity.converter;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.Converter;
import statikk.domain.entity.enums.Role;

/**
 *
 * @author Grann
 */
public class TeamCompMapConverter {

    public static String convertToString(Map<Role, Integer> roleMap) {
        int[] mapChars = new int[Role.values().length];
        roleMap.keySet().forEach((role) -> {
            mapChars[role.getRoleId() - 1] = roleMap.get(role);
        });
        String stringVal = "";
        for (int i = 0; i < mapChars.length; i++) {
            stringVal += mapChars[i];
        }
        return stringVal.replaceAll("([1-9]+)0+$", "$1");
    }

    public static Map<Role, Integer> convertToMap(String val) {
        Map<Role, Integer> roleMap = new HashMap<>();
        for (int i = 0; i < val.length(); i++) {
            int roleCount = Integer.parseInt(String.valueOf(val.charAt(i)));
            if (roleCount > 0) {
                Role role = Role.fromId(i + 1);
                roleMap.put(role, roleCount);
            }
        }
        return roleMap;
    }

}
