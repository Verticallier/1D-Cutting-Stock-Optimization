package alg.ga;

import problem.ObjectiveType;
import problem.OptimizationProblem;
import problem.ProblemModel;
import representation.Representation;
import representation.Solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * TODO: Implement RoulletteWheel Selection that selects individual acc. to its fitness
 *
 */
public class RouletteWheelSelection implements ParentSelector{
    private Random rand = new Random();


    @Override
    public <R extends Representation, PM extends ProblemModel<R>> List<Solution> select(OptimizationProblem<PM, R> problem, List<Solution> population) {


        List<Solution> selected = new ArrayList<>();
        int populationSize = population.size();
        double totalFitness = 0;
        double[] fitnessValues= new double[populationSize];

        if( problem.getObjective().type() == ObjectiveType.Minimization){
        for (int i = 0; i < populationSize; i++) {

            fitnessValues[i] = 1.0 / (1+ population.get(i).value());

         }
        }
        else {

            for (int i = 0; i < populationSize; i++) {

                fitnessValues[i] = population.get(i).value();

            }
        }


        for (int i = 0; i < population.size(); i++) {
            totalFitness += fitnessValues[i];
        }

        double[] cumulative = new double[populationSize];
        double sum = 0.0;

        for (int i = 0; i < populationSize; i++) {
            sum += fitnessValues[i] / totalFitness;
            cumulative[i] = sum;
        }

        int numberOfParents =2;
        for (int k = 0; k < numberOfParents; k++) {
            double r = rand.nextDouble();

            for (int i = 0; i < populationSize; i++) {
                if (r <= cumulative[i]) {

                    selected.add(population.get(i));

                    break;
                }
            }
        }

        return selected;



    }
}
