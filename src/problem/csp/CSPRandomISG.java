package problem.csp;

import alg.InitialSolutionGenerator;
import problem.OptimizationProblem;
import representation.IntegerPermutation;
import representation.SimpleSolution;
import representation.Solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CSPRandomISG implements InitialSolutionGenerator<CSPModel, IntegerPermutation> {
    @Override
    public Solution generate(OptimizationProblem<CSPModel, IntegerPermutation> problem) {

        CSPModel csp = problem.model();
        List<Integer> productList = new ArrayList<>(csp.getListOfExpandedItems());
        Collections.shuffle(productList);
        IntegerPermutation ip =new IntegerPermutation(productList);
        return new SimpleSolution(ip,problem.getObjective().value(csp,ip));
    }




    @Override
    public String toString() {
        return "CSP-Random-ISG";
    }
}
