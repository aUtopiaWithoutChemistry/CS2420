package assign05;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MergeSorterTester {
    private ArrayList<Integer> array, sortedArray, arraySmall;

    @BeforeEach
    void setUp() {
        Random rng = new Random();
        array = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            array.add(rng.nextInt(100));
        }
        sortedArray = (ArrayList<Integer>) array.clone();
        sortedArray.sort(Comparator.comparingInt(i -> i));
        arraySmall = new ArrayList<>();
        arraySmall.add(3);
        arraySmall.add(8);
        arraySmall.add(1);
        arraySmall.add(5);
        arraySmall.add(4);
        arraySmall.add(6);
    }

    @Test
    void testInsertionSort() {
        MergeSorter sorter = new MergeSorter<>(1000);
        sorter.sort(array);
        for (int i = 0; i < array.size(); i++) {
            assertEquals(sortedArray.get(i), array.get(i));
        }
    }

    @Test
    void testMergeSortSmall() {
        MergeSorter sorter = new MergeSorter<>(3);
        sorter.sort(arraySmall);
        assertEquals(1, arraySmall.get(0));
        assertEquals(3, arraySmall.get(1));
        assertEquals(4, arraySmall.get(2));
        assertEquals(5, arraySmall.get(3));
        assertEquals(6, arraySmall.get(4));
        assertEquals(8, arraySmall.get(5));
    }

    @Test
    void testMergeSort() {
        MergeSorter sorter = new MergeSorter<>(99);
        sorter.sort(array);
        System.out.println(array);
        System.out.println(array.size());
        for (int i = 0; i < array.size(); i++) {
            assertEquals(sortedArray.get(i), array.get(i));
        }
    }
}