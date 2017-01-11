/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.entity.converter;

import com.njin.loltheory.entity.enums.Lane;
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
        return x == null ? null : x.getLaneId();
    }

    @Override
    public Lane convertToEntityAttribute(Integer valToConvert) {
        return valToConvert == null ? null : Lane.getLane(valToConvert);
    }
}
