/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.riotapi.model;

import statikk.domain.entity.ChampSpec;
import statikk.domain.entity.FinalBuildOrder;
import java.io.Serializable;
import java.util.List;
import statikk.domain.entity.enums.Lane;
import statikk.domain.entity.enums.Role;

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
    private Role role;

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Lane getLane() {
        MatchLane matchLane = this.getTimeline().getLane();
        if (matchLane == MatchLane.BOT || matchLane == MatchLane.BOTTOM && this.getTimeline().getRole() == MatchRole.DUO_SUPPORT) {
            return Lane.SUPPORT;
        }
        return matchLane.toLane();

    }

}
