package lab01;

/**
 * This class contains a utility method for finding the smallest difference.
 * 
 * @author CS 2420 course staff and Zifan Zuo
 * @version January 10, 2025
 */
public class DiffUtil {

	/**
	 * Computes and returns the smallest difference (absolute value of subtraction) 
	 * among every pair of integers in the input array.
	 * @param a - input array of integers
	 * @throws IllegalArgumentException - if the array contains less than two items
	 */
	public static int findSmallestDiff(int[] a) {
		if (a.length < 2)
			throw new IllegalArgumentException("Array must be > 1 element");

		int diff = Math.abs(a[0] - a[1]);

		for (int i = 0; i < a.length; i++)
			for (int j = i + 1; j < a.length; j++) {
				int tmp_diff = Math.abs(a[i] - a[j]);

				if (tmp_diff < diff)
					diff = tmp_diff;
			}

		return diff;
	}
}
