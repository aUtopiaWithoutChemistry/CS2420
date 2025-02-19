package assign05;

import java.util.ArrayList;

/**
 * @author Zifan Zuo and Xinrui Ou
 * @version 2025-02-06
 */
public class MergeSorter<E extends Comparable<? super E>> implements Sorter<E> {
    private int threshold;
    private ArrayList<E> backingArrayPreviousSorted, backingArrayWorking;

    /**
     * the constructor for MergeSorter, set the threshold for change to insertion sort.
     *
     * @param threshold when below threshold value, will change to insertion sort
     */
    public MergeSorter(int threshold) {
        if (threshold <= 0) throw new IllegalArgumentException();
        this.threshold = threshold;
    }


    @Override
    public void sort(ArrayList<E> list) {
        if (list.size() < threshold) threshold = list.size();
        backingArrayPreviousSorted = list;
        backingArrayWorking = (ArrayList<E>) list.clone();
        mergeSortRecursive(0, list.size() - 1);
    }

    /**
     * the recursive method of MergeSorter.
     *
     * @param startIndex the start index of current sub array
     * @param endIndex the end index of current sub array
     */
    private void mergeSortRecursive(int startIndex, int endIndex) {
        // base case
        if (endIndex - startIndex <= threshold) insertionSort(startIndex, endIndex);
        // recursive call
        else {
            int centerIndex = startIndex + (endIndex - startIndex) / 2;
            // left half
            mergeSortRecursive(startIndex, centerIndex);
            // right half
            mergeSortRecursive(centerIndex + 1, endIndex);
            // merge arr1 and arr2
            int i = startIndex;
            int j = centerIndex + 1;
            int insertIndex = startIndex;
            while (i <= centerIndex && j <= endIndex) {
                E first = backingArrayPreviousSorted.get(i);
                E second = backingArrayPreviousSorted.get(j);
                if (first.compareTo(second) <= 0) {
                    backingArrayWorking.set(insertIndex, first);
                    i++;
                } else {
                    backingArrayWorking.set(insertIndex, second);
                    j++;
                }
                insertIndex++;
            }
            // if the first half run out of item, then copy rest of second half into the working array
            if (i > centerIndex) {
                for (; j < endIndex + 1; j++, insertIndex++) {
                    backingArrayWorking.set(insertIndex, backingArrayPreviousSorted.get(j));
                }
                // if the second half run out of item, then copy rest of first half into the working array
            } else {
                for (; i < centerIndex + 1; i++, insertIndex++) {
                    backingArrayWorking.set(insertIndex, backingArrayPreviousSorted.get(i));
                }
            }
            for (int k = startIndex; k <= endIndex; k++) {
                backingArrayPreviousSorted.set(k, backingArrayWorking.get(k));
            }
        }
    }

    /**
     * a private helper method to do insertion sort on backing array
     *
     * @param startIndex the start index of the array to be sorted
     * @param endIndex the end index of the array to be sorted
     */
    private void insertionSort(int startIndex, int endIndex) {
        for (int i = startIndex + 1; i <= endIndex; i++) {
            int j = i - 1;
            boolean swapped = false;
            E insertionItem = backingArrayPreviousSorted.get(i);
            E currentComparingItem = backingArrayPreviousSorted.get(j);
            while (j >= startIndex && insertionItem.compareTo(currentComparingItem) < 0) {
                backingArrayPreviousSorted.set(j + 1, backingArrayPreviousSorted.get(j));
                swapped = true;
                j--;
                if (j >= 0) currentComparingItem = backingArrayPreviousSorted.get(j);
            }
            if (swapped) {
                j++;
                backingArrayPreviousSorted.set(j, insertionItem);
            }
        }
    }
}
