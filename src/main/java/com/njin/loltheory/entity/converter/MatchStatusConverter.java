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
        switch (x) {
            case IN_PROGRESS:
                return 1;
            case COMPLETED:
                return 2;
            case READY:
                return 3;
        }
        throw new RuntimeException(x + " is an invalid match status");
    }

    @Override
    public MatchStatus convertToEntityAttribute(Integer valToConvert) {
        switch (valToConvert) {
            case 1:
                return MatchStatus.IN_PROGRESS;
            case 2:
                return MatchStatus.COMPLETED;
            case 3:
                return MatchStatus.READY;
        }
        throw new RuntimeException(valToConvert + " is an invalid match status");
    }
}
