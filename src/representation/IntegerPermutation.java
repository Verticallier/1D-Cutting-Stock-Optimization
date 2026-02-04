package representation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class IntegerPermutation implements Permutation<Integer>{

    int[] nodes;


    public IntegerPermutation(List<Integer> nodeList) {
        nodes = nodeList.stream().mapToInt(x->x).toArray();
    }


    public IntegerPermutation(int[] newNodes) {
        nodes = newNodes;
    }


    @Override
    public Integer get(int index) {
        return nodes[index];
    }

    @Override
    public int size() {
        return nodes.length;
    }

    @Override
    public void swap(int i1, int i2) {
        int tmp = nodes[i1];
        nodes[i1]= nodes[i2];
        nodes[i2]= tmp;
    }

    @Override
    public Permutation copy() {
        int[] newNodes = new int[nodes.length];


        for (int i = 0; i < nodes.length; i++) {
            newNodes[i]= nodes[i];
        }
        return new IntegerPermutation(newNodes);
    }

    @Override
    public String toString() {
        return Arrays.toString(nodes);
    }

    public List<Integer> toList() {
        return Arrays.stream(nodes)
                .boxed()
                .collect(Collectors.toList());
    }




}