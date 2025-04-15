package test;

import java.util.*;

public class Test {
    private int num;
    public Test() {
        this(10);
    }

    public Test(int i) {
        this.num = i;
    }

    public static void main(String[] args) {
        Random rng = new Random();
        int[] count = new int[10];
        for (int i = 0; i < 1000; i++) {
            count[rng.nextInt(10)]++;
        }
        System.out.println(Arrays.toString(count));
    }
}
