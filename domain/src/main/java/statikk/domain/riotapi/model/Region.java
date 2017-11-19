/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.riotapi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.HashMap;

/**
 *
 * @author AJ
 */
public enum Region {
    BR("BR1", 2),
    EUNE("EUN1", 3),
    EUW("EUW1", 4),
    JP("JP1", 5),
    KR("KR", 6),
    LAN("LA1", 7),
    LAS("LA2", 8),
    NA("NA1", 1),
    OCE("OC1", 9),
    PBE("PBE1", 10),
    RU("RU", 11),
    TR("TR1", 12);

    private final String platformId;
    private final int regionId;

    private Region(String platformId, int regionId) {
        this.platformId = platformId;
        this.regionId = regionId;
    }

    public String getPlatformId() {
        return this.platformId;
    }

    public int getRegionId() {
        return this.regionId;
    }

    public boolean isPublic() {
        return !this.equals(Region.PBE);
    }

    private static final HashMap<String, Region> regionMap;
    private static final HashMap<Integer, Region> regionIdMap;

    static {
        regionMap = new HashMap<>();
        regionIdMap = new HashMap<>();
        for (Region region : values()) {
            regionIdMap.put(region.getRegionId(), region);
            regionMap.put(Integer.toString(region.getRegionId()), region);
            regionMap.put(region.getPlatformId(), region);
            regionMap.put(region.name(), region);
        }
    }

    @JsonCreator
    public static Region fromString(String regionId) {
        return regionMap.get(regionId);
    }

    public static Region fromId(int regionId) {
        return regionIdMap.get(regionId);
    }

}
