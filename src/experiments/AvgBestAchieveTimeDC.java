package experiments;

import alg.Metaheuristic;

public class AvgBestAchieveTimeDC implements DataCollector{

    double bestAchieveTime;
    int count=0;

    @Override
    public void collect(Metaheuristic alg) {
        double val = alg.getBestAchieveTime();
        count++;
        bestAchieveTime +=val;
    }

    @Override
    public String resultString() {
        if (count==0)
            count++;
        double avg = bestAchieveTime /count;
        return "BEST_TIME:"+ avg;
    }

}
