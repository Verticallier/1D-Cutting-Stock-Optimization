package experiments.CSP;

import alg.IterationBasedTC;
import alg.TimeBasedTC;
import alg.ga.GA;
import experiments.AvgBestDC;
import experiments.DataCollector;
import experiments.ExperimentRunner;
import experiments.ResultProcessor;
import problem.OptimizationProblem;
import problem.SimpleOptimizationProblem;
import problem.csp.CSPMinimumWasteOF;
import problem.csp.CSPModel;
import problem.csp.CSPRandomISG;
import representation.IntegerPermutation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class CSPParameterTuningMain {

    public static void main(String[] args) {

        int[] populationSizes = {10, 20, 30, 50, 100};
        int repetitions = 10;
        long oneMinute= 60_000;


        List<CSPModel> models = List.of(
                new CSPModel(100,
                        new int[]{8, 12, 15, 18, 22, 27, 31, 35, 40, 46},
                        new int[]{6,  6,  5,  5,  5,  5,  5,  5,  4,  4}
                ),
                new CSPModel(100,
                        new int[]{45, 52, 58, 63, 67, 71, 76, 82, 88, 94},
                        new int[]{3,  4,  5,  5,  6,  6,  6,  5,  5,  5}
                ),
                new CSPModel(100,
                        new int[]{10, 14, 19, 23, 28, 33, 39, 47, 59, 73},
                        new int[]{6,  5,  5,  5,  5,  5,  5,  5,  5,  4}
                ),
                new CSPModel(100,
                        new int[]{7, 9, 13, 16, 21, 24, 29, 34, 37, 41, 48, 53, 57, 62, 69},
                        new int[]{5, 3,  3,  3,  4,  3,  3,  3,  3,  3,  3,  3,  3,  3,  2}
                ),
                new CSPModel(100,
                        new int[]{49, 51, 33, 67, 24, 76, 12, 88, 41, 59},
                        new int[]{6,  5,  6,  4,  6,  4,  7,  3,  5,  4}
                )
        );

        String outFile = "./data/output/CSPParameterTuningResults.txt";
        ResultProcessor rp = new AppendTextFileRP(outFile);

        System.out.println("PARAMETER TUNING START...");

        for (int popSize : populationSizes) {

            GA<CSPModel, IntegerPermutation> ga =
                                new GA<>(new TimeBasedTC<>(oneMinute));

            ga.isg = new CSPRandomISG();
            ga.populationSize = popSize;
            ga.parentSelector = new alg.ga.RouletteWheelSelection();
            ga.victimSelector = new alg.ga.FitnessBasedVictimSelector();


            ga.setCrossOverRate(0.9);
            ga.setCrossOverOperator(new alg.ga.CSP.CSPRandomCrossOver());
            ga.setMutationRate(0.2);
            ga.setMutationOperator(new alg.ga.CSP.CSPRandomSwapMutation());

            for (int i = 0; i < models.size(); i++) {

                OptimizationProblem<CSPModel, IntegerPermutation> problem =
                        new SimpleOptimizationProblem<>(models.get(i), new CSPMinimumWasteOF());

                DataCollector dc = new AvgBestDC();

                ExperimentRunner runner = new ExperimentRunner(
                        repetitions,
                        rp,
                        Collections.singletonList(problem),
                        Collections.singletonList(ga),
                        Collections.singletonList(dc)
                );


                rp.process("PopSize: " + popSize + " | Instance: I" + (i + 1));
                runner.run();
                System.out.println("Finished popSize=" + popSize + " | instance=I" + (i + 1));
            }
        }

        System.out.println("DONE. Results written to -> " + outFile);
    }




    static class AppendTextFileRP implements ResultProcessor {

        private final String fileName;

        AppendTextFileRP(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public void process(String string) {
            try (FileWriter fw = new FileWriter(fileName, true);
                 BufferedWriter bw = new BufferedWriter(fw)) {

                bw.write(string);
                bw.newLine();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
