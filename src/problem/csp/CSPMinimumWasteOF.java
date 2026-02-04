package problem.csp;

import problem.ObjectiveFunction;
import problem.ObjectiveType;
import representation.IntegerPermutation;

public class CSPMinimumWasteOF implements ObjectiveFunction<IntegerPermutation,CSPModel> {
    @Override
    public ObjectiveType type() {
        return ObjectiveType.Minimization;
    }

    public double value(CSPModel cspModel, IntegerPermutation ip) {

        int stockLength = cspModel.getStockLength();

        int used = 0;
        double totalWaste = 0.0;


        for (int i = 0; i < ip.size(); i++) {
            int item = ip.get(i);

            if (used + item <= stockLength) {
                used += item;
            } else {

                totalWaste += (stockLength - used);

                used = item;
            }
        }

        totalWaste += (stockLength - used); //last waste

        return totalWaste;
    }


}



