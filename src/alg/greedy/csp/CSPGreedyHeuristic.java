package alg.greedy.csp;

import alg.greedy.GreedyHeuristic;
import problem.csp.CSPModel;
import representation.IntegerPermutation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Greedy heuristic for Cutting Stock:
 *
 *   Expand item list from model
 *   Sort items in descending order (longest-first)
 *   Return an IntegerPermutation with that order
 */
public class CSPGreedyHeuristic implements GreedyHeuristic<CSPModel, IntegerPermutation> {

    @Override
    public IntegerPermutation solve(CSPModel cspModel) {

        int[] items = cspModel.getExpandedItems().clone();

        Arrays.sort(items);
        reverse(items);

        List<Integer> list = Arrays.stream(items).boxed().collect(Collectors.toList());
        return new IntegerPermutation(list);

    }

    private void reverse(int[] arr) {
        int L = 0, R = arr.length - 1;
        while (L < R) {
            int tmp = arr[L];
            arr[L] = arr[R];
            arr[R] = tmp;
            L++;
            R--;
        }
    }
}
