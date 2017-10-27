///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package statikk.dataminer.main;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import statikk.dataminer.service.ItemAnalysisService;
//import statikk.dataminer.service.MatchAnalyzerService;
//import statikk.dataminer.service.MatchMiningService;
//
///**
// *
// * @author AJ
// */
//@Component
//public class Application implements CommandLineRunner{
//
//    @Autowired
//    MatchMiningService matchMiningService;
//
//    @Autowired
//    MatchAnalyzerService matchAnalyzerService;
//    
//    @Autowired
//    ItemAnalysisService itemAnalysisService;
//
//    @Override
//    public void run(String[] strings) throws Exception {
//          itemAnalysisService.loadItems();        
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
//    }
//}
