package alg.ga;

import alg.TerminalCondition;
import problem.OptimizationProblem;
import problem.ProblemModel;
import representation.Representation;
import representation.Solution;

import java.util.List;

/**
 * IMPROVED GA: Adaptive Genetic Algorithm
 * When algorithm stucks mutation rate automatically increases, when algorithm escape from stuck continues with the normal way
 */
public class AdaptiveGA<PM extends ProblemModel<R>, R extends Representation> extends GA<PM, R> {

    // Ayarlar
    private final double LOW_MUTATION = 0.2;  // Normal
    private final double HIGH_MUTATION = 0.6; // Kaçış
    private final int STAGNATION_LIMIT = 20;  // Max Repetation

    public AdaptiveGA(TerminalCondition<PM, R> terminalCondition) {
        super(terminalCondition);
    }

    @Override
    protected Solution _perform(OptimizationProblem<PM, R> problem) {

        createInitialPopulation(problem);
        iterationCount = 0;

        int noImprovementCounter = 0;
        double currentBestValue = Double.MAX_VALUE;


        updateGlobalBest(problem);
        if (bestSolution != null) {
            currentBestValue = bestSolution.value();
        }

        while (!terminalCondition.isSatisfied(this, problem)) {
            iterationCount++;


            List<Solution> parents = selectParents(problem);
            List<Solution> offspring = produceOffspring(problem, parents);


            applyMutation(problem, offspring);
            replacePopulation(problem, offspring);


            updateGlobalBest(problem);

            if (bestSolution.value() < currentBestValue) {

                currentBestValue = bestSolution.value();
                noImprovementCounter = 0;


                this.setMutationRate(LOW_MUTATION);

            } else {

                noImprovementCounter++;
            }


            if (noImprovementCounter >= STAGNATION_LIMIT) {

                this.setMutationRate(HIGH_MUTATION);
            }
        }

        return bestSolution;
    }


    private void updateGlobalBest(OptimizationProblem<PM, R> problem) {
        for (Solution s : population) {

            if (bestSolution == null || s.value() < bestSolution.value()) {
                bestSolution = s;
            }
        }
    }

    @Override
    public String toString() {
        return "Adaptive GA (" + isg.toString() + ")";
    }
}