package assign05;

import java.util.ArrayList;

public class MedianOfThreePivotChooser<E extends Comparable<? super E>> implements PivotChooser<E>{
    @Override
    public int getPivotIndex(ArrayList list, int leftIndex, int rightIndex) {
        return 0;
    }
}
