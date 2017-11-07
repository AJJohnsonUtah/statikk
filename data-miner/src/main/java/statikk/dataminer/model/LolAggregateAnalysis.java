/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.dataminer.model;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import statikk.domain.entity.ChampBan;
import statikk.domain.entity.ChampBanPK;
import statikk.domain.entity.ChampFinalBuild;
import statikk.domain.entity.ChampFinalBuildPK;
import statikk.domain.entity.ChampMatchup;
import statikk.domain.entity.ChampMatchupPK;
import statikk.domain.entity.ChampSpecWinRate;
import statikk.domain.entity.ChampSpecWinRatePK;
import statikk.domain.entity.ChampSummonerSpells;
import statikk.domain.entity.ChampSummonerSpellsPK;
import statikk.domain.entity.ChampTeamup;
import statikk.domain.entity.ChampTeamupPK;

/**
 *
 * @author AJ
 */
@Service
public class LolAggregateAnalysis {

    private Map<ChampSpecWinRatePK, ChampSpecWinRate> champSpecWinRates;
    private Map<ChampMatchupPK, ChampMatchup> champMatchups;
    private Map<ChampTeamupPK, ChampTeamup> champTeamups;
    private Map<ChampFinalBuildPK, ChampFinalBuild> champFinalBuilds;
    private Map<ChampSummonerSpellsPK, ChampSummonerSpells> champSummonerSpells;
    private Map<ChampBanPK, ChampBan> champBans;

    public LolAggregateAnalysis() {
        champSpecWinRates = new HashMap<>();
        champMatchups = new HashMap<>();
        champTeamups = new HashMap<>();
        champFinalBuilds = new HashMap<>();
        champSummonerSpells = new HashMap<>();
        champBans = new HashMap<>();
    }

    public void resetAnalysis() {
        champSpecWinRates = new HashMap<>();
        champMatchups = new HashMap<>();
        champTeamups = new HashMap<>();
        champFinalBuilds = new HashMap<>();
        champSummonerSpells = new HashMap<>();
        champBans = new HashMap<>();
    }

    public Map<ChampSpecWinRatePK, ChampSpecWinRate> getChampSpecWinRates() {
        return champSpecWinRates;
    }

    public Map<ChampMatchupPK, ChampMatchup> getChampMatchups() {
        return champMatchups;
    }

    public Map<ChampTeamupPK, ChampTeamup> getChampTeamups() {
        return champTeamups;
    }

    public Map<ChampFinalBuildPK, ChampFinalBuild> getChampFinalBuilds() {
        return champFinalBuilds;
    }

    public Map<ChampSummonerSpellsPK, ChampSummonerSpells> getChampSummonerSpells() {
        return champSummonerSpells;
    }

    public Map<ChampBanPK, ChampBan> getChampBans() {
        return champBans;
    }

    public void addChampSpecWinRate(ChampSpecWinRate champSpecWinRate) {
        if (champSpecWinRates.containsKey(champSpecWinRate.getChampSpecWinRatePK())) {
            champSpecWinRates.get(champSpecWinRate.getChampSpecWinRatePK()).combine(champSpecWinRate);
        } else {
            champSpecWinRates.put(champSpecWinRate.getChampSpecWinRatePK(), champSpecWinRate);
        }
    }

    public void addChampMatchup(ChampMatchup champMatchup) {
        if (champMatchups.containsKey(champMatchup.getChampMatchupPK())) {
            champMatchups.get(champMatchup.getChampMatchupPK()).combine(champMatchup);
        } else {
            champMatchups.put(champMatchup.getChampMatchupPK(), champMatchup);
        }
    }

    public void addChampTeamup(ChampTeamup champTeamup) {
        if (champTeamups.containsKey(champTeamup.getChampTeamupPK())) {
            champTeamups.get(champTeamup.getChampTeamupPK()).combine(champTeamup);
        } else {
            champTeamups.put(champTeamup.getChampTeamupPK(), champTeamup);
        }
    }

    public void addChampFinalBuild(ChampFinalBuild champFinalBuild) {
        if (champFinalBuilds.containsKey(champFinalBuild.getChampFinalBuildPK())) {
            champFinalBuilds.get(champFinalBuild.getChampFinalBuildPK()).combine(champFinalBuild);
        } else {
            champFinalBuilds.put(champFinalBuild.getChampFinalBuildPK(), champFinalBuild);
        }
    }

    public void addChampSummonerSpells(ChampSummonerSpells champSummonerSpell) {
        if (champSummonerSpells.containsKey(champSummonerSpell.getChampSummonerSpellsPK())) {
            champSummonerSpells.get(champSummonerSpell.getChampSummonerSpellsPK()).combine(champSummonerSpell);
        } else {
            champSummonerSpells.put(champSummonerSpell.getChampSummonerSpellsPK(), champSummonerSpell);
        }
    }

    public void addChampBan(ChampBan champBan) {
        if (champBans.containsKey(champBan.getChampBanPK())) {
            champBans.get(champBan.getChampBanPK()).combine(champBan);
        } else {
            champBans.put(champBan.getChampBanPK(), champBan);
        }
    }

}
