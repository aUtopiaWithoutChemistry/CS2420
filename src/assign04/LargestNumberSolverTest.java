package assign04;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LargestNumberSolverTest {
    private Integer[] testArr1, testArr2;
    private BigInteger bigInteger1, bigInteger2, bigIntegerSum1And2;
    private List<Integer[]> lists;

    @BeforeEach
    void setup() {
        testArr1 = new Integer[]{4, 1, 8, 3, 7};
        testArr2 = new Integer[]{44, 1, 87, 32, 7};
        bigInteger1 = new BigInteger("87431");
        bigInteger2 = new BigInteger("87744321");
        bigIntegerSum1And2 = bigInteger1.add(bigInteger2);

        lists = new ArrayList<>();
        lists.add(testArr1);
        lists.add(testArr2);
    }

    @Test
    void testTest() {
        assertTrue(Arrays.equals(new Integer[]{1, 3, 4, 7, 8}, new Integer[]{1, 3, 4, 7, 8}));
    }
    @Test
    void testInsertionSort() {
        Comparator<Integer> cmp = Comparator.comparingInt(anInt -> anInt);
        LargestNumberSolver.insertionSort(testArr1, cmp);
        System.out.println(Arrays.toString(testArr1));
        assertTrue(Arrays.equals(new Integer[]{1, 3, 4, 7, 8}, testArr1));
    }

    @Test
    void testFindLargestNumber() {
        assertEquals(bigInteger2, LargestNumberSolver.findLargestNumber(testArr2));
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