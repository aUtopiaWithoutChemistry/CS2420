package assign03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class SimplePriorityQueueTest {

    private SimplePriorityQueue<Integer> emptyQueue, smallQueue, bigQueue;
    private int testSize = 1000;
    @BeforeEach
    void setUp() throws Exception{
        emptyQueue = new SimplePriorityQueue<>();
        smallQueue = new SimplePriorityQueue<>();
        bigQueue = new SimplePriorityQueue<>();

        ArrayList<Integer> testCollectionSmall = new ArrayList<>();
        ArrayList<Integer> testCollectionBig = new ArrayList<>();

        Random rng = new Random();
        for (int i = 0; i < testSize; i++) testCollectionBig.add(rng.nextInt(1000));

        testCollectionSmall.add(5);
        testCollectionSmall.add(9);
        testCollectionSmall.add(1);
        testCollectionSmall.add(4);

        smallQueue.insertAll(testCollectionSmall);
        bigQueue.insertAll(testCollectionBig);
    }

    @Test
    void testSize() {
        assertEquals(0, emptyQueue.size());
        assertEquals(4, smallQueue.size());
        assertEquals(testSize, bigQueue.size());
    }
}