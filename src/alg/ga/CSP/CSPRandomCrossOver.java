package alg.ga.CSP;

import alg.ga.CrossOverOperator;
import problem.OptimizationProblem;
import problem.ProblemModel;
import representation.IntegerPermutation;
import representation.Representation;
import representation.SimpleSolution;
import representation.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class CSPRandomCrossOver implements CrossOverOperator {
    private final Random rand = new Random();
    private static final Integer TEMP_FLAG = -1;


    @Override
    @SuppressWarnings("unchecked")
    public <R extends Representation, PM extends ProblemModel<R>> List<Solution> apply(OptimizationProblem<PM, R> problem, Solution parent1, Solution parent2) {


        List<Solution> children = new ArrayList<>();
        IntegerPermutation p1 = (IntegerPermutation) parent1.getRepresentation().copy();
        IntegerPermutation p2 = (IntegerPermutation) parent2.getRepresentation().copy();

        int size = p1.size();

        if (size < 2) {
            return children;
        }

        int cutLocation =  rand.nextInt(size);
        boolean condition=isCrossOverPossible(p1,p2,cutLocation);

        if(condition){ //apply crossover
            //if parent1's left from cut is not has same amounth of members p2's right crossover is not possible.

        List<Integer> childMembers1 = new ArrayList<>();
        List<Integer> childMembers2 = new ArrayList<>();

           //if parent1's left from cut is not has same amounth of members p2's right crossover is not possible.


            for (int i = 0; i < cutLocation; i++) {
                childMembers1.add(p1.get(i));
                childMembers2.add(p2.get(i));
            }

            for (int i = cutLocation; i < p1.size(); i++) {
                childMembers1.add(p2.get(i));
                childMembers2.add(p1.get(i));
            }

            IntegerPermutation c1 = new IntegerPermutation(childMembers1);
            IntegerPermutation c2 = new IntegerPermutation(childMembers2);
            children.add(new SimpleSolution(c1,problem.getObjective().value(problem.model(),(R)c1))) ;
            children.add(new SimpleSolution(c2,problem.getObjective().value(problem.model(),(R)c2))) ;

        }



        return  children;

    }

    private boolean isCrossOverPossible(IntegerPermutation p1, IntegerPermutation p2, int cutLocation) {
        List<Integer> parent1 = p1.toList();
        List<Integer> parent2 = p2.toList();


        List<Integer> temp1 = new ArrayList<>(parent1);
        List<Integer> temp2 = new ArrayList<>(parent2);


        for (int i = 0; i < cutLocation; i++) {
            for (int j = 0; j < cutLocation; j++) {
                if (Objects.equals(temp1.get(i), temp2.get(j))) {
                    temp1.set(i, TEMP_FLAG);
                    temp2.set(j, TEMP_FLAG);
                }
            }
        }

        long mismatchCount = temp1.stream().filter(value -> value != TEMP_FLAG).count() +
                temp2.stream().filter(value -> value != TEMP_FLAG).count();

        return mismatchCount == cutLocation;
    }


}
