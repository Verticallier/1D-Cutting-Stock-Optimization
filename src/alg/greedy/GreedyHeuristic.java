package alg.greedy;

import problem.ProblemModel;
import representation.Representation;

public interface GreedyHeuristic<PM extends ProblemModel<R>, R extends Representation> {
    R solve( PM pm);
}
