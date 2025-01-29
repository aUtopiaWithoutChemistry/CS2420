package assign03;

import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * This is a generic class that store E type data in a certain order, natural ordering
 * if it doesn't provide a customized Comparator. Using just array, without other data
 * types that Java provides. Supports access of the maximum element only.
 *
 * @author Zifan Zuo and Xinrui Ou
 * @version 2025-01-27
 */
public class SimplePriorityQueue<E> implements PriorityQueue<E> {
    private E[] arr;
    private int count;
    private Comparator cmp = null;

    /**
     * A default constructor, using the natural ordering to order the array
     */
    @SuppressWarnings("unchecked")
    public SimplePriorityQueue() {
        arr = (E[]) new Object[10];
        count = 0;
    }

    /**
     * A customized constructor, by passing a Comparator object to the constructor.
     *
     * @param cmp a customized Comparator
     */
    @SuppressWarnings("unchecked")
    public SimplePriorityQueue(Comparator<? super E> cmp) {
        arr = (E[]) new Object[10];
        count = 0;
        this.cmp = cmp;
    }

    @Override
    public void clear() {
        for (int i = 0; i < count; i++) arr[i] = null;
        count = 0;
    }

    @Override
    public boolean contains(E item) {
        return count != 0 && arr[binarySearch(item)].equals(item);
    }

    @Override
    public boolean containsAll(Collection<? extends E> coll) {
        boolean containsAll = true;
        for (E item : coll) containsAll = containsAll && contains(item);
        return containsAll;
    }

    @Override
    public E deleteMax() throws NoSuchElementException {
        if (count == 0) throw new NoSuchElementException();
        E returnValue = arr[count - 1];
        arr[count - 1] = null;
        count--;
        return returnValue;
    }

    @Override
    public E findMax() throws NoSuchElementException {
        if (count == 0) throw new NoSuchElementException();
        return arr[count - 1];
    }

    @Override
    @SuppressWarnings("unchecked")
    public void insert(E item) {
        // if the array is empty, insert to 0 index
        if (count == 0) {
            arr[0] = item;
            count++;
            return;
        }
        // if the array is full, expand the arr size
        if (count == arr.length) {
            E[] newArr = (E[]) new Object[count * 2];
            for (int i = 0; i < count; i++) newArr[i] = arr[i];
            arr = newArr;
        }
        int targetIndex = binarySearch(item);
        // if item is bigger than target, insert item after target
        if (compare(item, arr[targetIndex]) > 0) {
            for (int i = count - 1; i > targetIndex; i--) arr[i + 1] = arr[i];
            arr[targetIndex + 1] = item;
        }
        // if item is smaller than target, insert item before target
        else {
            for (int i = count - 1; i >= targetIndex; i--) arr[i + 1] = arr[i];
            arr[targetIndex] = item;
        }
        count++;
    }

    @Override
    public void insertAll(Collection<? extends E> coll) {
        for (E item : coll) insert(item);
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public int size() {
        if (count > 0 && arr[count - 1] != null)
            return count;
        return 0;
    }

    /**
     * Binary Search to find target item in the array and return the index of the target.
     *
     * @param item the target item to be searched in the array
     * @return the index of the target
     */
    private int binarySearch(E item) {
        int startIndex = 0;
        int endIndex = count - 1;
        int centreIndex = -1;
        while (startIndex <= endIndex) {
            centreIndex = (startIndex + endIndex) / 2;
            // check if item on the centre index is equals to target item
            if (arr[centreIndex] == null || arr[centreIndex].equals(item)) break;
            // if item is greater than centre item, then search right half
            if (compare(item, arr[centreIndex]) > 0) startIndex = centreIndex + 1;
                // if item is smaller than centre item, then search left half
            else endIndex = centreIndex - 1;
        }
        return centreIndex;
    }

    /**
     * private helper method to unify two type of comparison, with and without Comparator
     *
     * @param item1 the first item
     * @param item2 the second item
     * @return positive if 1 > 2, negative if 1 < 2, 0 if 1 == 2
     */
    @SuppressWarnings("unchecked")
    private int compare(E item1, E item2) {
        // if there is Comparator, using cmp, if not than using compareTo
        if (cmp == null) return ((Comparable<? super E>) item1).compareTo(item2);
        else return cmp.compare(item1, item2);
    }
}
