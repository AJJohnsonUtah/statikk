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
    private List<AnalyzerWorker> analyzerWorkers;
    private List<Thread> activeMiningThreads;
    private List<Thread> activeAnalyzingThreads;
    private long startTimeMillis;

    public MiningManager() {
    }

    public void begin() {
        startTimeMillis = System.currentTimeMillis();
        itemAnalysisService.loadItems();
        miningWorkers = new ArrayList<>();
        analyzerWorkers = new ArrayList<>();
        for (Region region : Region.values()) {
            if (!region.isPublic()) {
                continue;
            }
            try {
                MiningWorker miningWorker = new MiningWorker(region, matchMiningService);
                AnalyzerWorker analyzerWorker = new AnalyzerWorker(region, itemAnalysisService, matchAnalyzerService);
                miningWorkers.add(miningWorker);
                analyzerWorkers.add(analyzerWorker);
            } catch (IOException ex) {
                Logger.getLogger(MiningManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        activeMiningThreads = new ArrayList<>();
        activeAnalyzingThreads = new ArrayList<>();
        for (MiningWorker worker : miningWorkers) {
            Thread thread = new Thread(worker);
            activeMiningThreads.add(thread);
            thread.start();
        }
        for (AnalyzerWorker worker : analyzerWorkers) {
            Thread thread = new Thread(worker);
            activeAnalyzingThreads.add(thread);
            thread.start();
        }
        runStatusUpdate();
    }

    public void runStatusUpdate() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            long totalMatchesMined = 0;
            long totalMatchesAnalyzed = 0;
            for (int i = 0; i < activeMiningThreads.size() ; i++) {
                Thread thread = activeMiningThreads.get(i);
                MiningWorker miningWorker = miningWorkers.get(i);
                totalMatchesMined += miningWorker.getMatchesMined();
                if (thread.isAlive()) {
                    Logger.getLogger(MiningManager.class.getName()).log(Level.INFO, "Thread-{0} ({1}) is alive. {2} matches mined.", new Object[]{thread.getId(), miningWorker.getRegion(), miningWorker.getMatchesMined()});
                    System.out.println("Thread-" + thread.getId() + " (" + miningWorker.getRegion() + ") is alive. " + miningWorker.getMatchesMined() + " matches analyzed.");
                } else {
                    Logger.getLogger(MiningManager.class.getName()).log(Level.INFO, "Thread-{0} ({1}) has died.", new Object[]{thread.getId(), miningWorker.getRegion()});
                    System.out.println("Thread-" + thread.getId() + " (" + miningWorker.getRegion() + ") has died.");
                }
            }

            for (int i = 0; i < activeAnalyzingThreads.size(); i++) {
                Thread thread = activeAnalyzingThreads.get(i);
                AnalyzerWorker analyzerWorker = analyzerWorkers.get(i);
                totalMatchesAnalyzed += analyzerWorker.getMatchesAnalyzed();
                if (thread.isAlive()) {
                    Logger.getLogger(MiningManager.class.getName()).log(Level.INFO, "Thread-{0} ({1}) is alive. {2} matches analyzed.", new Object[]{thread.getId(), analyzerWorker.getRegion(), analyzerWorker.getMatchesAnalyzed()});
                    System.out.println("Thread-" + thread.getId() + " (" + analyzerWorker.getRegion() + ") is alive. " + analyzerWorker.getMatchesAnalyzed()+ " matches analyzed.");
                } else {
                    Logger.getLogger(MiningManager.class.getName()).log(Level.INFO, "Thread-{0} ({1}) has died.", new Object[]{thread.getId(), analyzerWorker.getRegion()});
                    System.out.println("Thread-" + thread.getId() + " (" + analyzerWorker.getRegion() + ") has died.");
                }
            }
            double secondsElapsed = (System.currentTimeMillis() - startTimeMillis) / 1000.0;
            Logger.getLogger(MiningManager.class.getName()).log(Level.INFO, "{0} matches mined in {1} seconds. ({2} matches per second)", new Object[]{totalMatchesMined, secondsElapsed, totalMatchesMined / secondsElapsed});
            Logger.getLogger(MiningManager.class.getName()).log(Level.INFO, "{0} matches analyzed in {1} seconds. ({2} matches per second)", new Object[]{totalMatchesAnalyzed, secondsElapsed, totalMatchesAnalyzed / secondsElapsed});
            System.out.println(totalMatchesMined + " matches mined in " + secondsElapsed + " seconds. (" + totalMatchesMined / secondsElapsed + " matches per second)");
            System.out.println(totalMatchesAnalyzed + " matches analyzed in " + secondsElapsed + " seconds. (" + totalMatchesAnalyzed / secondsElapsed + " matches per second)");
        },
                0,
                30,
                TimeUnit.SECONDS
        );
    }
}
