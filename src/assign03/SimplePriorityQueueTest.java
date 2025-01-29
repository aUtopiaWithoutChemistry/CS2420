package assign03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class SimplePriorityQueueTest {

    private SimplePriorityQueue<Integer> emptyQueue, smallQueue, bigQueue, reverseQueue, reverseBigQueue;
    private ArrayList<Integer> testCollectionBig;
    private ArrayList<Integer> testCollectionAdd;
    private final int testSize = 100;

    @BeforeEach
    void setUp() throws Exception {
        Comparator<Integer> reverseCmp = (Integer num1, Integer num2) -> num2 - num1;

        emptyQueue = new SimplePriorityQueue<>();
        smallQueue = new SimplePriorityQueue<>();
        bigQueue = new SimplePriorityQueue<>();
        reverseQueue = new SimplePriorityQueue<>(reverseCmp);
        reverseBigQueue = new SimplePriorityQueue<>(reverseCmp);

        ArrayList<Integer> testCollectionSmall = new ArrayList<>();
        testCollectionBig = new ArrayList<>();
        testCollectionAdd = new ArrayList<>();

        Random rng = new Random();
        for (int i = 0; i < testSize; i++) testCollectionBig.add(rng.nextInt(1000));
        testCollectionBig.sort(Comparator.comparingInt(n -> n));

        testCollectionSmall.add(5);
        testCollectionSmall.add(9);
        testCollectionSmall.add(1);
        testCollectionSmall.add(4);

        testCollectionAdd.add(114);
        testCollectionAdd.add(514);
        testCollectionAdd.add(1919810);

        smallQueue.insertAll(testCollectionSmall);
        reverseQueue.insertAll(testCollectionSmall);
        bigQueue.insertAll(testCollectionBig);
        reverseBigQueue.insertAll(testCollectionBig);
    }

    @Test
    void testGeneric() {
        SimplePriorityQueue<String> stringQueue = new SimplePriorityQueue<>(
                Comparator.comparingInt(String::length)
        );
        stringQueue.insert("woc");
        stringQueue.insert("nitamade");
        stringQueue.insert("2b");
        stringQueue.insert("diulei");
        assertEquals("nitamade", stringQueue.deleteMax());
        assertEquals("diulei", stringQueue.deleteMax());
        assertEquals("woc", stringQueue.deleteMax());
        assertEquals("2b", stringQueue.deleteMax());
    }

    @Test
    void testSize() {
        assertEquals(4, smallQueue.size());
        assertEquals(4, reverseQueue.size());
        assertEquals(testSize, bigQueue.size());
        assertEquals(testSize, reverseBigQueue.size());
    }

    @Test
    void testSizeEmpty() {
        assertEquals(0, emptyQueue.size());
    }

    @Test
    void testSizeBig() {
        int bigNumber = 100;
        for (int i = 0; i < bigNumber; i++) emptyQueue.insertAll(testCollectionBig);
        assertEquals(bigNumber * testSize, emptyQueue.size());
    }

    @Test
    void testDefaultComparator() {
        assertEquals(9, smallQueue.findMax());
        assertEquals(testCollectionBig.get(testSize - 1), bigQueue.findMax());
    }

    @Test
    void testReverseComparator() {
        assertEquals(1, reverseQueue.findMax());
        assertEquals(testCollectionBig.get(0), reverseBigQueue.findMax());
    }

    @Test
    void testClear() {
        smallQueue.clear();
        assertEquals(0, smallQueue.size());
        assertThrows(NoSuchElementException.class, () -> smallQueue.findMax());
        assertTrue(smallQueue.isEmpty());
    }

    @Test
    void testBigClear() {
        bigQueue.clear();
        assertEquals(0, bigQueue.size());
        assertThrows(NoSuchElementException.class, () -> bigQueue.findMax());
        assertTrue(bigQueue.isEmpty());
    }

    @Test
    void testContains() {
        assertFalse(smallQueue.contains(6));
    }

    @Test
    void testContainsTrue() {
        smallQueue.insert(6);
        assertTrue(smallQueue.contains(6));
    }

    @Test
    void testContainsEmpty() {
        assertFalse(emptyQueue.contains(114514));
    }

    @Test
    void testContainsAllFalse() {
        assertFalse(smallQueue.containsAll(testCollectionAdd));
    }

    @Test
    void testContainsAllTrue() {
        smallQueue.insert(1919810);
        smallQueue.insert(114);
        smallQueue.insert(514);
        assertTrue(smallQueue.containsAll(testCollectionAdd));
    }

    @Test
    void testFindMax() {
        assertEquals(9, smallQueue.findMax());
    }

    @Test
    void testSizeAfterFindMax() {
        smallQueue.findMax();
        assertEquals(4, smallQueue.size());
    }

    @Test
    void testFindMaxEmpty() {
        assertThrows(NoSuchElementException.class, () -> emptyQueue.findMax());
    }

    @Test
    void testDeleteMax() {
        assertEquals(9, smallQueue.deleteMax());
        assertEquals(5, smallQueue.deleteMax());
        assertEquals(4, smallQueue.deleteMax());
        assertEquals(1, smallQueue.deleteMax());
        assertThrows(NoSuchElementException.class, () -> smallQueue.deleteMax());
    }

    @Test
    void testSizeAfterDeleteMax() {
        smallQueue.deleteMax();
        assertEquals(3, smallQueue.size());
    }

    @Test
    void testDeleteMaxEmpty() {
        assertThrows(NoSuchElementException.class, () -> emptyQueue.deleteMax());
    }

    @Test
    void testInsert() {
        assertFalse(smallQueue.contains(114));
        smallQueue.insert(114);
        assertTrue(smallQueue.contains(114));
    }

    @Test
    void testInsertToEmptyQueue() {
        assertTrue(emptyQueue.isEmpty());
        emptyQueue.insert(999);
        assertTrue(emptyQueue.contains(999));
    }

    @Test
    void testInsertInOrder() {
        emptyQueue.insert(1);
        emptyQueue.insert(2);
        emptyQueue.insert(3);
        assertEquals(3, emptyQueue.deleteMax());
        assertEquals(2, emptyQueue.deleteMax());
        assertEquals(1, emptyQueue.deleteMax());
    }

    @Test
    void testInsertInReverseOrder() {
        emptyQueue.insert(3);
        emptyQueue.insert(2);
        emptyQueue.insert(1);
        assertEquals(3, emptyQueue.deleteMax());
        assertEquals(2, emptyQueue.deleteMax());
        assertEquals(1, emptyQueue.deleteMax());
    }

    @Test
    void testInsertInRandomOrder() {
        emptyQueue.insert(5);
        emptyQueue.insert(1);
        emptyQueue.insert(9);
        assertEquals(9, emptyQueue.deleteMax());
        assertEquals(5, emptyQueue.deleteMax());
        assertEquals(1, emptyQueue.deleteMax());
    }

    @Test
    void testInsertExceedBoundary() {
        for (int i = 0; i < 11; i++) {
            emptyQueue.insert(i);
        }
        assertEquals(11, emptyQueue.size());
    }

    @Test
    void testIsEmpty() {
        assertTrue(emptyQueue.isEmpty());
    }

    @Test
    void testIsEmptyAfterClear() {
        smallQueue.clear();
        assertTrue(smallQueue.isEmpty());
    }
}
