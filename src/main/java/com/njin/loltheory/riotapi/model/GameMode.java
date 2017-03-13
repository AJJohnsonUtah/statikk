/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.riotapi.model;

/**
 *
 * @author AJ
 */
public enum GameMode {
    CLASSIC("Classic Summoner's Rift and Twisted Treeline games"),
    ODIN("Dominion/Crystal Scar games"),
    ARAM("ARAM games"),
    TUTORIAL("Tutorial games"),
    ONEFORALL("One for All games"),
    ASCENSION("Ascension games"),
    FIRSTBLOOD("Snowdown Showdown games"),
    KINGPORO("King Poro games"),
    SIEGE("Nexus Siege games"),
    URF("Ultra Rapid Fire");

    private final String description;

    GameMode(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
