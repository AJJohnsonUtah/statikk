/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.framework.riotapi.model;

/**
 *
 * @author AJ
 */
public enum Region {
    BR("BR1"),
    EUNE("EUN1"),
    EUW("EUW1"),
    JP("JP1"),
    KR("KR"),
    LAN("LA1"),
    LAS("LA2"),
    NA("NA1"),
    OCE("OC1"),
    PBE("PBE1"),
    RU("RU"),
    TR("TR1");

    private final String platformId;
    
    private Region (String platformId) {
        this.platformId = platformId;
    }
    
    public String getPlatformId() {
        return this.platformId;
    }
}
