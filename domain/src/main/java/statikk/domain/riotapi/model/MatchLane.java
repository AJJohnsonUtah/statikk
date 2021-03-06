/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.riotapi.model;

import statikk.domain.entity.enums.Lane;

/**
 *
 * @author AJ
 */
public enum MatchLane {
    MID, MIDDLE, TOP, JUNGLE, BOT, BOTTOM, NONE;

    public Lane toLane() {
        switch (this) {
            case MID:
            case MIDDLE:
                return Lane.MIDDLE;
            case TOP:
                return Lane.TOP;
            case BOT:
            case BOTTOM:
                return Lane.BOTTOM;
            case JUNGLE:
                return Lane.JUNGLE;
        }
        return null;
    }
}
