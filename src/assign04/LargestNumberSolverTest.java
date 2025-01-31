package assign04;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class LargestNumberSolverTest {
    private Integer[] testArr1, testArr2, testArrEmpty, testArrSmall, testArrBig;
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
        testArrBig = new Integer[bigSize];
        bigInteger1 = new BigInteger("87431");
        bigInteger2 = new BigInteger("87744321");
        bigIntegerSum1And2 = bigInteger1.add(bigInteger2);
        bigArrItems = new ArrayList<>();
        lists = new ArrayList<>();

        lists.add(testArr1);
        lists.add(testArr2);

        Random rng = new Random();
        for (int i = 0; i < bigSize; i++) {
            bigArrItems.add(rng.nextInt());
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

    }

    @Test
    void testInsertionSortBigArray() {
        LargestNumberSolver.insertionSort(testArrBig, cmp);
        assertArrayEquals(bigArrItems.toArray(), testArrBig);
    }

    @Test
    void testInsertionSortSorted() {

    }

    @Test
    void testInsertionSortReversed() {

    }

    @Test
    void testInsertionSortWithDiffComparator() {

    }

    @Test
    void testInsertionSortWithDiffComparatorSorted() {

    }

    @Test
    void testInsertionSortWithDiffComparatorReversed() {

    }

    @Test
    void testFindLargestNumber() {
        BigInteger newbe = LargestNumberSolver.findLargestNumber(testArr2);
        assertEquals(bigInteger2, newbe);
    }

    @Test
    void testFindLargestInt() {
        assertEquals(87, LargestNumberSolver.findLargestInt(testArr2));
    }

    @Test
    void testFindLargestLong() {
        assertEquals((long)87, LargestNumberSolver.findLargestLong(testArr2));
    }

    @Test
    void testSum() {
        assertEquals(bigIntegerSum1And2,LargestNumberSolver.sum(lists));
    }
    @Test
    void testFindKthLargest(){
        assertEquals(testArr2,LargestNumberSolver.findKthLargest(lists,0));
        assertEquals(testArr1,LargestNumberSolver.findKthLargest(lists,1));
    }

}