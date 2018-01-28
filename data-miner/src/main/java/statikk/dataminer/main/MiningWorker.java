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
import statikk.dataminer.service.MatchMiningService;
import statikk.domain.riotapi.model.Region;

/**
 *
 * @author AJ
 */
public class MiningWorker implements Runnable {

    private final Region region;
    private boolean shouldRun;
    private final MatchMiningService matchMiningService;
    private long matchesMined;

    public MiningWorker(Region region,
            MatchMiningService matchMiningService) throws IOException {
        this.shouldRun = true;
        this.region = region;
        this.matchMiningService = matchMiningService;
        this.matchesMined = 0;
    }

    @Override
    public void run() {
        try {
            Logger.getLogger(MiningWorker.class.getName()).log(Level.INFO, "Running thread for {0}", this.region.getPlatformId());
            while (this.shouldRun) {
                Logger.getLogger(MiningWorker.class.getName()).log(Level.INFO, "Beginning to mine matches.");
                this.matchesMined += matchMiningService.mineMatches(region);
                Logger.getLogger(MiningWorker.class.getName()).log(Level.INFO, "Finished mining matches; Beginning to analyze matches.");
            }
        } catch (Exception ex) {
            System.out.println("FAILED for " + region + ". " + new Date());
            Logger.getLogger(MiningWorker.class.getName()).log(Level.SEVERE, "ERROR RUNNING MINING WORKER FOR REGION " + region, ex);
        }
    }

    public void stop() {
        this.shouldRun = false;
    }

    public long getMatchesMined() {
        return this.matchesMined;
    }

    public Region getRegion() {
        return this.region;
    }
}
