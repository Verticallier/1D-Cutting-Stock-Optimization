package experiments;

import alg.Metaheuristic;

public class AvgIterationDC implements DataCollector{

    double iteration;
    int count=0;

    @Override
    public void collect(Metaheuristic alg) {
        double val = alg.getIterationCount();
        count++;
        iteration +=val;
    }

    @Override
    public String resultString() {
        if (count==0)
            count++;
        double avg = iteration/count;
        return "ItCnt:"+ avg;
    }
}
