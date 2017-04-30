/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.framework.entity.enums;

import java.util.HashMap;

/**
 *
 * @author AJ
 */
public enum Lane {
    MIDDLE(1), BOTTOM(2), TOP(3), JUNGLE(4), SUPPORT(5), ROAM(6);

    private int laneId;

    Lane(int id) {
        this.laneId = id;
    }

    public int getLaneId() {
        return laneId;
    }

    private final static HashMap<Integer, Lane> laneMap;

    static {
        laneMap = new HashMap<>();
        for (Lane lane : values()) {
            laneMap.put(lane.laneId, lane);
        }
    }

    public static Lane getLane(int id) {
        return laneMap.get(id);
    }

}
