package assign10;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timing.ArrayListGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeltaSorterTest {
    private List<Integer> descendingList, ascendingList, descendingAnswer, ascendingAnswer;
    private final int problemSize = 100;

    @BeforeEach
    void setUp() {
        descendingList = ArrayListGenerator.generateDescendingDeltaSortedArrayList(problemSize, 5);
        ascendingList = descendingList.reversed();

        descendingAnswer = new ArrayList<>();
        ascendingAnswer = new ArrayList<>();
        descendingAnswer.addAll(descendingList);
        ascendingAnswer.addAll(ascendingList);

        Collections.sort(descendingAnswer, (i1, i2) -> i2 - i1);
        Collections.sort(ascendingAnswer);
    }

    @Test
    void testDefaultSorter() {
        System.out.println(descendingList);
        DeltaSorter.sort(descendingList, 5);
        System.out.println(descendingList);
        assertArrayEquals(descendingAnswer.toArray(), descendingList.toArray());
    }

    @Test
    void testCustomizedSorter() {
        DeltaSorter.sort(ascendingList, 5, (o1, o2) -> o2 - o1);
        assertArrayEquals(ascendingAnswer.toArray(), ascendingList.toArray());
    }
}