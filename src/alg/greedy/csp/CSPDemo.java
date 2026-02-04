package alg.greedy.csp;

import problem.csp.CSPModel;
import problem.csp.CSPMinimumWasteOF;
import representation.IntegerPermutation;

public class CSPDemo {

    public static void main(String[] args) {

        int stockLength = 50;
        int[] itemLengths = {50, 20, 7, 45, 30, 20, 10, 7, 20, 45, 30, 45};
        int[] quantities  = {1,  1 , 1,  1,  2,  2,  2, 1,  2,  1,  2,  1};

        CSPModel model = new CSPModel(stockLength, itemLengths, quantities);

        CSPGreedyHeuristic greedy = new CSPGreedyHeuristic();
        IntegerPermutation solution = greedy.solve(model);

        CSPMinimumWasteOF of = new CSPMinimumWasteOF();
        double waste = of.value(model, solution);

        System.out.println("===== CUTTING STOCK GREEDY DEMO =====");
        System.out.println("Instance feasible? " + model.isFeasable(solution));
        if(!model.isFeasable(solution)) return;
        System.out.println("Stock length: " + stockLength);
        System.out.println("Expanded items: " + model.itemCount());
        System.out.println("Greedy permutation (cut order): " + solution);
        System.out.println("Total waste: " + waste);

        System.out.println("=====================================");
    }
}
