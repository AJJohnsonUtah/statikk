/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.entity.converter;

import com.njin.loltheory.entity.enums.MatchStatus;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author AJ
 */
@Converter
public class MatchStatusConverter implements AttributeConverter<MatchStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(MatchStatus x) {
        return x.getMatchStatusId();
    }

    @Override
    public MatchStatus convertToEntityAttribute(Integer valToConvert) {
        return MatchStatus.getMatchStatus(valToConvert);
    }
}
