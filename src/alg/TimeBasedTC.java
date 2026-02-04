package alg;

import problem.OptimizationProblem;
import problem.ProblemModel;
import representation.Representation;
public class TimeBasedTC<PM extends ProblemModel<R>, R extends Representation>
        implements TerminalCondition<PM, R> {

    private final long maxMillis;
    private long startTime = -1;

    public TimeBasedTC(long maxMillis) {
        this.maxMillis = maxMillis;
    }

    @Override
    public boolean isSatisfied(Metaheuristic<PM, R> alg, OptimizationProblem<PM, R> problem) {

        if (alg.iterationCount() == 0) {
            startTime = System.currentTimeMillis();
        }

        return (System.currentTimeMillis() - startTime) >= maxMillis;
    }
}

