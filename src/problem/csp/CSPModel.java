
package problem.csp;


import problem.ProblemModel;
import representation.IntegerPermutation;

import java.util.ArrayList;
import java.util.List;


public class CSPModel implements ProblemModel<IntegerPermutation> {

    private final int stockLength;
    private final int[] itemLengths;
    private final int[] quantities;
    private final int[] expandedItems;
    private List<Integer> expandedItemsList;

    public CSPModel(int stockLength, int[] itemLengths, int[] quantities) {
        this.stockLength = stockLength;
        this.itemLengths = itemLengths;
        this.quantities = quantities;
        this.expandedItems = expand();
        expandedItemsList= new ArrayList<>();

    }

    /**
     * Expands itemLengths[] based on quantities[]
     * Example: lengths={45,30}, quantities={2,3}
     * expanded=[45,45,30,30,30]
     */
    private int[] expand() {
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < itemLengths.length; i++) {
            for (int q = 0; q < quantities[i]; q++) {
                list.add(itemLengths[i]);
            }
        }

        int[] arr = new int[list.size()];
        for (int i = 0; i < arr.length; i++)
            arr[i] = list.get(i);

        return arr;
    }

    public int maxLength(){
        int max =0;
        for (int i = 0; i < itemLengths.length; i++) {
            if(itemLengths[i]>max)
            { max=itemLengths[i];  }
        }
        return max;
    }

    public int[] getExpandedItems() {
        return expandedItems;
    }

    public List<Integer> getListOfExpandedItems(){

        expandedItemsList.clear();
        for (int x : expandedItems) expandedItemsList.add(x);
        return expandedItemsList;

    }

    public int getStockLength() {
        return stockLength;
    }

    public int itemCount() {
        return expandedItems.length;
    }




    @Override
    public boolean isFeasable(IntegerPermutation p) {

        boolean sizeOK = (p.size() == expandedItems.length);

        boolean instanceOK = (stockLength >= maxLength());

        return sizeOK && instanceOK;
    }


    @Override
    public String toString() {
        return "CSPModel";
    }

}

