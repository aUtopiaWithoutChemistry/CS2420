package assign10;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * This class contains generic static methods for sorting a descending delta-sorted list.
 * The result of both methods is that the list will be in descending order.
 * 
 * @author CS 2420 course staff and Zifan Zuo and Xinrui Ou.
 * @version 2025-04-10
 */
public class DeltaSorter {
	
	/**
	 * Fully sorts a descending, delta-sorted list using a BinaryMaxHeap.
	 * After completing, the provided list will contain the same items in descending order.
	 * This version uses the natural ordering of the elements.
	 * 
	 * @param list to sort that is currently delta-sorted and will be fully sorted
	 * @param delta value of the delta-sorted list
	 * @throws IllegalArgumentException if delta is less than 0 or greater than or
	 *         equal to the size of the list
	 */
	public static <T extends Comparable<? super T>> void sort(List<T> list, int delta){
		sort(list, delta, Comparator.naturalOrder());
	}
	
	/**
	 * Fully sorts a descending, delta-sorted list using a BinaryMaxHeap.
	 * After completing, the provided list will contain the same items in descending order.
	 * This version uses a provided comparator to order the elements.
	 * 
	 * @param list to sort that is currently delta-sorted and will be fully sorted
	 * @param delta value of the delta-sorted list
	 * @param cmp Comparator for ordering the elements
	 * @throws IllegalArgumentException if delta is less than 0 or greater than or
	 *         equal to the size of the list
	 */
	public static <T> void sort(List<T> list, int delta, Comparator<? super T> cmp){
		if (delta < 0 || delta >= list.size()) {
			throw new IllegalArgumentException("invalid input of delta");
		}
		int count = 0;
		List<T> starterList = new ArrayList<>();
		for (int i = 0; i < delta + 1; i++) {
			starterList.add(list.get(i));
		}

		BinaryMaxHeap<T> heap = new BinaryMaxHeap<>(starterList, cmp);

		for (int i = delta + 1; i < list.size(); i++) {
			heap.add(list.get(i));
			list.set(count, heap.extractMax());
			count++;
		}

		for (int i = 0; i < delta + 1; i++) {
			list.set(count, heap.extractMax());
			count++;
		}
	}

	/**
	 * The insertion sort from previous assignment, add it to here only for doing timing
	 * experiment.
	 *
	 * @param list to sort that is currently delta-sorted and will be fully sorted
	 * @param cmp Comparator for ordering the elements
	 */
	public static <T> void insertionSort(List<T> list, Comparator<? super T> cmp) {
		// loop over every item in the array
		for (int i = 1; i < list.size(); i++) {
			// to find the certain place in the sorted part for current item
			T temp = list.get(i);
			int j = i - 1;
			boolean swapped = false;
			// compare every item in sorted area, if bigger than temp then move back one
			while (j >= 0 && (cmp.compare(temp, list.get(j))) < 0) {
				list.set(j + 1, list.get(j));
				swapped = true;
				j--;
			}
			// if j is smaller than 0 or arr[j] is smaller than temp, j is pointing at invalid or
			// wrong place, so need to add 1
			j++;
			if (swapped) list.set(j, temp);
		}
	}


}