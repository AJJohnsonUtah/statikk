/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.framework.entity.converter;

import statikk.framework.entity.enums.MatchStatus;
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
