package alg.ga;

import alg.BaseMetaheuristic;
import alg.InitialSolutionGenerator;
import alg.TerminalCondition;
import problem.OptimizationProblem;
import problem.ProblemModel;
import representation.Representation;
import representation.Solution;
import utils.RandUtils;

import java.util.ArrayList;
import java.util.List;

public class GA<PM extends ProblemModel<R>, R extends Representation> extends BaseMetaheuristic<PM,R> {

    public InitialSolutionGenerator<PM,R> isg;
    public int populationSize;
    List<Solution> population;
    public ParentSelector parentSelector;
    public VictimSelector victimSelector;
    private double crossOverRate;
    private CrossOverOperator crossOverOperator;
    private double mutationRate;
    private MutationOperation mutationOperator;

    public GA(TerminalCondition<PM, R> terminalCondition) {
        super(terminalCondition);
    }

    @Override
    protected Solution _perform(OptimizationProblem<PM, R> problem) {

        while (!terminalCondition.isSatisfied(this,problem))
        {
            iterationCount++;
            List<Solution> parents = selectParents(problem);
            List<Solution> offspring = produceOffspring(problem,parents);
            applyMutation(problem,offspring);
            replacePopulation(problem,offspring);
        }

        return bestSolution;
    }

    protected void replacePopulation(OptimizationProblem<PM, R> problem, List<Solution> offspring) {
        List<Solution>  victims = victimSelector.select(problem, population,offspring.size());
        population.removeAll(victims);
        population.addAll(offspring);
    }

    protected void applyMutation(OptimizationProblem<PM, R> problem, List<Solution> offspring) {
        for (Solution s : offspring)
        {
            if (RandUtils.rollDice(mutationRate))
            {
                mutationOperator.apply(problem,s);
                updateBest(problem,s);

            }
        }
    }

    protected List<Solution> selectParents(OptimizationProblem<PM, R> problem) {

        List<Solution> parents = parentSelector.select(problem, population);
        return parents;
    }

    protected List<Solution> produceOffspring(OptimizationProblem<PM, R> problem, List<Solution> parents) {

        List<Solution> offSpring = new ArrayList<>();

        for (int p1 = 0; p1 < parents.size(); p1++) {
            for (int p2 = p1+1; p2 < parents.size(); p2++) {
                if (RandUtils.rollDice(crossOverRate))
                {
                    Solution parent1 = parents.get(p1);
                    Solution parent2 = parents.get(p2);

                    List<Solution> children =  crossOverOperator.apply(problem,parent1,parent2);
                    offSpring.addAll(children);
                    updateBest(problem,children);
                }

            }
        }

        return offSpring;
    }


    @Override
    protected void _init(OptimizationProblem<PM, R> problem) {
        createInitialPopulation(problem);
        iterationCount =0;
    }

    protected void createInitialPopulation(OptimizationProblem<PM, R> problem) {
        population=  new ArrayList<>();
        for (int i = 0; i < populationSize ; i++) {
            Solution s = isg.generate(problem);
            updateBest(problem,s);
            population.add(s);
        }
    }


    public double getCrossOverRate() {
        return crossOverRate;
    }

    public void setCrossOverRate(double crossOverRate) {
        this.crossOverRate = crossOverRate;
    }

    public CrossOverOperator getCrossOverOperator() {
        return crossOverOperator;
    }

    public void setCrossOverOperator(CrossOverOperator crossOverOperator) {
        this.crossOverOperator = crossOverOperator;
    }

    public double getMutationRate() {
        return mutationRate;
    }

    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    public MutationOperation getMutationOperator() {
        return mutationOperator;
    }

    public void setMutationOperator(MutationOperation mutationOperator) {
        this.mutationOperator = mutationOperator;
    }

    public VictimSelector getVictimSelector() {
        return victimSelector;
    }

    public void setVictimSelector(VictimSelector victimSelector) {
        this.victimSelector = victimSelector;
    }

    public ParentSelector getParentSelector() {
        return parentSelector;
    }

    public void setParentSelector(ParentSelector parentSelector) {
        this.parentSelector = parentSelector;
    }

    public List<Solution> getPopulation() {
        return population;
    }

    public void setPopulation(List<Solution> population) {
        this.population = population;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public InitialSolutionGenerator<PM, R> getIsg() {
        return isg;
    }

    public void setIsg(InitialSolutionGenerator<PM, R> isg) {
        this.isg = isg;
    }


    @Override
    public String toString() {
        return "GA-" + isg.toString();
    }


}
