/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.framework.riotapi.model;

import java.util.EnumSet;

/**
 *
 * @author AJ
 */
public enum EventType {
    ASCENDED_EVENT, BUILDING_KILL, CAPTURE_POINT, CHAMPION_KILL, ELITE_MONSTER_KILL, ITEM_DESTROYED, ITEM_PURCHASED, ITEM_SOLD, ITEM_UNDO, PORO_KING_SUMMON, SKILL_LEVEL_UP, WARD_KILL, WARD_PLACED;

    public boolean isItemEvent() {
        return EnumSet.of(ITEM_PURCHASED, ITEM_DESTROYED, ITEM_SOLD, ITEM_UNDO).contains(this);
    }
}
