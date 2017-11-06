/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.entity.converter;

import javax.persistence.AttributeConverter;
import statikk.domain.riotapi.model.Region;

/**
 *
 * @author AJ
 */
public class RegionConverter  implements AttributeConverter<Region, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Region x) {
        return x.getRegionId();
    }

    @Override
    public Region convertToEntityAttribute(Integer valToConvert) {
        return Region.fromId(valToConvert);
    }
    
}
