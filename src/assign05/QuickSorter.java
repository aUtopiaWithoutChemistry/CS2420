package assign05;

import java.util.ArrayList;

/**
 * @author Zifan Zuo and Xinrui Ou
 * @version 2025-02-06
 */
public class QuickSorter<E extends Comparable<? super E>> implements Sorter<E>{
    private final PivotChooser<E> chooser;
    private ArrayList<E> list;

    public QuickSorter(PivotChooser<E> chooser) {
        this.chooser = chooser;
    }

    @Override
    public void sort(ArrayList<E> list) {
        this.list = list;
        quickSorterRecur(chooser, 0, list.size() - 1);
    }

    private void quickSorterRecur(PivotChooser<E> chooser, int startIndex, int endIndex) {
        // base case
        if (endIndex - startIndex < 1) return;
        // find pivot index
        int pivotIndex = chooser.getPivotIndex(list, startIndex, endIndex);
        E pivot = list.get(pivotIndex);
        // partition
        swap(pivotIndex, endIndex);
        int i = startIndex;
        int j = endIndex - 1;
        while (i <= j) {
            // if item at i is bigger than pivot and item at j is smaller than pivot then swap
            if (list.get(i).compareTo(pivot) < 0) i++;
            else if(list.get(j).compareTo(pivot) >= 0) j--;
            else swap(i, j);
        }
        swap(i, endIndex);
        // recursively sort sub arrays
        quickSorterRecur(chooser, startIndex, i - 1);
        quickSorterRecur(chooser, i + 1, endIndex);
    }

    private void swap(int firstIndex, int secondIndex) {
        E temp = list.get(secondIndex);
        list.set(secondIndex, list.get(firstIndex));
        list.set(firstIndex, temp);
    }
}
