package assign05;

import java.util.ArrayList;

/**
 * @author Zifan Zuo and Xinrui Ou
 * @version 2025-02-06
 */
public class FirstPivotChooser<E extends Comparable<? super E>> implements PivotChooser<E>{
    @Override
    public int getPivotIndex(ArrayList<E> list, int leftIndex, int rightIndex) {
        if (leftIndex >= rightIndex) throw new IndexOutOfBoundsException();
        return leftIndex;
    }
}
