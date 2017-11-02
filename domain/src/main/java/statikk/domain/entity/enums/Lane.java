/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.HashMap;

/**
 *
 * @author AJ
 */
public enum Lane {
    MIDDLE(1), BOTTOM(2), TOP(3), JUNGLE(4), SUPPORT(5), ROAM(6);

    private Integer laneId;

    Lane(Integer id) {
        this.laneId = id;
    }

    public Integer getLaneId() {
        return laneId;
    }

    private final static HashMap<Integer, Lane> laneMap;

    static {
        laneMap = new HashMap<>();
        for (Lane lane : values()) {
            laneMap.put(lane.laneId, lane);
        }
    }

    @JsonCreator
    public static Lane getLane(Integer id) {
        return laneMap.get(id);
    }

}
