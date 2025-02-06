package assign05;

import java.util.ArrayList;

/**
 * @author Zifan Zuo and Xinrui Ou
 * @version 2025-02-06
 */
public class MergeSorter<E extends Comparable<? super E>> implements Sorter<E>{
    private int threshold;

    public MergeSorter(int threshold) {
        if (threshold < 0) throw new IllegalArgumentException();
        this.threshold = threshold;
    }

    @Override
    public void sort(ArrayList list) {

    }

    private void mergeSortRecursive(ArrayList list) {

    }
}
