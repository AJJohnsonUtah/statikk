package statikk.framework.entity.converter;

import statikk.framework.riotapi.model.QueueType;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author AJ
 */
@Converter
public class QueueTypeConverter implements AttributeConverter<QueueType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(QueueType x) {
        return x.getQueueTypeId();
    }

    @Override
    public QueueType convertToEntityAttribute(Integer valToConvert) {
        return QueueType.fromId(valToConvert);
    }
}
