package alg.ga.CSP;

import alg.ga.MutationOperation;
import problem.OptimizationProblem;
import problem.ProblemModel;
import representation.IntegerPermutation;
import representation.Representation;
import representation.SimpleSolution;
import representation.Solution;

import java.util.Objects;
import java.util.Random;

public class CSPRandomSwapMutation implements MutationOperation {

    private Random rand = new Random();

    @Override
    public <R extends Representation, PM extends ProblemModel<R>>
    void apply(OptimizationProblem<PM, R> problem, Solution offSpring) {

        int firstChoosen;
        int secondChoosen;
        boolean isDifferent=false;

        IntegerPermutation p = (IntegerPermutation) offSpring.getRepresentation();
        // System.out.println("MUT before: " + p);



        /**
         * Is there any possible swap mutation? ex:[50,50,50,50,50,50]
         * */
        for (int i = 0; i < p.size()-1; i++) {
                if(!Objects.equals(p.get(i), p.get(i + 1)))
                {
                    isDifferent=true;
                    break;
                }
        }
        if(!isDifferent){
            System.out.println("SWAP MUTATION IS NOT POSSIBLE ALL ITEMS ARE SAME! NO DISTINCT ITEM");

            return;
        }



       /**
        * indexes and values must be different. ex: [10, 20 ,20 ,30] if values or indexes is same nothing changes.
        **/

        do {
            firstChoosen = rand.nextInt(p.size());
            secondChoosen = rand.nextInt(p.size());
        }while(firstChoosen == secondChoosen || Objects.equals(p.get(firstChoosen),p.get(secondChoosen)));


            p.swap(firstChoosen,secondChoosen);



        double newValue = problem.getObjective().value(problem.model(), (R) p) ;    //Update
        offSpring.values()[0] = newValue;


    }


}
