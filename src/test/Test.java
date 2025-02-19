package test;

import java.util.ArrayList;

public class Test {
    public static int MCSS(int[] arr){
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            int sum = 0;
            for (int j = i; j < arr.length; j++) {
                sum += arr[j];
                if (sum > max) {
                    max = sum;
                }
            }
        }
        return max;
    }

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(5);
        list.add(7);
        ArrayList<Integer> array = (ArrayList<Integer>) list.clone();
        System.out.println(array);
        list.remove(0);
        System.out.println(list);
        System.out.println(array);
    }
}
