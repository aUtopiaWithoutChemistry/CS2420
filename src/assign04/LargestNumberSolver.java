package assign04;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.List;

public class LargestNumberSolver {
    public static <T> void insertionSort(T[] arr, Comparator<? super T> cmp) {
        // loop over every item in the array
        for (int i = 1; i < arr.length; i++) {

            // to find the certain place in the sorted part for current item
            T temp = arr[i];
            int j = i - 1;
            boolean swapped = false;

            // compare every item in sorted area, if bigger than temp then move back one
            while (j >= 0 && cmp.compare(temp, arr[j]) < 0) {
                arr[j + 1] = arr [j];
                swapped = true;
                j--;
            }
            // if j is smaller than 0 or arr[j] is smaller than temp, j is pointing at invalid or
            // wrong place, so need to add 1
            j++;
            if (swapped) arr[j] = temp;
        }
    }
    public static BigInteger findLargestNumber(Integer[] arr) {
        return null;
    }
    public static int findLargestInt(Integer[] arr) {
        return 0;
    }
    public static long findLargestLong(Integer[] arr) {
        return 0;
    }
    public static BigInteger sum(List<Integer[]> list) {
        return null;
    }
    public static Integer[] findKthLargest(List<Integer[]> list, int k) {
        return null;
    }
    public static List<Integer[]> readFile(String filename) {
        return null;
    }

//    private static <T> int compare(T item1, T item2, Comparator<T> cmp) {
//        return cmp.compare(item1, item2);
//    }
}
