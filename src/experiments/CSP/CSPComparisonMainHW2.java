package experiments.CSP;

import alg.TimeBasedTC;
import alg.ga.GA;
import experiments.*;
import problem.OptimizationProblem;
import problem.SimpleOptimizationProblem;
import problem.csp.CSPMinimumWasteOF;
import problem.csp.CSPModel;
import problem.csp.CSPRandomISG;
import representation.IntegerPermutation;
import alg.greedy.csp.CSPGreedyHeuristic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CSPComparisonMainHW2 {

    public static void main(String[] args) {

        int repetitions = 20;
        long oneMinute = 60_000;

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

        String outFile = "./data/output/CSPComparisonResults_HW2.txt";
        ResultProcessor rp = new AppendTextFileRP(outFile);

        System.out.println("GA VS GREEDY COMPARISON START...");

        for (int i = 0; i < models.size(); i++) {

            CSPModel model = models.get(i);
            OptimizationProblem<CSPModel, IntegerPermutation> problem =
                    new SimpleOptimizationProblem<>(model, new CSPMinimumWasteOF());

            // ---Greedy Report ---
            CSPGreedyHeuristic greedy = new CSPGreedyHeuristic();
            CSPMinimumWasteOF of = new CSPMinimumWasteOF();
            double totalWaste = 0.0;
            long startTime = System.currentTimeMillis();

            for (int r = 0; r < repetitions; r++) {
                IntegerPermutation rep = greedy.solve(model);
                totalWaste += of.value(model, rep);
            }

            long endTime = System.currentTimeMillis();

            double avgWaste = totalWaste / repetitions;
            double avgRunTime = (double)(endTime - startTime) / repetitions;


            String greedyResult = String.format("[GREEDY] (I%d) BEST:%.2f  RUNTIME:%.2f",
                    (i + 1), avgWaste, avgRunTime);

            rp.process(greedyResult);






            //GA
            GA<CSPModel, IntegerPermutation> ga =
                    new GA<>(new TimeBasedTC<>(oneMinute));

            ga.isg = new CSPRandomISG();
            ga.populationSize = 100;
            ga.parentSelector = new alg.ga.RouletteWheelSelection();
            ga.victimSelector = new alg.ga.FitnessBasedVictimSelector();
            ga.setCrossOverRate(0.9);
            ga.setCrossOverOperator(new alg.ga.CSP.CSPRandomCrossOver());
            ga.setMutationRate(0.2);
            ga.setMutationOperator(new alg.ga.CSP.CSPRandomSwapMutation());


            List<DataCollector> dataCollectors = Arrays.asList(
                    new AvgBestDC(),
                    new AvgIterationDC(),
                    new AvgBestAchieveTimeDC(),
                    new AvgRunTimeDC());
            ExperimentRunner runner = new ExperimentRunner(
                    repetitions,
                    rp,
                    Collections.singletonList(problem),
                    Collections.singletonList(ga),
                    dataCollectors
            );
            runner.run();

            System.out.println("Finished instance I" + (i+1));
        }

        System.out.println("DONE. Results -> " + outFile);
    }



    //
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
