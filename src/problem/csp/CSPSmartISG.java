package problem.csp;

import alg.InitialSolutionGenerator;
import problem.OptimizationProblem;
import representation.IntegerPermutation;
import representation.SimpleSolution;
import representation.Solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Smart ISG with GRASP Logic.
 * Keeps Best Fit and alive diversification
 * Seperate the sorted list and match random instance from each list
 * We are randomly choosing the one of the best for keep the diversity alive.
 */
public class CSPSmartISG implements InitialSolutionGenerator<CSPModel, IntegerPermutation> {

    private final Random rand = new Random();

    private final int RCL_SIZE = 3;

    @Override
    public Solution generate(OptimizationProblem<CSPModel, IntegerPermutation> problem) {

        CSPModel csp = problem.model();
        int stockLength = csp.getStockLength();


        List<Integer> allItems = new ArrayList<>(csp.getListOfExpandedItems());
        Collections.sort(allItems, Collections.reverseOrder());

        int midPoint = allItems.size() / 2;
        List<Integer> largeItems = new ArrayList<>(allItems.subList(0, midPoint));
        List<Integer> smallItems = new ArrayList<>(allItems.subList(midPoint, allItems.size()));


        Collections.sort(smallItems, Collections.reverseOrder());

        List<Integer> chromosome = new ArrayList<>();


        while (!largeItems.isEmpty()) {


            int largeIndex = rand.nextInt(largeItems.size());
            Integer big = largeItems.remove(largeIndex);
            chromosome.add(big);


            int remainingSpace = stockLength - big;


            List<Integer> candidatesIndices = new ArrayList<>();

            for (int i = 0; i < smallItems.size(); i++) {
                if (smallItems.get(i) <= remainingSpace) {
                    candidatesIndices.add(i);

                    if (candidatesIndices.size() >= RCL_SIZE) {
                        break;
                    }
                }
            }


            if (!candidatesIndices.isEmpty()) {

                int randomCandidateIndex = rand.nextInt(candidatesIndices.size());
                int actualIndexInList = candidatesIndices.get(randomCandidateIndex);

                Integer small = smallItems.remove(actualIndexInList);
                chromosome.add(small);
            }
        }

        chromosome.addAll(smallItems);

        IntegerPermutation ip = new IntegerPermutation(chromosome);
        return new SimpleSolution(ip, problem.getObjective().value(csp, ip));
    }

    @Override
    public String toString() {
        return "CSP-Smart-ISG (GRASP-RCL)";
    }
}