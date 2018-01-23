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

    public static Lane getLane(Integer id) {
        return laneMap.get(id);
    }

    @JsonCreator
    public static Lane getLane(String id) {
        char firstChar = id.charAt(0);
        if (firstChar >= '0' && firstChar <= '9') {
            return getLane(Integer.parseInt(id));
        }
        return Lane.valueOf(id);
    }
}
