/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.framework.riotapi.model;

import statikk.framework.entity.ChampSpec;
import statikk.framework.entity.FinalBuildOrder;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author AJ
 */
public class ParticipantDto implements Serializable {

    private int championId;
    private Rank highestAchievedSeasonTier;
    private List<MasteryDto> masteries;
    private int participantId;
    private List<RuneDto> runes;
    private int spell1Id;
    private int spell2Id;
    private ParticipantStatsDto stats;
    private LolTeam teamId;
    private ParticipantTimeline timeline;
    private ChampSpec champSpec = null;
    private FinalBuildOrder finalBuildOrder = null;

    public FinalBuildOrder getFinalBuildOrder() {
        return finalBuildOrder;
    }

    public void setFinalBuildOrder(FinalBuildOrder finalBuildOrder) {
        this.finalBuildOrder = finalBuildOrder;
    }

    public void setChampSpec(ChampSpec champSpec) {
        this.champSpec = champSpec;
    }

    public ChampSpec getChampSpec() {
        return champSpec;
    }

    public int getChampionId() {
        return championId;
    }

    public Rank getHighestAchievedSeasonTier() {
        return highestAchievedSeasonTier;
    }

    public List<MasteryDto> getMasteries() {
        return masteries;
    }

    public int getParticipantId() {
        return participantId;
    }

    public List<RuneDto> getRunes() {
        return runes;
    }

    public int getSpell1Id() {
        return spell1Id;
    }

    public int getSpell2Id() {
        return spell2Id;
    }

    public ParticipantStatsDto getStats() {
        return stats;
    }

    public LolTeam getTeamId() {
        return teamId;
    }

    public ParticipantTimeline getTimeline() {
        return timeline;
    }

}
