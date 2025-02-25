package assign05;

import java.util.ArrayList;
import java.util.Random;

/**
 * An implementation of Pivot chooser, to choose a random index from the subarray,
 * and return this index as pivot.
 *
 * @author Zifan Zuo and Xinrui Ou
 * @version 2025-02-06
 */
public class RandomPivotChooser<E extends Comparable<? super E>> implements PivotChooser<E>{
    @Override
    public int getPivotIndex(ArrayList<E> list, int leftIndex, int rightIndex) {
        if (leftIndex >= rightIndex) throw new IndexOutOfBoundsException();
        Random rng = new Random();
        return rng.nextInt(rightIndex - leftIndex + 1) + leftIndex;
    }
}
