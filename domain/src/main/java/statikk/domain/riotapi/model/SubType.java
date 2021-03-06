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
public enum SubType {
    NONE("Custom games"),
    NORMAL("Summoner's Rift unranked games"),
    NORMAL_3x3("Twisted Treeline unranked games"),
    ODIN_UNRANKED("Dominion/Crystal Scar games"),
    ARAM_UNRANKED_5x5("ARAM / Howling Abyss games"),
    BOT("Summoner's Rift and Crystal Scar games played against Intro, Beginner, or Intermediate AI"),
    BOT_3x3("Twisted Treeline games played against AI"),
    RANKED_SOLO_5x5("Summoner's Rift ranked solo 5v5 games from earlier seasons and Summoner's Rift ranked solo games from current season that use Team Builder matchmaking"),
    RANKED_TEAM_3x3("Twisted Treeline ranked team games"),
    RANKED_TEAM_5x5("Summoner's Rift ranked team games"),
    ONEFORALL_5x5("One for All games"),
    FIRSTBLOOD_1x1("Snowdown Showdown 1x1 games"),
    FIRSTBLOOD_2x2("Snowdown Showdown 2x2 games"),
    SR_6x6("Summoner's Rift 6x6 Hexakill games"),
    CAP_5x5("Team Builder games"),
    URF("Ultra Rapid Fire games"),
    URF_BOT("Ultra Rapid Fire games played against AI"),
    NIGHTMARE_BOT("Summoner's Rift games played against Nightmare AI"),
    ASCENSION("Ascension games"),
    HEXAKILL("Twisted Treeline 6x6 Hexakill games"),
    KING_PORO("King Poro games"),
    COUNTER_PICK("Nemesis games"),
    BILGEWATER("Black Market Brawlers games"),
    SIEGE("Nexus Siege games"),
    RANKED_FLEX_TT("Ranked Flex Twisted Treeline games"),
    ARSR("All Random Summoner's Rift"),
    RANKED_FLEX_SR("Ranked Flex Summoner's Rift games"),
    DARKSTAR("Darkstar games");

    private final String description;

    SubType(String description) {
        this.description = description;
    }
}
