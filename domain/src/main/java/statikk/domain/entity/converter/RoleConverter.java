/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.entity.converter;

import statikk.domain.entity.enums.Role;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author AJ
 */
@Converter
public class RoleConverter implements AttributeConverter<Role, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Role x) {
        return x == null ? -1 : x.getRoleId();
    }

    @Override
    public Role convertToEntityAttribute(Integer valToConvert) {
        return valToConvert == -1 ? null : Role.fromId(valToConvert);
    }
}
