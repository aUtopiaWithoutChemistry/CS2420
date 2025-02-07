package test;

import java.util.ArrayList;

public class Test {
    private static void updateArray(int[] arr) {
        arr = new int[arr.length];
        for(int i = 0; i < arr.length; i++)
            arr[i]++;
    }

    public static void main(String[] args) {
        int[] values = { 1, 2, 3, 4, 5 };
        updateArray(values);
        System.out.print(values[4]);
    }
}
