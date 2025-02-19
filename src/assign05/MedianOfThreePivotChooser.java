package assign05;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author Zifan Zuo and Xinrui Ou
 * @version 2025-02-06
 */
public class MedianOfThreePivotChooser<E extends Comparable<? super E>> implements PivotChooser<E>{
    @Override
    public int getPivotIndex(ArrayList<E> list, int leftIndex, int rightIndex) {
        ArrayList<Integer> itemsIndex = new ArrayList<>();
        itemsIndex.add(leftIndex);
        itemsIndex.add(rightIndex);
        itemsIndex.add((rightIndex - leftIndex) / 2 + leftIndex);
        itemsIndex.sort(Comparator.comparing(list::get));
        return itemsIndex.get(1);
    }
}
