package alg.ga;

import problem.ObjectiveType;
import problem.OptimizationProblem;
import problem.ProblemModel;
import representation.Representation;
import representation.Solution;

import java.util.*;


/**
 * TODO: Performs victim selection acc.to fitness (disproportional)
 */



/**
 * Performs victim selection according to fitness (disproportional)
 * Higher chance to remove worse individuals.
 */
public class FitnessBasedVictimSelector implements VictimSelector {

    private final Random rand = new Random();

    @Override
    public <R extends Representation, PM extends ProblemModel<R>>
    List<Solution> select(OptimizationProblem<PM, R> problem,
                          List<Solution> population,
                          int count) {

        int n = population.size();
        if (n == 0 || count <= 0) return Collections.emptyList();
        if (count >= n) return new ArrayList<>(population);

        var type = problem.getObjective().type();


        double[] weights = new double[n];
        double total = 0.0;

        for (int i = 0; i < n; i++) {
            double v = population.get(i).value();
            double w;

            if (type == ObjectiveType.Minimization) {

                w = v;
            } else {

                w = 1.0 / (1.0 + v); //minimiation
            }

            if (Double.isNaN(w) || w < 0) w = 0;
            weights[i] = w;
            total += w;
        }


        List<Solution> victims = new ArrayList<>(count);
        if (total == 0.0) {
            while (victims.size() < count) {
                Solution cand = population.get(rand.nextInt(n));
                if (!victims.contains(cand)) {
                    victims.add(cand);
                }
            }
            return victims;
        }


        double[] cumulative = new double[n];
        double sum = 0.0;
        for (int i = 0; i < n; i++) {
            sum += weights[i] / total;
            cumulative[i] = sum;
        }

        // duplicate check
        int safety = 0;
        int maxTries = 50 * n;

        while (victims.size() < count && safety < maxTries) {
            safety++;

            double r = rand.nextDouble();
            Solution picked = null;

            for (int i = 0; i < n; i++) {
                if (r <= cumulative[i]) {
                    picked = population.get(i);
                    break;
                }
            }

            if (picked != null && !victims.contains(picked)) {
                victims.add(picked);
            }
        }


        while (victims.size() < count) {
            Solution cand = population.get(rand.nextInt(n));
            if (!victims.contains(cand)) {
                victims.add(cand);
            }
        }

        return victims;
    }
}
