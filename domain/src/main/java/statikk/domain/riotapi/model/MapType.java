/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.riotapi.model;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 *
 * @author AJ
 */
public enum MapType {
    OrigSummerRift(1, "Summoner's Rift", "Original Summer Variant"),
    OrigAutumnRift(2, "Summoner's Rift", "Original Autumn Variant"),
    Tutorial(3, "The Proving Grounds", "Tutorial Map"),
    OrigTreeline(4, "Twisted Treeline", "Original Version"),
    Dominion(8, "The Crystal Scar", "Dominion Map"),
    Treeline(10, "Twisted Treeline", "Current Version"),
    Rift(11, "Summoner's Rift", "Current Version"),
    ARAM(12, "Howling Abyss", "ARAM Map"),
    ARAMButcher(14,
            "Butcher's Bridge", "ARAM Map"),
    CosmicRuins(16,
            "Cosmic Ruins", "Darkstar Map"),
    ValoranCityPark(18, "Valoran City Park", "Star Guardian Invasion Map");

    private final int mapId;
    private final String name;
    private final String description;

    public int getMapId() {
        return mapId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    MapType(int mapId, String name, String description) {
        this.mapId = mapId;
        this.name = name;
        this.description = description;
    }

    @JsonCreator
    public static MapType fromMapId(int mapId) {
        for (MapType mapType : values()) {
            if (mapId == mapType.getMapId()) {
                return mapType;
            }
        }
        return null;
    }

}
