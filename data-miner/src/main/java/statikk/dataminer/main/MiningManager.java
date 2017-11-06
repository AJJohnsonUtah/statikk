package statikk.dataminer.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import statikk.domain.riotapi.model.Region;

public class MiningManager {

    private List<MiningWorker> miningWorkers;

    public MiningManager() {
    }

    public void begin() {
        System.out.println("Beginning MiningManager...");
        miningWorkers = new ArrayList<>();
        for (Region region : Region.values()) {
            if(miningWorkers.size() > 0) {
                break;
            }
            try {
                MiningWorker miningWorker = new MiningWorker(region);
                miningWorkers.add(miningWorker);
            } catch (IOException ex) {
                Logger.getLogger(MiningManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        for (MiningWorker worker : miningWorkers) {
            Thread thread = new Thread(worker);
            thread.start();
        }
    }
}
