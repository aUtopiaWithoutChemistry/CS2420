package assign05;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SorterTest {
    private MergeSorter<Integer> mergeSorter;
    private QuickSorter<Integer> quickSorterFirstPivot;
    private QuickSorter<Integer> quickSorterRandomPivot;
    private QuickSorter<Integer> quickSorterMedianPivot;

    @BeforeEach
    void setUp() {
        mergeSorter = new MergeSorter<>(5); // Threshold set to 5
        quickSorterFirstPivot = new QuickSorter<>(new FirstPivotChooser<Integer>());
        quickSorterRandomPivot = new QuickSorter<>(new RandomPivotChooser<Integer>());
        quickSorterMedianPivot = new QuickSorter<>(new MedianOfThreePivotChooser<Integer>());
    }

    // Helper method to create a list
    private ArrayList<Integer> toArrayList(Integer... elements) {
        return new ArrayList<>(Arrays.asList(elements));
    }

    // 📌 Test: Empty List
    @Test
    void testEmptyList() {
        ArrayList<Integer> list = new ArrayList<>();
        mergeSorter.sort(list);
        assertEquals(new ArrayList<>(), list);

        quickSorterFirstPivot.sort(list);
        assertEquals(new ArrayList<>(), list);
    }

    // 📌 Test: Single Element List
    @Test
    void testSingleElement() {
        ArrayList<Integer> list = toArrayList(42);
        mergeSorter.sort(list);
        assertEquals(toArrayList(42), list);

        quickSorterFirstPivot.sort(list);
        assertEquals(toArrayList(42), list);
    }

    // 📌 Test: Already Sorted List
    @Test
    void testAlreadySorted() {
        ArrayList<Integer> list = toArrayList(1, 2, 3, 4, 5);
        mergeSorter.sort(list);
        assertEquals(toArrayList(1, 2, 3, 4, 5), list);

        quickSorterFirstPivot.sort(list);
        assertEquals(toArrayList(1, 2, 3, 4, 5), list);
    }

    // 📌 Test: Reverse Sorted List
    @Test
    void testReverseSorted() {
        ArrayList<Integer> list = toArrayList(5, 4, 3, 2, 1);
        mergeSorter.sort(list);
        assertEquals(toArrayList(1, 2, 3, 4, 5), list);

        quickSorterFirstPivot.sort(list);
        assertEquals(toArrayList(1, 2, 3, 4, 5), list);
    }

    // 📌 Test: List with Duplicates
    @Test
    void testListWithDuplicates() {
        ArrayList<Integer> list = toArrayList(4, 2, 2, 1, 3, 3, 5, 1);
        mergeSorter.sort(list);
        assertEquals(toArrayList(1, 1, 2, 2, 3, 3, 4, 5), list);

        quickSorterFirstPivot.sort(list);
        assertEquals(toArrayList(1, 1, 2, 2, 3, 3, 4, 5), list);
    }

    // 📌 Test: Large List (Performance)
    @Test
    void testLargeList() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 10000; i >= 1; i--) {
            list.add(i);
        }
        mergeSorter.sort(list);
        for (int i = 0; i < list.size(); i++) {
            assertEquals(i + 1, list.get(i));
        }

        list.clear();
        for (int i = 10000; i >= 1; i--) {
            list.add(i);
        }
        quickSorterFirstPivot.sort(list);
        for (int i = 0; i < list.size(); i++) {
            assertEquals(i + 1, list.get(i));
        }
    }

    // 📌 Test: MergeSorter Stability Check
    @Test
    void testMergeSorterStability() {
        MergeSorter<TestObject> mergeSorter1 = new MergeSorter<>(5);
        ArrayList<TestObject> list = new ArrayList<>(Arrays.asList(
                new TestObject(1, "A"),
                new TestObject(2, "B"),
                new TestObject(1, "C"),
                new TestObject(2, "D")
        ));

        mergeSorter1.sort(list);

        assertEquals("A", list.get(0).value);
        assertEquals("C", list.get(1).value);
        assertEquals("B", list.get(2).value);
        assertEquals("D", list.get(3).value);
    }

    // 📌 Test: QuickSorter Different Pivot Strategies
    @Test
    void testQuickSorterWithDifferentPivots() {
        ArrayList<Integer> list1 = toArrayList(3, 6, 2, 8, 7, 1, 5, 4);
        ArrayList<Integer> list2 = new ArrayList<>(list1);
        ArrayList<Integer> list3 = new ArrayList<>(list1);

        quickSorterFirstPivot.sort(list1);
        quickSorterRandomPivot.sort(list2);
        quickSorterMedianPivot.sort(list3);

        List<Integer> expected = toArrayList(1, 2, 3, 4, 5, 6, 7, 8);
        assertEquals(expected, list1);
        assertEquals(expected, list2);
        assertEquals(expected, list3);
    }

    // 📌 Test: QuickSorter Stability (Should NOT be stable)
    @Test
    void testQuickSorterInstability() {
        QuickSorter<TestObject> quickSorter1 = new QuickSorter<>(new FirstPivotChooser<TestObject>());
        ArrayList<TestObject> list = new ArrayList<>(Arrays.asList(
                new TestObject(1, "A"),
                new TestObject(2, "B"),
                new TestObject(1, "C"),
                new TestObject(2, "D")
        ));

        quickSorter1.sort(list);

        // Order of equal elements is NOT guaranteed
        boolean stable = list.get(0).value.equals("A") && list.get(1).value.equals("C");
        boolean unstable = list.get(0).value.equals("C") && list.get(1).value.equals("A");

        assertTrue(stable || unstable);
    }

    // Custom class for stability testing
    private static class TestObject implements Comparable<TestObject> {
        int key;
        String value;

        TestObject(int key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public int compareTo(TestObject other) {
            return Integer.compare(this.key, other.key);
        }
    }
}