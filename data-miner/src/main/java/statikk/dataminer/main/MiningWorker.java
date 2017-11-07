/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.dataminer.main;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import statikk.dataminer.model.LolAggregateAnalysis;
import statikk.dataminer.service.ItemAnalysisService;
import statikk.dataminer.service.MatchAnalyzerService;
import statikk.dataminer.service.MatchMiningService;
import statikk.domain.riotapi.model.Region;

/**
 *
 * @author AJ
 */
public class MiningWorker implements Runnable {

    private final Region region;
    private boolean shouldRun;

    private final ItemAnalysisService itemAnalysisService;
    private final MatchAnalyzerService matchAnalyzerService;
    private final MatchMiningService matchMiningService;

    private long matchesAnalyzed;

    public MiningWorker(Region region,
            ItemAnalysisService itemAnalysisService,
            MatchAnalyzerService matchAnalyzerService,
            MatchMiningService matchMiningService) throws IOException {
        this.shouldRun = true;
        this.region = region;
        this.itemAnalysisService = itemAnalysisService;
        this.matchAnalyzerService = matchAnalyzerService;
        this.matchMiningService = matchMiningService;
        this.matchesAnalyzed = 0;
    }

    @Override
    public void run() {
        try {
            LinkedHashSet<Long> summonerIds = new LinkedHashSet<>();
            Logger.getLogger(MiningWorker.class.getName()).log(Level.INFO, "Running thread for {0}", this.region.getPlatformId());
            while (this.shouldRun) {
                Logger.getLogger(MiningWorker.class.getName()).log(Level.INFO, "Beginning to mine matches.");
                int matchesMined = matchMiningService.mineMatches(10, region, summonerIds);
                Logger.getLogger(MiningWorker.class.getName()).log(Level.INFO, "Finished mining matches; Beginning to analyze matches.");
                List<Long> newSummonerIds = matchAnalyzerService.analyzeMatches(matchesMined, region, new LolAggregateAnalysis());
                matchesAnalyzed += matchesMined;
                if (summonerIds.size() < 1000) {
                    summonerIds.addAll(newSummonerIds);
                }
                Logger.getLogger(MiningWorker.class.getName()).log(Level.INFO, "Finished analyzing matches.");
            }
        } catch (Exception ex) {
            Logger.getLogger(MiningWorker.class.getName()).log(Level.SEVERE, "ERROR RUNNING MINING WORKER FOR REGION " + region, ex);
        }
    }

    public void stop() {
        this.shouldRun = false;
    }

    public long getMatchesAnalyzed() {
        return this.matchesAnalyzed;
    }

    public Region getRegion() {
        return this.region;
    }
}
