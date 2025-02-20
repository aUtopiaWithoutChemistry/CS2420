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

    @Test
    void testMergeSortInvalidThreshold() {
        MergeSorter<Integer> ms;
        assertThrows(IllegalArgumentException.class, () -> new MergeSorter<>(-100));
    }

    // 📌 Test: MergeSorter with threshold 1 on 1-element list
    @Test
    void testMergeSortSingleElementThreshold1() {
        MergeSorter<Integer> ms = new MergeSorter<>(1);
        ArrayList<Integer> list = toArrayList(42);
        ms.sort(list);
        assertEquals(toArrayList(42), list);
    }

    @Test
    void testFirstPivotChooserEmptyList() {
        FirstPivotChooser<Integer> chooser = new FirstPivotChooser<>();
        ArrayList<Integer> list = new ArrayList<>();
        assertThrows(IndexOutOfBoundsException.class, () -> {
            chooser.getPivotIndex(list, 0, 0);
        });
    }

    // 📌 Test: QuickSorter on 4-element list with median pivot
    @Test
    void testQuickSort4ElementsMedianPivot() {
        ArrayList<Integer> list = toArrayList(3, 1, 4, 2);
        quickSorterMedianPivot.sort(list);
        assertEquals(toArrayList(1, 2, 3, 4), list);
    }

    // 📌 Test: MergeSorter on large list with threshold 100
    @Test
    void testMergeSortLargeListThreshold100() {
        MergeSorter<Integer> ms = new MergeSorter<>(100);
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 10000; i >= 1; i--) {
            list.add(i);
        }
        ms.sort(list);
        for (int i = 0; i < list.size(); i++) {
            assertEquals(i + 1, list.get(i));
        }
    }

    // 📌 Test: MergeSorter on custom-type list with duplicates and threshold 1
    @Test
    void testMergeSortCustomTypeWithDuplicates() {
        MergeSorter<TestObject> ms = new MergeSorter<>(1);
        ArrayList<TestObject> list = new ArrayList<>(Arrays.asList(
                new TestObject(1, "A"),
                new TestObject(1, "B"),
                new TestObject(2, "C"),
                new TestObject(2, "D")
        ));
        ms.sort(list);
        assertEquals("A", list.get(0).value);
        assertEquals("B", list.get(1).value);
        assertEquals("C", list.get(2).value);
        assertEquals("D", list.get(3).value);
    }

    // 📌 Test: QuickSorter on empty list with median pivot
    @Test
    void testQuickSortEmptyListMedianPivot() {
        ArrayList<Integer> list = new ArrayList<>();
        quickSorterMedianPivot.sort(list);
        assertEquals(new ArrayList<>(), list);
    }

    // 📌 Test: MergeSorter on large list with threshold 1
    @Test
    void testMergeSortLargeListThreshold1() {
        MergeSorter<Integer> ms = new MergeSorter<>(1);
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 10000; i >= 1; i--) {
            list.add(i);
        }
        ms.sort(list);
        for (int i = 0; i < list.size(); i++) {
            assertEquals(i + 1, list.get(i));
        }
    }

    // 📌 Test: QuickSorter on large sorted list with random pivot
    @Test
    void testQuickSortLargeSortedRandomPivot() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 10000; i++) {
            list.add(i);
        }
        quickSorterRandomPivot.sort(list);
        for (int i = 0; i < list.size(); i++) {
            assertEquals(i + 1, list.get(i));
        }
    }

    // 📌 Test: QuickSorter on large duplicates with first pivot
    @Test
    void testQuickSortLargeDuplicatesFirstPivot() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(i % 10);
        }
        ArrayList<Integer> expected = new ArrayList<>(list);
        expected.sort(Integer::compareTo);
        quickSorterFirstPivot.sort(list);
        assertEquals(expected, list);
    }

    // 📌 Test: MergeSorter on large list with threshold 10
    @Test
    void testMergeSortLargeListThreshold10() {
        MergeSorter<Integer> ms = new MergeSorter<>(10);
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 10000; i >= 1; i--) {
            list.add(i);
        }
        ms.sort(list);
        for (int i = 0; i < list.size(); i++) {
            assertEquals(i + 1, list.get(i));
        }
    }

    // 📌 Test: QuickSorter on large sorted list with first pivot
    @Test
    void testQuickSortLargeSortedFirstPivot() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 10000; i++) {
            list.add(i);
        }
        quickSorterFirstPivot.sort(list);
        for (int i = 0; i < list.size(); i++) {
            assertEquals(i + 1, list.get(i));
        }
    }

    // 📌 Test: QuickSorter on 1-element list with random pivot
    @Test
    void testQuickSortSingleElementRandomPivot() {
        ArrayList<Integer> list = toArrayList(42);
        quickSorterRandomPivot.sort(list);
        assertEquals(toArrayList(42), list);
    }

    // 📌 Test: QuickSorter on large list with random pivot
    @Test
    void testQuickSortLargeListRandomPivot() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 10000; i >= 1; i--) {
            list.add(i);
        }
        quickSorterRandomPivot.sort(list);
        for (int i = 0; i < list.size(); i++) {
            assertEquals(i + 1, list.get(i));
        }
    }

    // 📌 Test: QuickSorter on 4-element list with first pivot
    @Test
    void testQuickSort4ElementsFirstPivot() {
        ArrayList<Integer> list = toArrayList(3, 1, 4, 2);
        quickSorterFirstPivot.sort(list);
        assertEquals(toArrayList(1, 2, 3, 4), list);
    }

    // 📌 Test: QuickSorter on medium list with first pivot
    @Test
    void testQuickSortMediumListFirstPivot() {
        ArrayList<Integer> list = toArrayList(5, 3, 9, 1, 4, 8, 6, 2, 7, 0);
        quickSorterFirstPivot.sort(list);
        assertEquals(toArrayList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9), list);
    }

    // 📌 Test: MergeSorter on large duplicates with threshold 1
    @Test
    void testMergeSortLargeDuplicatesThreshold1() {
        MergeSorter<Integer> ms = new MergeSorter<>(1);
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(i % 10);
        }
        ArrayList<Integer> expected = new ArrayList<>(list);
        expected.sort(Integer::compareTo);
        ms.sort(list);
        assertEquals(expected, list);
    }

    // 📌 Test: QuickSorter on medium list with median pivot
    @Test
    void testQuickSortMediumListMedianPivot() {
        ArrayList<Integer> list = toArrayList(5, 3, 9, 1, 4, 8, 6, 2, 7, 0);
        quickSorterMedianPivot.sort(list);
        assertEquals(toArrayList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9), list);
    }

    // 📌 Test: QuickSorter on custom-type with first pivot
    @Test
    void testQuickSortCustomTypeFirstPivot() {
        QuickSorter<TestObject> qs = new QuickSorter<>(new FirstPivotChooser<TestObject>());
        ArrayList<TestObject> list = new ArrayList<>(Arrays.asList(
                new TestObject(2, "B"),
                new TestObject(1, "A"),
                new TestObject(3, "C"),
                new TestObject(2, "D")
        ));
        qs.sort(list);
        assertEquals(1, list.get(0).key);
        assertEquals(2, list.get(1).key);
        assertEquals(2, list.get(2).key);
        assertEquals(3, list.get(3).key);
    }

    // 📌 Test: QuickSorter on custom-type with median pivot
    @Test
    void testQuickSortCustomTypeMedianPivot() {
        QuickSorter<TestObject> qs = new QuickSorter<>(new MedianOfThreePivotChooser<TestObject>());
        ArrayList<TestObject> list = new ArrayList<>(Arrays.asList(
                new TestObject(3, "C"),
                new TestObject(1, "A"),
                new TestObject(2, "B"),
                new TestObject(4, "D")
        ));
        qs.sort(list);
        assertEquals(1, list.get(0).key);
        assertEquals(2, list.get(1).key);
        assertEquals(3, list.get(2).key);
        assertEquals(4, list.get(3).key);
    }

    // 📌 Test: QuickSorter on large sorted list with median pivot
    @Test
    void testQuickSortLargeSortedMedianPivot() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 10000; i++) {
            list.add(i);
        }
        quickSorterMedianPivot.sort(list);
        for (int i = 0; i < list.size(); i++) {
            assertEquals(i + 1, list.get(i));
        }
    }

    // 📌 Test: MergeSorter on 4-element list with threshold 1
    @Test
    void testMergeSort4ElementsThreshold1() {
        MergeSorter<Integer> ms = new MergeSorter<>(1);
        ArrayList<Integer> list = toArrayList(3, 1, 4, 2);
        ms.sort(list);
        assertEquals(toArrayList(1, 2, 3, 4), list);
    }

    // 📌 Test: MergeSorter on empty list with threshold 1
    @Test
    void testMergeSortEmptyListThreshold1() {
        MergeSorter<Integer> ms = new MergeSorter<>(1);
        ArrayList<Integer> list = new ArrayList<>();
        ms.sort(list);
        assertEquals(new ArrayList<>(), list);
    }

    // 📌 Test: QuickSorter on large list with median pivot
    @Test
    void testQuickSortLargeListMedianPivot() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 10000; i >= 1; i--) {
            list.add(i);
        }
        quickSorterMedianPivot.sort(list);
        for (int i = 0; i < list.size(); i++) {
            assertEquals(i + 1, list.get(i));
        }
    }

    // 📌 Test: QuickSorter on large duplicates with median pivot
    @Test
    void testQuickSortLargeDuplicatesMedianPivot() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(i % 10);
        }
        ArrayList<Integer> expected = new ArrayList<>(list);
        expected.sort(Integer::compareTo);
        quickSorterMedianPivot.sort(list);
        assertEquals(expected, list);
    }

    // 📌 Test: QuickSorter on 1-element list with median pivot
    @Test
    void testQuickSortSingleElementMedianPivot() {
        ArrayList<Integer> list = toArrayList(42);
        quickSorterMedianPivot.sort(list);
        assertEquals(toArrayList(42), list);
    }

    // 📌 Test: QuickSorter on medium list with random pivot
    @Test
    void testQuickSortMediumListRandomPivot() {
        ArrayList<Integer> list = toArrayList(5, 3, 9, 1, 4, 8, 6, 2, 7, 0);
        quickSorterRandomPivot.sort(list);
        assertEquals(toArrayList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9), list);
    }

    // 📌 Test: MergeSorter on medium list with threshold 10
    @Test
    void testMergeSortMediumListThreshold10() {
        MergeSorter<Integer> ms = new MergeSorter<>(10);
        ArrayList<Integer> list = toArrayList(5, 3, 9, 1, 4, 8, 6, 2, 7, 0);
        ms.sort(list);
        assertEquals(toArrayList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9), list);
    }

    // 📌 Test: QuickSorter on custom-type with random pivot
    @Test
    void testQuickSortCustomTypeRandomPivot() {
        QuickSorter<TestObject> qs = new QuickSorter<>(new RandomPivotChooser<TestObject>());
        ArrayList<TestObject> list = new ArrayList<>(Arrays.asList(
                new TestObject(3, "C"),
                new TestObject(1, "A"),
                new TestObject(2, "B"),
                new TestObject(4, "D")
        ));
        qs.sort(list);
        assertEquals(1, list.get(0).key);
        assertEquals(2, list.get(1).key);
        assertEquals(3, list.get(2).key);
        assertEquals(4, list.get(3).key);
    }

    // 📌 Test: QuickSorter on large duplicates with random pivot
    @Test
    void testQuickSortLargeDuplicatesRandomPivot() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(i % 10);
        }
        ArrayList<Integer> expected = new ArrayList<>(list);
        expected.sort(Integer::compareTo);
        quickSorterRandomPivot.sort(list);
        assertEquals(expected, list);
    }

    // 📌 Test: QuickSorter on 4-element list with random pivot
    @Test
    void testQuickSort4ElementsRandomPivot() {
        ArrayList<Integer> list = toArrayList(3, 1, 4, 2);
        quickSorterRandomPivot.sort(list);
        assertEquals(toArrayList(1, 2, 3, 4), list);
    }

    // 📌 Test: MergeSorter on large sorted list with threshold 1
    @Test
    void testMergeSortLargeSortedThreshold1() {
        MergeSorter<Integer> ms = new MergeSorter<>(1);
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 10000; i++) {
            list.add(i);
        }
        ms.sort(list);
        for (int i = 0; i < list.size(); i++) {
            assertEquals(i + 1, list.get(i));
        }
    }

    // 📌 Test: QuickSorter on empty list with random pivot
    @Test
    void testQuickSortEmptyListRandomPivot() {
        ArrayList<Integer> list = new ArrayList<>();
        quickSorterRandomPivot.sort(list);
        assertEquals(new ArrayList<>(), list);
    }

    // 📌 Test: MergeSorter with threshold larger than list size
    @Test
    void testMergeSortThresholdLargerThanList() {
        MergeSorter<Integer> ms = new MergeSorter<>(100);
        ArrayList<Integer> list = toArrayList(3, 1, 4, 2);
        ms.sort(list);
        assertEquals(toArrayList(1, 2, 3, 4), list);
    }

    // 📌 Test: MergeSorter on medium list with threshold 1
    @Test
    void testMergeSortMediumListThreshold1() {
        MergeSorter<Integer> ms = new MergeSorter<>(1);
        ArrayList<Integer> list = toArrayList(5, 3, 9, 1, 4, 8, 6, 2, 7, 0);
        ms.sort(list);
        assertEquals(toArrayList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9), list);
    }

    // 📌 Test: QuickSorter on large list with first pivot
    @Test
    void testQuickSortLargeListFirstPivot() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 10000; i >= 1; i--) {
            list.add(i);
        }
        quickSorterFirstPivot.sort(list);
        for (int i = 0; i < list.size(); i++) {
            assertEquals(i + 1, list.get(i));
        }
    }

    // Custom TestObject class for stability testing...

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