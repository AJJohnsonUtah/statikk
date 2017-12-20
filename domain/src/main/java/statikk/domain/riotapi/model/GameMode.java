/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.riotapi.model;

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
    ARSR("All Random Summoner's Rift"),
    URF("Ultra Rapid Fire"),
    ASSASSINATE("Blood Hunt Assassin games"),
    DARKSTAR("Darkstar games"),
    DOOMBOTSTEEMO("Doom Bot games"),
    STARGUARDIAN("Star Guardian Invasion games"),
    PROJECT("Overdrive Project games"),
    SNOWURG("Snow Urf games");
    

    private final String description;

    GameMode(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
