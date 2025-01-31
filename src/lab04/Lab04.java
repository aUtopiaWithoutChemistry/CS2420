package lab04;

import java.util.Arrays;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Lab04 {
    public static <T extends Comparable<T>> T findMedianComparable(T[] arr) {
        Arrays.sort(arr);
        return arr[arr.length / 2];
    }

    public static <T> T findMedianComparator(T[] arr, Comparator<T> cmp) {
        Arrays.sort(arr, cmp);
        return arr[arr.length / 2];
    }
//    public static <T extends Comparable<T>> void sort(T[] arr) {
//        for (int i = 0; i < arr.length - 1; i++) {
//            for (int j = 0; j < arr.length - i; j++) {
//                T temp = arr[j];
//                if (arr[j].compareTo(arr[j + 1]) > 0) {
//
//                }
//            }
//        }
//    }
    public static void main(String[] arg) {
        assertEquals("bird", findMedianComparable(new String[]{"bird", "cat", "dog", "ant"}));
    }
}
