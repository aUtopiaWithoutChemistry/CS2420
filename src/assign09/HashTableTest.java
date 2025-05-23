package assign09;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for hash table, tests using wrapper class and String class to check
 * the generic part works fine, also checks all the method in HashTable class.
 *
 * @author Zifan Zuo and Xinrui Ou
 * @version 2025-04-01
 */
class HashTableTest {
    private HashTable<Integer, Integer> emptyTable, smallTable, largeTable, customizeTable;
    private HashTable<String, String> genericTable;
    private final int largeSize = 1000;

    @BeforeEach
    void setUp() {
        emptyTable = new HashTable<>();
        smallTable = new HashTable<>();
        largeTable = new HashTable<>();
        customizeTable = new HashTable<>(1.0);
        genericTable = new HashTable<>();

        smallTable.put(1, 4);
        smallTable.put(2, 5);
        smallTable.put(3, 6);

        genericTable.put("one", null);
        genericTable.put("two", "x");
        genericTable.put("three", ".com");

        for (int i = 0; i < largeSize; i++) {
            largeTable.put(i, i + largeSize);
            customizeTable.put(i, i + largeSize);
        }
    }

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
    void testClearOnGenericTable() {
        genericTable.clear();
        assertTrue(genericTable.isEmpty());
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
    void testContainsKeyGeneric() {
        assertTrue(genericTable.containsKey("one"));
        assertTrue(genericTable.containsKey("two"));
        assertTrue(genericTable.containsKey("three"));
        assertFalse(genericTable.containsKey("x"));
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
    void testContainsValueGeneric() {
        assertTrue(genericTable.containsValue("x"));
        assertTrue(genericTable.containsValue(".com"));
        assertFalse(genericTable.containsValue("one"));
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
    void testEntriesGeneric() {
        assertTrue(genericTable.entries().contains(new MapEntry<String, String>("one", null)));
        assertTrue(genericTable.entries().contains(new MapEntry<>("two", "x")));
        assertTrue(genericTable.entries().contains(new MapEntry<>("three", ".com")));
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
    void testGetGeneric() {
        assertNull(genericTable.get("one"));
        assertEquals("x", genericTable.get("two"));
        assertEquals(".com", genericTable.get("three"));
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
    void testPutGeneric() {
        assertNull(genericTable.put("four", "wow"));
        assertNull(genericTable.put("one", "wow"));
        assertEquals("wow", genericTable.put("one", "lol"));
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
    void testRemoveGeneric() {
        assertEquals("x", genericTable.remove("two"));
        assertNull(genericTable.remove("one"));
        assertNull(genericTable.remove("one"));
    }

    @Test
    void testSize() {
        assertEquals(0, emptyTable.size());
        assertEquals(3, smallTable.size());
        assertEquals(largeSize, largeTable.size());
        assertEquals(largeSize, customizeTable.size());
        assertEquals(3, genericTable.size());
    }

    @Test
    void testCollisionEmpty() {
        emptyTable.put(1, 1);
        emptyTable.put(11, 1);
        assertEquals(1, emptyTable.getNumberOfCollisions());
        emptyTable.put(21, 1);
        emptyTable.put(31, 1);
        emptyTable.put(41, 1);
        emptyTable.put(51, 1);
        emptyTable.put(61, 1);
        emptyTable.put(71, 1);
        emptyTable.put(81, 1);
        emptyTable.put(91, 1);
        assertEquals(9, emptyTable.getNumberOfCollisions());
        emptyTable.put(101, 1);
        assertEquals(10, emptyTable.getNumberOfCollisions());
    }

    @Test
    void testCollisionLarge() {
        // 160 cell, 1000 items, 6 items are already in the cell that we put last item
        assertEquals(6, largeTable.getNumberOfCollisions());
        largeTable.put(1120, 19999);
        assertEquals(7, largeTable.getNumberOfCollisions());
        largeTable.put(1280, 1);
        assertEquals(8, largeTable.getNumberOfCollisions());
        largeTable.put(1440, 1);
        largeTable.put(1600, 1);
        largeTable.put(1760, 2);
        assertEquals(11, largeTable.getNumberOfCollisions());
    }

    @Test
    void testCollisionCustomized() {
        assertEquals(0, customizeTable.getNumberOfCollisions());
    }

    @Test
    void testPutWithNullValue() {
        HashTable<Object, Object> table = new HashTable<>();
        table.put(1, null);
        assertEquals(1, table.size());
    }
}