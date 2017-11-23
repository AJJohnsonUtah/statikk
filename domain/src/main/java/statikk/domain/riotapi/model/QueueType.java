/**
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *
 */
package statikk.domain.riotapi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.HashMap;

/**
 *
 * @author AJ
 *
 *
 *
 *
 *
 */
public enum QueueType {
    CUSTOM(0, "Custom games"),
    NORMAL_3x3(
            8, "Normal 3v3 games"),
    NORMAL_5x5_BLIND(
            2, "Normal 5v5 Blind Pick games"),
    NORMAL_5x5_DRAFT(
            14, "Normal 5v5 Draft Pick games"),
    RANKED_SOLO_5x5(
            4, "Ranked Solo 5v5 games from earlier seasons"),
    RANKED_PREMADE_5x5(
            6, "Ranked Premade 5v5 games"),
    RANKED_PREMADE_3x3(9, "Used for both historical Ranked Premade 3v3 games and current Ranked Flex Twisted Treeline games"),
    RANKED_FLEX_TT(9, "Used for both historical Ranked Premade 3v3 games and current Ranked Flex Twisted Treeline games"),
    RANKED_TEAM_3x3(
            41, "Ranked Team 3v3 games"),
    RANKED_TEAM_5x5(
            42, "Ranked Team 5v5 games"),
    ODIN_5x5_BLIND(
            16, "Dominion 5v5 Blind Pick games"),
    ODIN_5x5_DRAFT(
            17, "Dominion 5v5 Draft Pick games"),
    BOT_5x5(
            7, "Historical Summoner's Rift Coop vs AI games"),
    BOT_ODIN_5x5(
            25, "Dominion Coop vs AI games"),
    BOT_5x5_INTRO(
            31, "Summoner's Rift Coop vs AI Intro Bot games"),
    BOT_5x5_BEGINNER(
            32, "Summoner's Rift Coop vs AI Beginner Bot games"),
    BOT_5x5_INTERMEDIATE(
            33, "Historical Summoner's Rift Coop vs AI Intermediate Bot games"),
    BOT_TT_3x3(
            52, "Twisted Treeline Coop vs AI games"),
    GROUP_FINDER_5x5(
            61, "Team Builder games"),
    ARAM_5x5_OLD(
            65, "ARAM games"),
    ONEFORALL_5x5(
            70, "One for All games"),
    FIRSTBLOOD_1x1(
            72, "Snowdown Showdown 1v1 games"),
    FIRSTBLOOD_2x2(
            73, "Snowdown Showdown 2v2 games"),
    SR_6x6(
            75, "Summoner's Rift 6x6 Hexakill games"),
    URF_5x5(
            76, "Ultra Rapid Fire games"),
    ONEFORALL_MIRRORMODE_5x5(
            78, "One for All (Mirror mode)"),
    BOT_URF_5x5(
            83, "Ultra Rapid Fire games played against AI games"),
    NIGHTMARE_BOT_5x5_RANK1(
            91, "Doom Bots Rank 1 games"),
    NIGHTMARE_BOT_5x5_RANK2(
            92, "Doom Bots Rank 2 games"),
    NIGHTMARE_BOT_5x5_RANK5(
            93, "Doom Bots Rank 5 games"),
    ASCENSION_5x5(
            96, "Ascension games"),
    HEXAKILL(
            98, "Twisted Treeline 6x6 Hexakill games"),
    BILGEWATER_ARAM_5x5(
            100, "Butcher's Bridge games"),
    KING_PORO_5x5(
            300, "King Poro games"),
    COUNTER_PICK(
            310, "Nemesis games"),
    BILGEWATER_5x5(
            313, "Black Market Brawlers games"),
    SIEGE(
            315, "Nexus Siege games"),
    DEFINITELY_NOT_DOMINION_5x5(
            317, "Definitely Not Dominion games"),
    ARURF_5X5(
            318, "All Random URF games"),
    TEAM_BUILDER_DRAFT_UNRANKED_5x5(
            400, "Normal 5v5 Draft Pick games"),
    TEAM_BUILDER_DRAFT_RANKED_5x5(
            410, "Ranked 5v5 Draft Pick games"),
    TEAM_BUILDER_RANKED_SOLO(
            420, "Ranked Solo games from current season that use Team Builder matchmaking"),
    BLIND_PICK_NORMAL(
            430, "Blind pick Summoner's Rift games"),
    RANKED_FLEX_SR(
            440, "Ranked Flex Summoner's Rift games"),
    ARAM_5X5(
            450, "5v5 ARAM games"),
    BLIND_PICK_3x3(460, "Blind pick 3v3 games"),
    RANKED_FLEX_3x3(470, "Ranked Flex pick 3v3 games"),
    ARSR_5x5(
            325, "All Random Summoner's Rift games"),
    ASSASSINATE_5x5(
            600, "Blood Hunt Assassin games"),
    DARKSTAR_3x3(
            610, "Darkstar games"),
    COOP_VS_AI_INTERMEDIATE_3x3(800, "Co-op vs. AI Intermediate Bot games, 3v3"),
    COOP_VS_AI_INTRO_3x3(810, "Co-op vs. AI Intro Bot games, 3v3"),
    COOP_VS_AI_BEGINNER_3x3(820, "Co-op vs. AI Beginner Bot games, 3v3"),
    COOP_VS_AI_INTRO_5x5(830, "Co-op vs. AI Intro Bot games, 5v5"),
    COOP_VS_AI_BEGINNER_5x5(840, "Co-op vs. AI Beginner Bot games, 5v5"),
    COOP_VS_AI_INTERMEDIATE_5x5(850, "Co-op vs. AI Intermediate Bot games, 5v5"),
    ASCENSION(910, "Ascension games"),
    NEXUS_SIEGE(940, "Nexus Siege games"),
    DOOM_BOTS_DIFFICULTY_VOTING(950, "Doom Bots games with difficulty voting"),
    DOOM_BOTS(960, "Doom Bots games"),
    STAR_GUARDIAN_NORMAL(980, "Star Guardian Invasion: Normal games"),
    STAR_GUARDIAN_ONSLAUGHT(990, "Star Guardian Invasion: Onslaught games"),
    PROJECT(1000, "Overdrive Project games");
    
    private final Integer queueTypeId;
    private final String name;

    public Integer getQueueTypeId() {
        return queueTypeId;
    }

    public String getName() {
        return name;
    }

    QueueType(Integer queueTypeId, String name) {
        this.queueTypeId = queueTypeId;
        this.name = name;
    }

    public static QueueType fromId(Integer queueTypeId) {
        return queueTypeMap.get(queueTypeId);
    }

    @JsonCreator
    public static QueueType fromString(String queueType) {
        char firstChar = queueType.charAt(0);
        if (firstChar >= '0' && firstChar <= '9') {
            return fromId(Integer.parseInt(queueType));
        }
        return QueueType.valueOf(queueType);
    }

    private static final HashMap<Integer, QueueType> queueTypeMap;

    static {
        queueTypeMap = new HashMap<>();
        for (QueueType queueType : values()) {
            queueTypeMap.put(queueType.queueTypeId, queueType);
        }
    }

}
