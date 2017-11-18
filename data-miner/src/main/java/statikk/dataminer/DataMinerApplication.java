package statikk.dataminer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import statikk.dataminer.main.MiningManager;

@SpringBootApplication
@PropertySource(value = "riot-api-key.properties", ignoreResourceNotFound = true)
@ComponentScan({"statikk.dataminer", "statikk.domain"})
public class DataMinerApplication implements CommandLineRunner {

    @Autowired
    MiningManager miningManager;
    
    public static void main(String[] args) {
        System.out.println("Starting this ish");
        SpringApplication app = new SpringApplication(DataMinerApplication.class);
        System.out.println("Running this ish");
        app.run(args);
    }
    
    

//    @Autowired
//    MatchMiningService matchMiningService;
//
//    @Autowired
//    MatchAnalyzerService matchAnalyzerService;
//
//    @Autowired
//    ItemAnalysisService itemAnalysisService;

    @Override
    public void run(String[] strings) throws Exception {
        miningManager.begin();
//        itemAnalysisService.loadItems();
//        int numIterations = 0;
//        int numMatchesToAnalyze = 30;
//        long veryStart, start, middle, end;
//        long timeMining = 0;
//        long timeAnalyzing = 0;
//        System.out.println("Beginning Analysis!");
//        while (true) {
//            System.out.println("BEGIN: Main loop");
//            start = System.currentTimeMillis();
//            matchMiningService.mineMatches(numMatchesToAnalyze);
//            System.out.println("INFO: Matches have been mined.");
//            middle = System.currentTimeMillis();
//            matchAnalyzerService.analyzeMatches(numMatchesToAnalyze);
//            System.out.println("INFO: Matches have been analyzed.");
//            numIterations++;
//            end = System.currentTimeMillis();
//            timeMining += middle - start;
//            timeAnalyzing += end - middle;
//            System.out.println(numIterations * numMatchesToAnalyze + " matches found and analyzed in " + ((timeMining + timeAnalyzing) / 1000.0) + " seconds");
//            System.out.println((timeMining * 1.0) / (timeMining + timeAnalyzing) + "% time mining.");
//            System.out.println((timeAnalyzing * 1.0) / (timeMining + timeAnalyzing) + "% time analyzing.");
//        }
    }

}
