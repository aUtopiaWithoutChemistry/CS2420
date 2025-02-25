package assign05;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * An implementation of Pivot chooser, to choose the first, end, and the middle index from
 * the given subarray and return the index of the median item as the pivot.
 *
 * @author Zifan Zuo and Xinrui Ou
 * @version 2025-02-06
 */
public class MedianOfThreePivotChooser<E extends Comparable<? super E>> implements PivotChooser<E>{
    @Override
    public int getPivotIndex(ArrayList<E> list, int leftIndex, int rightIndex) {
        if (leftIndex >= rightIndex) throw new IndexOutOfBoundsException();
        ArrayList<Integer> itemsIndex = new ArrayList<>();
        itemsIndex.add(leftIndex);
        itemsIndex.add(rightIndex);
        itemsIndex.add((rightIndex - leftIndex) / 2 + leftIndex);
        itemsIndex.sort(Comparator.comparing(list::get));
        return itemsIndex.get(1);
    }
}
