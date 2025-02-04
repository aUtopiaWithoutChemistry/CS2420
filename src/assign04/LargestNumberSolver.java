package assign04;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.*;

/**
 * This class provides some static methods dealing with arrays of Integer, to
 * find the largest number it can form and other operations related.
 *
 * @author Zifan Zuo and Xinrui Ou
 * @version 2025-02-04
 */
public class LargestNumberSolver {
    /**
     * insertion sorting by a given comparator, this method will change
     * the original array.
     *
     * @param arr array to be sorted
     * @param cmp a customized comparator
     */
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

    /**
     * to find the largest number that given array can form, and return that number
     * in BigInteger format.
     *
     * @param arr an array contains Integer
     * @return the largest BigInteger that numbers in given array can form
     */
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
                // when the number at the first index is different
                if (array1.get(0) > array2.get(0)) return -1;
                else if (array1.get(0) < array2.get(0)) return 1;
                    // when the number at the first index is same and both number have only one digit
                else if (array1.get(0).equals(array2.get(0)) && array2.size() == 1 && array1.size() == 1) return 0;
                    // when the number at the first index is tie and at least one number have more
                    // than one digit.
                else {
                    if (array1.size() == 1) array1.add(array1.get(0));
                    else if (array2.size() == 1) array2.add(array2.get(0));
                    else {
                        array1.removeFirst();
                        array2.removeFirst();
                    }
                }
            }
            return 0;
        };
        Integer[] newArr = new Integer[arr.length];
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        insertionSort(newArr, cmp);
        return new BigInteger(makeNumberString(newArr));
    }

    /**
     * to find the largest number that given array can form, and turn it into integer,
     * if this BigInteger is bigger than the max value of integer type can carry, it
     * will throw an OutOfRangeException.
     *
     * @param arr an array contains Integer
     * @return an integer version of largest number can be form by the given array.
     * @throws OutOfRangeException if the number of BigInteger larger than the max value
     *                             of integer primitive type.
     */
    public static int findLargestInt(Integer[] arr) {
        BigInteger target = LargestNumberSolver.findLargestNumber(arr);
        if (target.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) > 0) throw new OutOfRangeException("int");
        return target.intValue();
    }

    /**
     * to find the largest number that given array can form, and turn it into long integer,
     * if this BigInteger is bigger than the max value of long integer type can carry, it
     * will throw an OutOfRangeException.
     *
     * @param arr an array contains Integer
     * @return an long integer version of largest number can be form by the given array.
     * @throws OutOfRangeException if the number of BigInteger larger than the max value
     *                             of long integer primitive type.
     */
    public static long findLargestLong(Integer[] arr) {
        BigInteger target = LargestNumberSolver.findLargestNumber(arr);
        if (target.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0) throw new OutOfRangeException("Long");
        return target.longValue();
    }

    /**
     * To sum up the BigIntegers of given list of Integer arrays and return the result
     * in form of BigInteger
     *
     * @param list a list of Integer arrays
     * @return the sum of BigIntegers made by given Integer arrays
     */
    public static BigInteger sum(List<Integer[]> list) {
        BigInteger sum = new BigInteger("0");
        for (Integer[] integerArr : list) sum = sum.add(LargestNumberSolver.findLargestNumber(integerArr));
        return sum;
    }

    /**
     * To find the kth largest BigInteger that Integer arrays, in given List, can form, and
     * return the original Integer array of the kth largest BigInteger.
     *
     * @param list a list of Integer arrays
     * @param k    the kth largest item
     * @return the Integer array that can form the kth largest number.
     * @throws IllegalArgumentException if the input index is larger than the given List
     */
    public static Integer[] findKthLargest(List<Integer[]> list, int k) {
        if (k > list.size() - 1) throw new IllegalArgumentException();
        // find BigInteger for each Integer array and
        // make a 2d array of BigInteger , [BigInteger, index in the original list]
        BigInteger[][] arr = new BigInteger[list.size()][2];
        for (int i = 0; i < list.size(); i++) {
            arr[i][0] = LargestNumberSolver.findLargestNumber(list.get(i));
            arr[i][1] = new BigInteger(String.valueOf(i));
        }
        // sort the 2d array by the biginteger stored in it's first place
        LargestNumberSolver.insertionSort(arr, Comparator.comparing(item -> item[0]));
        // find the array.length - k's place, and get the index of it's integer list
        // return the integer array at that list
        return list.get(arr[arr.length - 1 - k][1].intValue());
    }

    /**
     * To read a file that contains lines of number seperated by spaces,
     * put those numbers into Integer arrays, and store in a List.
     *
     * @param filename the path of the file
     * @return a List of Integer array
     */
    public static List<Integer[]> readFile(String filename) {
        File file = new File(filename);
        List<Integer[]> list = new ArrayList<>();
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (scanner.hasNextLine()) {
            String string = scanner.next();
            String[] stringArray = string.split(" ");
            Integer[] integers = new Integer[stringArray.length];
            for (int i = 0; i < stringArray.length; i++) {
                integers[i] = Integer.parseInt(stringArray[i]);
            }
            list.add(integers);
        }
        return list;
    }

    /**
     * a helper method to convert an array of integers to a number String
     *
     * @param arr an array of integer
     * @return a String that contains of number
     */
    private static String makeNumberString(Integer[] arr) {
        StringBuilder bigNumber = new StringBuilder();
        for (Integer integer : arr) bigNumber.append(integer);
        return bigNumber.toString();
    }
}
