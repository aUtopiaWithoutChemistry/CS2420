package lab12;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Demonstration of Java's PriorityQueue class.
 * 
 * @author CS 2420 course staff
 * @version 2025-4-4
 */
public class PriorityQueueDemo {

	public static void main(String[] args) {
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>(25, (arg0, arg1) -> arg1.compareTo(arg0));
		pq.add(36);
		pq.add(17);
		pq.add(3);
		pq.add(100);
		pq.add(19);
		pq.add(2);
		pq.add(70);

		System.out.println("Array: " + Arrays.toString(pq.toArray()));

		System.out.println("First item removed: " + pq.remove());
		System.out.println("Second item removed: " + pq.remove());
		System.out.println("Third item removed: " + pq.remove());
		System.out.println("Fourth item removed: " + pq.remove());
		System.out.println("Fifth item removed: " + pq.remove());
		System.out.println("Sixth item removed: " + pq.remove());
		System.out.println("Seventh item removed: " + pq.remove());
	}
}
