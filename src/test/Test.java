package test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Test {
    private int num;
    public Test() {
        this(10);
    }

    public Test(int i) {
        this.num = i;
    }

    public static void main(String[] args) {
        List<Integer> test = new LinkedList<>();
        System.out.println(test);
    }
}
