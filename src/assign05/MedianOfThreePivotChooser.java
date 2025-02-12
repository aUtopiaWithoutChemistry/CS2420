package assign05;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class MedianOfThreePivotChooser<E extends Comparable<? super E>> implements PivotChooser<E>{
    @Override
    public int getPivotIndex(ArrayList<E> list, int leftIndex, int rightIndex) {
        Random rng = new Random();
        ArrayList<Integer> items = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            items.add(rng.nextInt(rightIndex - leftIndex + 1) + leftIndex);
        }
        items.sort(Comparator.comparing(list::get));
        return items.get(1);
    }
}
