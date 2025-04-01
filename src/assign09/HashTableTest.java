package assign09;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class HashTableTest {
    private HashTable<Integer, Integer> emptyTable, smallTable, largeTable, customizeTable;
    private final int largeSize = 1000;

    @BeforeEach
    void setUp() {
        emptyTable = new HashTable<>();
        smallTable = new HashTable<>();
        largeTable = new HashTable<>();
        customizeTable = new HashTable<>(1.0);

        smallTable.put(1, 4);
        smallTable.put(2, 5);
        smallTable.put(3, 6);

        for (int i = 0; i < largeSize; i++) {
            largeTable.put(i, i + largeSize);
            customizeTable.put(i, i + largeSize);
        }
    }

    // --- Basic functionality ---
    @Test
    void testClearBySize() {
        smallTable.clear();
        assertEquals(0, smallTable.size());
    }

    @Test
    void testClearByIsEmpty() {
        largeTable.clear();
        assertTrue(largeTable.isEmpty());
    }

    @Test
    void testClearByContainsKey() {
        smallTable.clear();
        assertFalse(smallTable.containsKey(1));
        assertFalse(smallTable.containsKey(2));
        assertFalse(smallTable.containsKey(3));
    }

    @Test
    void testClearByContainsValue() {
        largeTable.clear();
        for (int i = 0; i < 100; i++) {
            assertFalse(largeTable.containsValue(i));
        }
    }

    @Test
    void testContainsKeyEmpty() {
        assertFalse(emptyTable.containsKey(1));
    }

    @Test
    void testContainsKeySmall() {
        assertTrue(smallTable.containsKey(1));
        assertFalse(smallTable.containsKey(4));
    }

    @Test
    void testContainsKeyLarge() {
        for (int i = 0; i < largeSize; i++) {
            assertTrue(largeTable.containsKey(i));
        }
        assertFalse(largeTable.containsKey(1919810));
    }

    @Test
    void testContainsKeyCustomized() {
        for (int i = 0; i < largeSize; i++) {
            assertTrue(customizeTable.containsKey(i));
        }
        assertFalse(customizeTable.containsKey(114514));
    }

    @Test
    void testContainsValueEmpty() {
        assertFalse(emptyTable.containsValue(1919));
    }

    @Test
    void testContainsValueSmall() {
        assertTrue(smallTable.containsValue(4));
        assertTrue(smallTable.containsValue(5));
        assertTrue(smallTable.containsValue(6));
        assertFalse(smallTable.containsValue(1));
    }

    @Test
    void testContainsValueLarge() {
        for (int i = 0; i < largeSize; i++) {
            assertTrue(largeTable.containsValue(i + largeSize));
        }
        assertFalse(largeTable.containsValue(514));
    }

    @Test
    void testContainsValueCustomized() {
        for (int i = 0; i < largeSize; i++) {
            assertTrue(customizeTable.containsValue(i + largeSize));
        }
        assertFalse(customizeTable.containsValue(114));
    }

    @Test
    void testEntriesEmpty() {
        assertEquals(0, emptyTable.entries().size());
    }

    @Test
    void testEntriesSmall() {
        assertEquals(new MapEntry<>(1, 4), smallTable.entries().get(0));
        assertEquals(new MapEntry<>(2, 5), smallTable.entries().get(1));
        assertEquals(new MapEntry<>(3, 6), smallTable.entries().get(2));
    }

    @Test
    void testEntriesLarge() {
        assertTrue(largeTable.entries().contains(new MapEntry<>(0, 1000)));
        assertTrue(largeTable.entries().contains(new MapEntry<>(999, 1999)));
    }

    @Test
    void testEntriesCustomized() {
        for (int i = 0; i < largeSize; i++){
            assertTrue(customizeTable.entries().contains(new MapEntry<>(i, i + largeSize)));
        }
    }

    @Test
    void testGetEmpty() {
        assertNull(emptyTable.get(0));
    }

    @Test
    void testGetSmall() {
        assertEquals(4, smallTable.get(1));
        assertEquals(5, smallTable.get(2));
        assertEquals(6, smallTable.get(3));
        assertNull(smallTable.get(4));
        assertNull(smallTable.get(1919810));
    }

    @Test
    void testGetLarge() {
        assertEquals(1999, largeTable.get(999));
        assertEquals(1145, largeTable.get(145));
        assertNull(largeTable.get(1001));
        assertNull(largeTable.get(1919810));
    }

    @Test
    void testGetCustomized() {
        assertEquals(1129, customizeTable.get(129));
        assertEquals(1566, customizeTable.get(566));
        assertNull(customizeTable.get(1002));
        assertNull(customizeTable.get(8848));
    }

    @Test
    void testIsEmptyEmpty() {
        assertTrue(emptyTable.isEmpty());
    }

    @Test
    void testIsEmptySmall() {
        assertFalse(smallTable.isEmpty());
        smallTable.remove(1);
        smallTable.remove(2);
        smallTable.remove(3);
        assertTrue(smallTable.isEmpty());
    }

    @Test
    void testIsEmptyLarge() {
        assertFalse(largeTable.isEmpty());
        largeTable.clear();
        assertTrue(largeTable.isEmpty());
    }

    @Test
    void testPutEmpty() {
        assertNull(emptyTable.put(1, 1));
    }

    @Test
    void testPutSmall() {
        assertEquals(4, smallTable.put(1, 5));
    }

    @Test
    void testPutLarge() {
        assertEquals(1000, largeTable.put(0, 1001));
        assertEquals(1001, largeTable.put(0, 1999));
    }

    @Test
    void testPutCustomized() {
        assertEquals(1999, customizeTable.put(999, 2000));
        assertEquals(largeSize, largeTable.size());
    }

    @Test
    void testRemoveEmpty() {
        assertNull(emptyTable.remove(1));
    }

    @Test
    void testRemoveSmall() {
        assertNull(smallTable.remove(4));
        assertEquals(3, smallTable.size());
        assertEquals(4, smallTable.remove(1));
        assertEquals(2, smallTable.size());
        assertEquals(5, smallTable.remove(2));
        assertEquals(6, smallTable.remove(3));
        assertTrue(smallTable.isEmpty());
    }

    @Test
    void testRemoveLarge() {
        assertNull(largeTable.remove(1145));
        for (int i = 0; i < largeSize; i++) {
            assertEquals(i + largeSize, largeTable.remove(i));
        }
        assertTrue(largeTable.isEmpty());
    }

    @Test
    void testRemoveCustomized() {
        assertNull(customizeTable.remove(1919));
        for (int i = 0; i < largeSize; i++) {
            assertEquals(i + largeSize, customizeTable.remove(i));
            assertEquals(largeSize - i - 1, customizeTable.size());
        }
    }

    @Test
    void testSize() {
        assertEquals(0, emptyTable.size());
        assertEquals(3, smallTable.size());
        assertEquals(largeSize, largeTable.size());
        assertEquals(largeSize, customizeTable.size());
    }
}