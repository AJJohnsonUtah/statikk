/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.dataminer.main;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import statikk.dataminer.model.LolAggregateAnalysis;
import statikk.dataminer.service.ItemAnalysisService;
import statikk.dataminer.service.MatchAnalyzerService;
import statikk.domain.riotapi.model.Region;

/**
 *
 * @author AJ
 */
public class AnalyzerWorker implements Runnable {

    private final Region region;
    private boolean shouldRun;

    private final MatchAnalyzerService matchAnalyzerService;

    private long matchesAnalyzed;

    public AnalyzerWorker(Region region,
            ItemAnalysisService itemAnalysisService,
            MatchAnalyzerService matchAnalyzerService) throws IOException {
        this.shouldRun = true;
        this.region = region;
        this.matchAnalyzerService = matchAnalyzerService;
        this.matchesAnalyzed = 0;
    }

    @Override
    public void run() {
        try {
            Logger.getLogger(AnalyzerWorker.class.getName()).log(Level.INFO, "Running thread for {0}", this.region.getPlatformId());
            while (this.shouldRun) {
                matchesAnalyzed += matchAnalyzerService.analyzeMatches(region, new LolAggregateAnalysis());
                Logger.getLogger(AnalyzerWorker.class.getName()).log(Level.INFO, "Finished analyzing matches.");
            }
        } catch (Exception ex) {
            System.out.println("FAILED for " + region + ". " + new Date());
            Logger.getLogger(AnalyzerWorker.class.getName()).log(Level.SEVERE, "ERROR RUNNING ANALYZING WORKER FOR REGION " + region, ex);
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
