/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.entity.converter;

import statikk.domain.entity.enums.Lane;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author AJ
 */
@Converter
public class LaneConverter implements AttributeConverter<Lane, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Lane x) {
        return x == null ? -1 : x.getLaneId();
    }

    @Override
    public Lane convertToEntityAttribute(Integer valToConvert) {
        return valToConvert == -1 ? null : Lane.getLane(valToConvert);
    }
}
