package assign04;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
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
                arr[j + 1] = arr[j];
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
        Comparator<Integer> cmp = (Integer x1, Integer x2) -> {
            ArrayList<Integer> array1 = new ArrayList<>();
            ArrayList<Integer> array2 = new ArrayList<>();
            // get every digit in x1 and x2
            while (x1 > 0) {
                array1.add(0, x1 % 10);
                x1 /= 10;
            }
            while (x2 > 0) {
                array2.add(0, x2 % 10);
                x2 /= 10;
            }
            while (!array1.isEmpty() && !array2.isEmpty()) {
                // when first index already compare;6,7
                if (array1.get(0) > array2.get(0)) {
                    return -1;
                }
                if (array1.get(0) < array2.get(0)) {
                    return 1;
                }
                if (array1.get(0).equals(array2.get(0)) && array2.size() == 1 && array1.size() == 1) {
                    return 0;
                }
                // when first index doesn't work;666,667
                if (array1.get(0).equals(array2.get(0)) && array1.size() > 1 && array2.size() > 1) {
                    array1.removeFirst();
                    array2.removeFirst();
                    continue;
                }
                // 6, 665
                if (array1.get(0).equals(array2.get(0)) && ((array1.size() > 1 && array2.size() == 1) || (array2.size() > 1 && array1.size() == 1))) {
                    if (array1.size() == 1){
                        array1.add(array1.get(0));
                    }
                    else{
                        array2.add(array2.get(0));
                    }
                    array2.removeFirst();
                    array1.removeFirst();
                }
            }
            return 0;
        };
        Integer[] newArr = new Integer[arr.length];
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        insertionSort(newArr, cmp);
        return new BigInteger(makeNumberString(newArr));
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

    private static String makeNumberString(Integer[] arr) {
        StringBuilder bigNumber = new StringBuilder();
        for (Integer integer : arr) bigNumber.append(integer);
        return bigNumber.toString();
    }
}
