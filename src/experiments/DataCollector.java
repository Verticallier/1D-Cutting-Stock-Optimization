package experiments;

import alg.Metaheuristic;

public interface DataCollector {

    void collect(Metaheuristic alg);
    String resultString();
}
