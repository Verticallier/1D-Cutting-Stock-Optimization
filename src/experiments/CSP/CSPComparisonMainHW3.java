package experiments.CSP;

import alg.TimeBasedTC;
import alg.ga.AdaptiveGA; // YENİ SINIF
import alg.ga.GA;
import experiments.*;
import problem.OptimizationProblem;
import problem.SimpleOptimizationProblem;
import problem.csp.CSPMinimumWasteOF;
import problem.csp.CSPModel;
import problem.csp.CSPRandomISG;
import problem.csp.CSPSmartISG; // Smart ISG (GRASP-RCL)
import representation.IntegerPermutation;
import alg.greedy.csp.CSPGreedyHeuristic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CSPComparisonMainHW3 {

    public static void main(String[] args) {


        int repetitions = 20;
        long oneMinute = 60_000;
        int popSize = 100;


        List<CSPModel> models = List.of(
                new CSPModel(100, new int[]{8,12,15,18,22,27,31,35,40,46}, new int[]{6,6,5,5,5,5,5,5,4,4}),
                new CSPModel(100, new int[]{45,52,58,63,67,71,76,82,88,94}, new int[]{3,4,5,5,6,6,6,5,5,5}),
                new CSPModel(100, new int[]{10,14,19,23,28,33,39,47,59,73}, new int[]{6,5,5,5,5,5,5,5,5,4}),
                new CSPModel(100, new int[]{7,9,13,16,21,24,29,34,37,41,48,53,57,62,69}, new int[]{5,3,3,3,4,3,3,3,3,3,3,3,3,3,2}),
                new CSPModel(100, new int[]{49,51,33,67,24,76,12,88,41,59}, new int[]{6,5,6,4,6,4,7,3,5,4}),
                new CSPModel(100, new int[]{11,17,26,32,38,44,57,63,71,85}, new int[]{6,5,5,5,5,5,5,4,3,2}),
                new CSPModel(100, new int[]{9,14,21,28,36,43,50,58,66,79}, new int[]{6,5,5,5,5,5,4,4,3,3}),
                new CSPModel(100, new int[]{13,19,24,29,34,41,48,56,64,92}, new int[]{5,5,5,5,5,5,5,4,3,3}),
                new CSPModel(100, new int[]{16,23,27,31,39,46,52,61,74,88}, new int[]{5,5,5,5,5,5,5,4,3,3}),
                new CSPModel(100, new int[]{12,18,25,33,37,45,54,62,77,89}, new int[]{5,5,5,5,5,5,4,4,3,4})
        );

        String outFile = "./data/output/CSP_HW3_Adaptive_Final.txt";
        ResultProcessor rp = new AppendTextFileRP(outFile);

        System.out.println("HW3: COMPARISON START (Greedy vs Standard GA vs Adaptive Smart GA)...");

        for (int i = 0; i < models.size(); i++) {
            CSPModel model = models.get(i);
            OptimizationProblem<CSPModel, IntegerPermutation> problem =
                    new SimpleOptimizationProblem<>(model, new CSPMinimumWasteOF());

            // 1. GREEDY
            CSPGreedyHeuristic greedy = new CSPGreedyHeuristic();
            CSPMinimumWasteOF of = new CSPMinimumWasteOF();
            double totalWaste = 0.0;
            long startTime = System.currentTimeMillis();
            for (int r = 0; r < repetitions; r++) {
                IntegerPermutation rep = greedy.solve(model);
                totalWaste += of.value(model, rep);
            }
            long endTime = System.currentTimeMillis();
            rp.process(String.format("[GREEDY] (I%d) BEST:%.2f  RUNTIME:%.2f", (i + 1), totalWaste/repetitions, (double)(endTime-startTime)/repetitions));

            // 2. STANDARD GA
            GA<CSPModel, IntegerPermutation> standardGA = new GA<>(new TimeBasedTC<>(oneMinute));
            standardGA.isg = new CSPRandomISG(); // Random ISG
            standardGA.populationSize = popSize;
            standardGA.parentSelector = new alg.ga.RouletteWheelSelection();
            standardGA.victimSelector = new alg.ga.FitnessBasedVictimSelector();
            standardGA.setCrossOverRate(0.9);
            standardGA.setCrossOverOperator(new alg.ga.CSP.CSPRandomCrossOver());
            standardGA.setMutationRate(0.2); // SABİT ORAN
            standardGA.setMutationOperator(new alg.ga.CSP.CSPRandomSwapMutation());

            // 3. IMPROVED GA
            AdaptiveGA<CSPModel, IntegerPermutation> improvedGA = new AdaptiveGA<>(new TimeBasedTC<>(oneMinute));
            improvedGA.isg = new CSPSmartISG();
            improvedGA.populationSize = popSize;
            improvedGA.parentSelector = new alg.ga.RouletteWheelSelection();
            improvedGA.victimSelector = new alg.ga.FitnessBasedVictimSelector();
            improvedGA.setCrossOverRate(0.9);
            improvedGA.setCrossOverOperator(new alg.ga.CSP.CSPRandomCrossOver());
            improvedGA.setMutationRate(0.2);
            improvedGA.setMutationOperator(new alg.ga.CSP.CSPRandomSwapMutation());

            ExperimentRunner runner = new ExperimentRunner(
                    repetitions,
                    rp,
                    Collections.singletonList(problem),
                    Arrays.asList(standardGA, improvedGA),
                    Arrays.asList(new AvgBestDC(), new AvgRunTimeDC())
            );

            runner.run();
            System.out.println("Finished instance I" + (i+1));
        }
        System.out.println("DONE -> " + outFile);
    }

    static class AppendTextFileRP implements ResultProcessor {
        private final String fileName;
        AppendTextFileRP(String fileName) { this.fileName = fileName; }
        @Override public void process(String string) {
            try (FileWriter fw = new FileWriter(fileName, true);
                 BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(string); bw.newLine();
            } catch (IOException e) { throw new RuntimeException(e); }
        }
    }
}