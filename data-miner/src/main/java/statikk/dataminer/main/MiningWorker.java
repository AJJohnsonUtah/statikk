/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.dataminer.main;

import java.io.IOException;
import statikk.dataminer.service.ItemAnalysisService;
import statikk.dataminer.service.MatchAnalyzerService;
import statikk.dataminer.service.MatchMiningService;
import statikk.domain.riotapi.model.Region;
import statikk.domain.riotapi.service.RiotApiService;
import statikk.domain.service.FinalBuildOrderService;

/**
 *
 * @author AJ
 */
public class MiningWorker implements Runnable {

    private final Region region;
    private final RiotApiService riotApiService;

    private final ItemAnalysisService itemAnalysisService;
    private final MatchAnalyzerService matchAnalyzerService;
    private final MatchMiningService matchMiningService;

    public MiningWorker(Region region) throws IOException {
        this.region = region;
        this.riotApiService = new RiotApiService();
        this.itemAnalysisService = new ItemAnalysisService(riotApiService);
        this.matchAnalyzerService = new MatchAnalyzerService(riotApiService);
        this.matchMiningService = new MatchMiningService(riotApiService);
    }

    @Override
    public void run() {
        System.out.println("Running thread for " + this.region.getPlatformId());
        itemAnalysisService.loadItems();
    }
}
