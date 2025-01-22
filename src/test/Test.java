package test;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(1);
        arr.add(arr.size(), 4);
        System.out.println(arr);
    }
}
