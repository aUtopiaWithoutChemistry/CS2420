package assign10;

import org.junit.jupiter.api.Test;
import timing.ArrayListGenerator;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for DeltaSorter
 * Includes tests for various delta values, edge cases, and different data types
 *
 * @author Zifan Zuo and Xinrui Ou
 * @version 2025-04-10
 */
public class DeltaSorterTest {

    @Test
    void testDeltaZero() {
        List<Integer> list = new ArrayList<>(List.of(10, 9, 8, 7, 6, 5, 4, 3, 2, 1));
        List<Integer> expected = new ArrayList<>(list);

        DeltaSorter.sort(list, 0);

        assertArrayEquals(expected.toArray(), list.toArray());
    }

    @Test
    void testMaximumDelta() {
        List<Integer> list = new ArrayList<>(List.of(10, 5, 8, 3, 7, 2, 9, 4, 6, 1));
        List<Integer> expected = new ArrayList<>(list);
        Collections.sort(expected, (i1, i2) -> i2 - i1); // Sort in descending order

        DeltaSorter.sort(list, list.size() - 1);

        assertArrayEquals(expected.toArray(), list.toArray());
    }

    @Test
    void testInvalidDelta() {
        List<Integer> list = new ArrayList<>(List.of(10, 9, 8, 7, 6));

        // Test delta < 0
        assertThrows(IllegalArgumentException.class, () -> DeltaSorter.sort(list, -1));

        // Test delta >= list.size()
        assertThrows(IllegalArgumentException.class, () -> DeltaSorter.sort(list, list.size()));
        assertThrows(IllegalArgumentException.class, () -> DeltaSorter.sort(list, list.size() + 5));
    }

    @Test
    void testEmptyList() {
        List<Integer> list = new ArrayList<>();

        // Any delta except 0 should throw an exception for empty lists
        assertThrows(IllegalArgumentException.class, () -> DeltaSorter.sort(list, 1));

        // Delta = 0 is invalid for empty list
        assertThrows(IllegalArgumentException.class, () -> DeltaSorter.sort(list, 0));
    }

    @Test
    void testSingleElementList() {
        List<Integer> list = new ArrayList<>(List.of(42));
        List<Integer> expected = new ArrayList<>(list);

        // Only delta = 0 is valid for single element list
        DeltaSorter.sort(list, 0);
        assertArrayEquals(expected.toArray(), list.toArray());

        // Other delta values should throw exception
        assertThrows(IllegalArgumentException.class, () -> DeltaSorter.sort(list, 1));
    }

    @Test
    void testDuplicateValues() {
        List<Integer> list = new ArrayList<>(List.of(10, 8, 8, 7, 5, 5, 5, 3, 3, 1));
        List<Integer> expected = new ArrayList<>(list);
        Collections.sort(expected, (i1, i2) -> i2 - i1); // Sort in descending order

        DeltaSorter.sort(list, 3);

        assertArrayEquals(expected.toArray(), list.toArray());
    }

    @Test
    void testVariousDeltaValues() {
        // Original list structure is the same but with different delta values
        for (int delta = 0; delta < 10; delta++) {
            List<Integer> list = ArrayListGenerator.generateDescendingDeltaSortedArrayList(100, delta);
            List<Integer> expected = new ArrayList<>(list);
            Collections.sort(expected, (i1, i2) -> i2 - i1); // Sort in descending order

            DeltaSorter.sort(list, delta);

            assertArrayEquals(expected.toArray(), list.toArray(), "Failed with delta = " + delta);
        }
    }

    @Test
    void testCustomComparator() {
        List<Integer> list = ArrayListGenerator.generateDescendingDeltaSortedArrayList(50, 5);
        List<Integer> expected = new ArrayList<>(list);
        Collections.sort(expected, (i1, i2) -> i2 - i1); // Sort in ascending order

        // Use comparator that sorts in ascending order (opposite of default)
        DeltaSorter.sort(list, 5, Comparator.naturalOrder());

        assertArrayEquals(expected.toArray(), list.toArray());
    }

    @Test
    void testStringSort() {
        List<String> list = new ArrayList<>(List.of(
                "zebra", "monkey", "tiger", "elephant", "lion",
                "giraffe", "snake", "bear", "penguin", "kangaroo"));

        // Create a delta-sorted version (descending)
        List<String> deltaSorted = new ArrayList<>(list);
        Collections.sort(deltaSorted, (s1, s2) -> s2.compareTo(s1)); // Sort descending

        // Create sub-disorder with delta = 2
        int delta = 2;
        for(int start = 0; start < deltaSorted.size(); start += delta + 1) {
            int end = Math.min(start + delta + 1, deltaSorted.size());
            // Shuffle elements within this subrange
            for(int i = start; i < end - 1; i++) {
                if (i + 1 < end) {
                    // Swap adjacent elements
                    String temp = deltaSorted.get(i);
                    deltaSorted.set(i, deltaSorted.get(i + 1));
                    deltaSorted.set(i + 1, temp);
                }
            }
        }

        List<String> expected = new ArrayList<>(deltaSorted);
        Collections.sort(expected, (s1, s2) -> s2.compareTo(s1)); // Sort descending

        DeltaSorter.sort(deltaSorted, delta);

        assertArrayEquals(expected.toArray(), deltaSorted.toArray());
    }

    @Test
    void testCustomObjectSort() {
        class Person implements Comparable<Person> {
            private final String name;
            private final int age;

            public Person(String name, int age) {
                this.name = name;
                this.age = age;
            }

            @Override
            public int compareTo(Person other) {
                return this.age - other.age;
            }

            @Override
            public String toString() {
                return name + "(" + age + ")";
            }
        }

        List<Person> list = new ArrayList<>();
        list.add(new Person("Alice", 30));
        list.add(new Person("Bob", 25));
        list.add(new Person("Charlie", 40));
        list.add(new Person("Diana", 35));
        list.add(new Person("Eve", 28));
        list.add(new Person("Frank", 45));

        // Create expected result (descending by age)
        List<Person> expected = new ArrayList<>(list);
        Collections.sort(expected, Comparator.comparingInt(p -> p.age));

        // Sort using DeltaSorter
        DeltaSorter.sort(list, 2, (p1, p2) -> p2.age - p1.age);

        assertArrayEquals(expected.toArray(), list.toArray());
    }

    @Test
    void testLargeList() {
        int size = 10000;
        int delta = 50;
        List<Integer> list = ArrayListGenerator.generateDescendingDeltaSortedArrayList(size, delta);
        List<Integer> expected = new ArrayList<>(list);
        Collections.sort(expected, (i1, i2) -> i2 - i1); // Sort in descending order

        DeltaSorter.sort(list, delta);

        assertArrayEquals(expected.toArray(), list.toArray());
    }

    @Test
    void testDifferentDataPatterns() {
        // Test with all identical elements
        List<Integer> allSame = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            allSame.add(5);
        }

        List<Integer> expectedAllSame = new ArrayList<>(allSame);
        DeltaSorter.sort(allSame, 3);
        assertArrayEquals(expectedAllSame.toArray(), allSame.toArray());

        // Test with already sorted list
        List<Integer> alreadySorted = new ArrayList<>();
        for (int i = 20; i >= 1; i--) {
            alreadySorted.add(i);
        }

        List<Integer> expectedAlreadySorted = new ArrayList<>(alreadySorted);
        DeltaSorter.sort(alreadySorted, 4);
        assertArrayEquals(expectedAlreadySorted.toArray(), alreadySorted.toArray());
    }
}