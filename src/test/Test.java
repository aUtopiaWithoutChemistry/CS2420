package test;

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
        int[] arr = new int[]{-1, 10, -2, 13, -21, 9, -5, 16};
        System.out.println(MCSS(arr));
    }
}
