/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.entity.converter;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import statikk.domain.entity.enums.Role;

/**
 *
 * @author Grann
 */
@Converter
public class TeamCompMapConverter implements AttributeConverter<Map<Role, Integer>, Long> {

    @Override
    public Long convertToDatabaseColumn(Map<Role, Integer> x) {
        return Long.valueOf(convertToString(x));
    }

    @Override
    public Map<Role, Integer> convertToEntityAttribute(Long valToConvert) {
        String val = String.valueOf(valToConvert);
        Map<Role, Integer> roleMap = new HashMap<>();
        for (int i = 0; i < val.length(); i++) {
            char roleCount = val.charAt(i);
            if (roleCount > 0) {
                Role role = Role.fromId(i + 1);
                roleMap.put(role, Integer.parseInt(String.valueOf(roleCount)));
            }
        }
        return roleMap;
    }

    public static String convertToString(Map<Role, Integer> roleMap) {
        int[] mapChars = new int[Role.values().length];
        roleMap.keySet().forEach((role) -> {
            mapChars[role.getRoleId() - 1] = roleMap.get(role);
        });
        String stringVal = "";
        for (int i = 0; i < mapChars.length; i++) {
            stringVal += mapChars[i];
        }
        return stringVal;
    }
}
