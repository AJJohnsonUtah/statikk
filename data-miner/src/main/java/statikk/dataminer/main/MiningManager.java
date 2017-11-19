package statikk.dataminer.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import statikk.dataminer.service.ItemAnalysisService;
import statikk.dataminer.service.MatchAnalyzerService;
import statikk.dataminer.service.MatchMiningService;
import statikk.domain.riotapi.model.Region;

@Component
public class MiningManager {

    @Autowired
    private MatchAnalyzerService matchAnalyzerService;

    @Autowired
    private MatchMiningService matchMiningService;

    @Autowired
    private ItemAnalysisService itemAnalysisService;

    private List<MiningWorker> miningWorkers;
    private List<Thread> activeThreads;
    private long startTimeMillis;

    public MiningManager() {
    }

    public void begin() {
        startTimeMillis = System.currentTimeMillis();
        itemAnalysisService.loadItems();
        miningWorkers = new ArrayList<>();
        for (Region region : Region.values()) {
            if (!region.isPublic() || region.equals(Region.NA)) {
                continue;
            }
            try {
                MiningWorker miningWorker = new MiningWorker(region, itemAnalysisService, matchAnalyzerService, matchMiningService);
                miningWorkers.add(miningWorker);
            } catch (IOException ex) {
                Logger.getLogger(MiningManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        activeThreads = new ArrayList<>();
        for (MiningWorker worker : miningWorkers) {
            Thread thread = new Thread(worker);
            activeThreads.add(thread);
            thread.start();
        }
        runStatusUpdate();
    }

    public void runStatusUpdate() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            long totalMatchesMined = 0;
            for (int i = 0; i < activeThreads.size(); i++) {                
                Thread thread = activeThreads.get(i);
                MiningWorker miningWorker = miningWorkers.get(i);
                totalMatchesMined += miningWorker.getMatchesAnalyzed();
                if (thread.isAlive()) {
                    Logger.getLogger(MiningManager.class.getName()).log(Level.INFO, "Thread-{0} ({1}) is alive. {2} matches analyzed.", new Object[]{thread.getId(), miningWorker.getRegion(), miningWorker.getMatchesAnalyzed()});
                    System.out.println("Thread-" + thread.getId() + " (" + miningWorker.getRegion() + ") is alive. " + miningWorker.getMatchesAnalyzed() + " matches analyzed.");
                } else {
                    Logger.getLogger(MiningManager.class.getName()).log(Level.INFO, "Thread-{0} ({1}) has died.", new Object[]{thread.getId(), miningWorker.getRegion()});
                    System.out.println("Thread-" + thread.getId() + " (" + miningWorker.getRegion() + ") has died.");
                }
            }
            double secondsElapsed = (System.currentTimeMillis() - startTimeMillis) / 1000.0;
            Logger.getLogger(MiningManager.class.getName()).log(Level.INFO, "{0} matches analyzed in {1} seconds. ({2} matches per second)", new Object[]{totalMatchesMined, secondsElapsed, totalMatchesMined / secondsElapsed});
            System.out.println(totalMatchesMined + " matches analyzed in " + secondsElapsed + " seconds. (" + totalMatchesMined / secondsElapsed + " matches per second)");
        },
                0,
                30,
                TimeUnit.SECONDS
        );
    }
}
