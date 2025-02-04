package assign04;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for LargestNumberSolver.
 *
 * @author Zifan Zuo and Xinrui Ou
 * @version 2025-02-04
 */
class LargestNumberSolverTest {
    private Integer[] testArr1, testArr2, testArrEmpty, testArrSmall, testArrBig, testArrSorted;
    private ArrayList<Integer> bigArrItems;
    private BigInteger bigInteger1, bigInteger2, bigIntegerSum1And2;
    private ArrayList<Integer[]> lists;
    private Comparator<Integer> cmp, reverseCmp;
    private final int bigSize = 10000;

    @BeforeEach
    void setup() {
        testArr1 = new Integer[]{4, 1, 8, 3, 7};
        testArr2 = new Integer[]{44, 1, 87, 32, 7};
        testArrEmpty = new Integer[0];
        testArrSmall = new Integer[]{514};
        testArrBig = new Integer[bigSize];
        testArrSorted = new Integer[]{1, 2, 3, 4, 5, 1919810};
        bigInteger1 = new BigInteger("87431");
        bigInteger2 = new BigInteger("87744321");
        bigIntegerSum1And2 = bigInteger1.add(bigInteger2);
        bigArrItems = new ArrayList<>();
        lists = new ArrayList<>();

        lists.add(testArr1);
        lists.add(testArr2);

        Random rng = new Random();
        for (int i = 0; i < bigSize; i++) {
            bigArrItems.add(rng.nextInt(10000));
            testArrBig[i] = bigArrItems.get(i);
        }

        cmp = Comparator.comparingInt(anInt -> anInt);
        reverseCmp = (int1, int2) -> int2 - int1;

        bigArrItems.sort(cmp);
    }

    @Test
    void testInsertionSort() {
        LargestNumberSolver.insertionSort(testArr1, cmp);
        assertArrayEquals(new Integer[]{1, 3, 4, 7, 8}, testArr1);
    }

    @Test
    void testInsertionSortEmptyArray() {
        LargestNumberSolver.insertionSort(testArrEmpty, cmp);
        assertArrayEquals(new Integer[0], testArrEmpty);
    }

    @Test
    void testInsertionSortSmallArray() {
        LargestNumberSolver.insertionSort(testArrSmall, cmp);
        assertArrayEquals(new Integer[]{514}, testArrSmall);
    }

    @Test
    void testInsertionSortBigArray() {
        LargestNumberSolver.insertionSort(testArrBig, cmp);
        assertArrayEquals(bigArrItems.toArray(), testArrBig);
    }

    @Test
    void testInsertionSortSorted() {
        LargestNumberSolver.insertionSort(testArrSorted, cmp);
        assertArrayEquals(new Integer[]{1, 2, 3, 4, 5, 1919810}, testArrSorted);
    }

    @Test
    void testInsertionSortReversed() {
        LargestNumberSolver.insertionSort(testArrSorted, reverseCmp);
        assertArrayEquals(new Integer[]{1919810, 5, 4, 3, 2, 1}, testArrSorted);
    }

    @Test
    void testInsertionSortWithDiffComparator() {
        LargestNumberSolver.insertionSort(testArr1, cmp);
        assertArrayEquals(new Integer[]{1, 3, 4, 7, 8}, testArr1);
        LargestNumberSolver.insertionSort(testArr1, reverseCmp);
        assertArrayEquals(new Integer[]{8, 7, 4, 3, 1}, testArr1);
    }

    @Test
    void testInsertionSortWithDiffComparatorSorted() {
        LargestNumberSolver.insertionSort(testArrSorted, cmp);
        assertArrayEquals(new Integer[]{1, 2, 3, 4, 5, 1919810}, testArrSorted);
        LargestNumberSolver.insertionSort(testArrSorted, reverseCmp);
        assertArrayEquals(new Integer[]{1919810, 5, 4, 3, 2, 1}, testArrSorted);
    }

    @Test
    void testFindLargestNumber() {
        BigInteger newbe = LargestNumberSolver.findLargestNumber(testArr2);
        assertEquals(bigInteger2, newbe);
    }

    @Test
    void testFindLargestNumberEmpty() {
        BigInteger newbe = LargestNumberSolver.findLargestNumber(testArrEmpty);
        assertEquals(new BigInteger("0"), newbe);
    }

    @Test
    void testFindLargestNumberSmall() {
        BigInteger newbe = LargestNumberSolver.findLargestNumber(testArrSmall);
        assertEquals(new BigInteger("514"), newbe);
    }

    @Test
    void testFindLargestNumberOriginalArray() {
        Integer[] newbe = {1, 4, 3, 4, 6, 6};
        Integer[] newbeCopy = Arrays.copyOf(newbe, 6);
        LargestNumberSolver.findLargestNumber(newbeCopy);
        assertArrayEquals(newbe, newbeCopy);
    }

    @Test
    void testFindLargestInt() {
        assertEquals(87744321, LargestNumberSolver.findLargestInt(testArr2));
    }

    @Test
    void testFindLargestIntEmpty() {
        assertEquals(0, LargestNumberSolver.findLargestInt(testArrEmpty));
    }

    @Test
    void testFindLargestIntSmall() {
        assertEquals(514, LargestNumberSolver.findLargestInt(testArrSmall));
    }

    @Test
    void testFindLargestIntOverflow() {
        assertThrows(OutOfRangeException.class, () -> {
            LargestNumberSolver.findLargestInt(testArrBig);
        });
    }

    @Test
    void testFindLargestIntOriginalArray() {
        Integer[] newbe = {1, 4, 3, 4, 6, 6};
        Integer[] newbeCopy = Arrays.copyOf(newbe, 6);
        LargestNumberSolver.findLargestInt(newbeCopy);
        assertArrayEquals(newbe, newbeCopy);
    }

    @Test
    void testFindLargestLong() {
        assertEquals((long) 87744321, LargestNumberSolver.findLargestLong(testArr2));
    }

    @Test
    void testFindLargestLongEmpty() {
        assertEquals(0, LargestNumberSolver.findLargestLong(testArrEmpty));
    }

    @Test
    void testFindLargestLongSmall() {
        assertEquals(514, LargestNumberSolver.findLargestLong(testArrSmall));
    }

    @Test
    void testFindLargestLongOverflow() {
        assertThrows(OutOfRangeException.class, () -> {
            LargestNumberSolver.findLargestLong(testArrBig);
        });
    }

    @Test
    void testFindLargestLongOriginalArray() {
        Integer[] newbe = {1, 4, 3, 4, 6, 6};
        Integer[] newbeCopy = Arrays.copyOf(newbe, 6);
        LargestNumberSolver.findLargestLong(newbeCopy);
        assertArrayEquals(newbe, newbeCopy);
    }

    @Test
    void testSum() {
        assertEquals(bigIntegerSum1And2, LargestNumberSolver.sum(lists));
    }

    @Test
    void testSumEmptyArray() {
        List<Integer[]> list = new ArrayList<>();
        assertEquals(new BigInteger("0"), LargestNumberSolver.sum(list));
    }

    @Test
    void testSumOneArray() {
        List<Integer[]> list = new ArrayList<>();
        list.add(testArr1);
        assertEquals(new BigInteger("87431"), LargestNumberSolver.sum(list));
    }

    @Test
    void testSumOriginalList() {
        List<Integer[]> copyOfOriginalList = List.copyOf(lists);
        LargestNumberSolver.sum(lists);
        for (int i = 0; i < lists.size(); i++) {
            assertArrayEquals(copyOfOriginalList.get(i), lists.get(i));
        }
    }

    @Test
    void testFindKthLargest() {
        assertEquals(testArr2, LargestNumberSolver.findKthLargest(lists, 0));
        assertEquals(testArr1, LargestNumberSolver.findKthLargest(lists, 1));
    }

    @Test
    void testFindKthLargestWithOutRangeNumberK() {
        assertThrows(IllegalArgumentException.class, () -> {
            LargestNumberSolver.findKthLargest(lists, 10);
        });
    }

    @Test
    void testFindKthLargestOriginalList() {
        List<Integer[]> copyOfOriginalList = List.copyOf(lists);
        LargestNumberSolver.findKthLargest(lists, 0);
        for (int i = 0; i < lists.size(); i++) {
            assertArrayEquals(copyOfOriginalList.get(i), lists.get(i));
        }
    }

    @Test
    void testReadFile() {
        List<Integer[]> list = LargestNumberSolver.readFile("/Users/lama/Library/Mobile Documents/com~apple~CloudDocs/MyUofU/2025 Spring/CS2420/CS2420/src/assign04/integers.txt");
        assertEquals(410, list.get(0)[0]);
    }
}