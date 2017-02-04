/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.entity.converter;

import com.njin.loltheory.riotapi.model.Rank;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author AJ
 */
@Converter
public class RankConverter implements AttributeConverter<Rank, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Rank x) {
        return x == null ? -1 : x.getRankId();
    }

    @Override
    public Rank convertToEntityAttribute(Integer valToConvert) {
        return valToConvert == -1 ? null : Rank.fromId(valToConvert);
    }
}
