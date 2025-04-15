package assign10;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for BinaryMaxHeap
 * Includes tests for different size, edge cases, different data types,
 * customized comparator, and all public methods in BinaryMaxHeap class.
 *
 * @author Zifan Zuo and Xinrui Ou
 * @version 2025-04-10
 */
class BinaryMaxHeapTest {
    private BinaryMaxHeap<Integer> emptyHeap, smallHeap, mediumHeap, largeHeap, emptyMinHeap, largeMinHeap;
    private BinaryMaxHeap<String> stringHeap;
    private final int mediumSize = 15;
    private final int largeSize = 100;

    @BeforeEach
    void setUp() {
        emptyHeap = new BinaryMaxHeap<>();
        emptyMinHeap = new BinaryMaxHeap<>((i1, i2) -> i2 - i1);

        List<Integer> smallList = new ArrayList<>();
        List<Integer> mediumList = new ArrayList<>();
        List<Integer> largeList = new ArrayList<>();
        List<String> stringList = new ArrayList<>();

        smallList.add(5);
        smallList.add(7);
        smallList.add(1);

        for (int i = 1; i <= largeSize; i++) {
            if (i <= mediumSize) {
                mediumList.add(i);
            }
            largeList.add(i);
        }

        stringList.add("queue");
        stringList.add("list");
        stringList.add("stack");
        stringList.add("tree");
        stringList.add("map");
        stringList.add("graph");
        stringList.add("heap");

        smallHeap = new BinaryMaxHeap<>(smallList);
        mediumHeap = new BinaryMaxHeap<>(mediumList);
        largeHeap = new BinaryMaxHeap<>(largeList);
        largeMinHeap = new BinaryMaxHeap<>(largeList, (i1, i2) -> i2 - i1);
        stringHeap = new BinaryMaxHeap<>(stringList);
    }

    @Test
    void testAddEmptyHeap() {
        emptyHeap.add(5);
        assertEquals(5, emptyHeap.peek());
        emptyHeap.add(6);
        assertEquals(6, emptyHeap.peek());
        emptyHeap.add(1);
        assertEquals(6, emptyHeap.peek());
    }

    @Test
    void testAddSmallHeap() {
        System.out.println(Arrays.toString(smallHeap.toArray()));
        smallHeap.add(1);
        assertEquals(7, smallHeap.peek());
        smallHeap.add(5);
        assertEquals(7, smallHeap.peek());
        smallHeap.add(7);
        assertEquals(7, smallHeap.peek());
        smallHeap.add(9);
        assertEquals(9, smallHeap.peek());
        smallHeap.add(11);
        assertEquals(11, smallHeap.peek());
    }

    @Test
    void testAddMediumHeap() {
        assertEquals(15, mediumHeap.peek());
        mediumHeap.add(1);
        assertEquals(15, mediumHeap.peek());
        assertEquals(mediumSize + 1, mediumHeap.size());
        mediumHeap.add(1919810);
        assertEquals(1919810, mediumHeap.peek());
    }

    @Test
    void testAddLargeHeap() {
        largeHeap.add(1);
        assertEquals(largeSize + 1, largeHeap.size());
        assertEquals(100, largeHeap.peek());
        largeHeap.add(114514);
        assertEquals(114514, largeHeap.peek());
    }

    @Test
    void testAddEmptyMinHeap() {
        emptyMinHeap.add(5);
        assertEquals(5, emptyMinHeap.peek());
        emptyMinHeap.add(6);
        assertEquals(5, emptyMinHeap.peek());
        emptyMinHeap.add(1);
        assertEquals(1, emptyMinHeap.peek());
    }

    @Test
    void testAddLargeMinHeap() {
        assertEquals(1, largeMinHeap.peek());
        largeMinHeap.add(101);
        assertEquals(1, largeMinHeap.peek());
        largeMinHeap.add(-114514);
        assertEquals(-114514, largeMinHeap.peek());
    }

    @Test
    void testAddStringHeap() {
        assertEquals("tree", stringHeap.peek());
        stringHeap.add("utah");
        assertEquals("utah", stringHeap.peek());
    }

    @Test
    void testAddDuplicate() {
        emptyHeap.add(100);
        emptyHeap.add(100);
        assertEquals(100, emptyHeap.extractMax());
        assertEquals(100, emptyHeap.extractMax());
    }

    @Test
    void testPeekEmptyHeap() {
        assertThrows(NoSuchElementException.class, () -> emptyHeap.peek());
    }

    @Test
    void testPeekSmallHeap() {
        assertEquals(7, smallHeap.peek());
    }

    @Test
    void testPeekMediumHeap() {
        assertEquals(15, mediumHeap.peek());
    }

    @Test
    void testPeekLargeHeap() {
        assertEquals(100, largeHeap.peek());
    }

    @Test
    void testPeekEmptyMinHeap() {
        assertThrows(NoSuchElementException.class, () -> emptyMinHeap.peek());
    }

    @Test
    void testPeekLargeMinHeap() {
        assertEquals(1, largeMinHeap.peek());
    }

    @Test
    void testPeekStringHeap() {
        assertEquals("tree", stringHeap.peek());
    }

    @Test
    void testExtractMaxEmptyHeap() {
        assertThrows(NoSuchElementException.class, () -> emptyHeap.extractMax());
    }

    @Test
    void testExtractMaxSmallHeap() {
        assertEquals(7, smallHeap.extractMax());
        assertEquals(5, smallHeap.extractMax());
        assertEquals(1, smallHeap.extractMax());
        assertThrows(NoSuchElementException.class, () -> smallHeap.extractMax());
    }

    @Test
    void testExtractMaxMediumHeap() {
        for (int i = 15; i > 0; i--) {
            assertEquals(i, mediumHeap.extractMax());
        }
        assertThrows(NoSuchElementException.class, () -> mediumHeap.extractMax());
    }

    @Test
    void testExtractMaxLargeHeap() {
        for (int i = 100; i > 0; i--) {
            assertEquals(i, largeHeap.extractMax());
        }
        assertThrows(NoSuchElementException.class, () -> largeHeap.extractMax());
    }

    @Test
    void testExtractMaxEmptyMinHeap() {
        assertThrows(NoSuchElementException.class, () -> emptyMinHeap.extractMax());
    }

    @Test
    void testExtractMaxLargeMinHeap() {
        for (int i = 1; i <= 100; i++) {
            assertEquals(i, largeMinHeap.extractMax());
        }
        assertThrows(NoSuchElementException.class, () -> largeMinHeap.extractMax());
    }

    @Test
    void testExtractMaxStringHeap() {
        assertEquals("tree", stringHeap.extractMax());
        assertEquals("stack", stringHeap.extractMax());
        assertEquals("queue", stringHeap.extractMax());
        assertEquals("map", stringHeap.extractMax());
        assertEquals("list", stringHeap.extractMax());
        assertEquals("heap", stringHeap.extractMax());
        assertEquals("graph", stringHeap.extractMax());
        assertThrows(NoSuchElementException.class, () -> stringHeap.extractMax());
    }

    @Test
    void testSizeEmptyHeap() {
        assertEquals(0, emptyHeap.size());
    }

    @Test
    void testSizeSmallHeap() {
        assertEquals(3, smallHeap.size());
    }

    @Test
    void testSizeMediumHeap() {
        assertEquals(15, mediumHeap.size());
    }

    @Test
    void testSizeLargeHeap() {
        assertEquals(100, largeHeap.size());
    }

    @Test
    void testSizeEmptyMinHeap() {
        assertEquals(0, emptyMinHeap.size());
    }

    @Test
    void testSizeLargeMinHeap() {
        assertEquals(100, largeMinHeap.size());
    }

    @Test
    void testSizeStringHeap() {
        assertEquals(7, stringHeap.size());
    }

    @Test
    void testIsEmptyEmptyHeap() {
        assertTrue(emptyHeap.isEmpty());
    }

    @Test
    void testIsEmptySmallHeap() {
        assertFalse(smallHeap.isEmpty());
        for (int i = 0; i < 3; i++) {
            smallHeap.extractMax();
        }
        assertTrue(smallHeap.isEmpty());
    }

    @Test
    void testIsEmptyMediumHeap() {
        assertFalse(mediumHeap.isEmpty());
        for (int i = 0; i < 15; i++) {
            mediumHeap.extractMax();
        }
        assertTrue(mediumHeap.isEmpty());
    }

    @Test
    void testIsEmptyLargeHeap() {
        assertFalse(largeHeap.isEmpty());
        for (int i = 0; i < 100; i++) {
            largeHeap.extractMax();
        }
        assertTrue(largeHeap.isEmpty());
    }

    @Test
    void testIsEmptyEmptyMinHeap() {
        assertTrue(emptyMinHeap.isEmpty());
    }

    @Test
    void testIsEmptyLargeMinHeap() {
        assertFalse(largeMinHeap.isEmpty());
        for (int i = 0; i < 100; i++) {
            largeMinHeap.extractMax();
        }
        assertTrue(largeMinHeap.isEmpty());
    }

    @Test
    void testIsEmptyStringHeap() {
        assertFalse(stringHeap.isEmpty());
        for (int i = 0; i < 7; i++) {
            stringHeap.extractMax();
        }
        assertTrue(stringHeap.isEmpty());
    }

    @Test
    void testClearSmallHeap() {
        assertFalse(smallHeap.isEmpty());
        smallHeap.clear();
        assertTrue(smallHeap.isEmpty());
    }

    @Test
    void testClearMediumHeap() {
        assertFalse(smallHeap.isEmpty());
        smallHeap.clear();
        assertEquals(0, smallHeap.size());
    }

    @Test
    void testClearLargeHeap() {
        assertFalse(largeHeap.isEmpty());
        largeHeap.clear();
        assertTrue(largeHeap.isEmpty());
    }

    @Test
    void testClearLargeMinHeap() {
        assertFalse(largeMinHeap.isEmpty());
        largeMinHeap.clear();
        assertTrue(largeMinHeap.isEmpty());
    }

    @Test
    void testClearStringHeap() {
        assertFalse(stringHeap.isEmpty());
        stringHeap.clear();
        assertTrue(stringHeap.isEmpty());
    }

    @Test
    void testToArrayEmptyHeap() {
        assertArrayEquals(new Object[0], emptyHeap.toArray());
    }

    @Test
    void testToArraySmallHeap() {
        assertArrayEquals(new Object[]{7, 5, 1}, smallHeap.toArray());
    }
}