package assign10;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Using the idea of Binary Max Heap to implements the Priority Queue,
 * which ensure the maximum value in this heap according to the comparator
 * or its default comparable method can be accessed with constant time.
 * This class provided four different constructor for customize comparator
 * or build heap by adding an entire list.
 *
 * @author Zifan Zuo and Xinrui Ou
 * @version 2025-04-10
 *
 * @param <E> the placeholder for generic type of each element in this heap
 */
public class BinaryMaxHeap<E> implements PriorityQueue<E> {
    private E[] arr;
    private int count;
    private Comparator<? super E> cmp;

    /**
     * default constructor, create an empty heap, and use the natural ordering.
     */
    public BinaryMaxHeap() {
        this(new ArrayList<>(), (o1, o2) -> ((Comparable<? super E>)o1).compareTo(o2));
    }

    /**
     * create an empty heap with a customized comparator.
     *
     * @param cmp the customized comparator.
     */
    public BinaryMaxHeap(Comparator<? super E> cmp) {
        this(new ArrayList<>(), cmp);
    }

    /**
     * create a heap with given list using the natural ordering.
     *
     * @param list given list, contains all elements we need to input to this heap.
     */
    public BinaryMaxHeap(List<? extends E> list) {
        this(list, (o1, o2) -> ((Comparable<? super E>)o1).compareTo(o2));
    }

    /**
     * create a heap with given list using the customized comparator.
     *
     * @param list given list, contains all elements we need to input to this heap.
     * @param cmp the customized comparator.
     */
    public BinaryMaxHeap(List<? extends E> list, Comparator<? super E> cmp) {
        this.cmp = cmp;
        count = 1;
        arr = (E[]) new Object[8];
        buildHeap(list);
    }

    /**
     * Adds the given item to this heap.
     *
     * @param item the new item to be added into the heap
     */
    @Override
    public void add(E item) {
        if (count == arr.length) {
            doubleSize();
        }
        arr[count] = item;
        percolateUp(count);
        count++;
    }

    /**
     * Returns, but does not remove, the maximum item in this heap.
     *
     * @return the maximum item in this heap.
     * @throws NoSuchElementException if this heap is empty
     */
    @Override
    public E peek() throws NoSuchElementException {
        if (count == 1) {
            throw new NoSuchElementException();
        }
        return arr[1];
    }

    /**
     * Returns and removes the maximum item in this heap.
     *
     * @return the maximum item
     * @throws NoSuchElementException if this heap is empty
     */
    @Override
    public E extractMax() throws NoSuchElementException {
        if (count == 1) {
            throw new NoSuchElementException();
        }
        E value = arr[1];
        count--;
        swap(1, count);
        arr[count] = null;
        percolateDown(1);
        return value;
    }

    /**
     * Returns the number of items in this heap.
     *
     * @return number of items in this heap.
     */
    @Override
    public int size() {
        return count - 1;
    }

    /**
     * Determine if this heap is empty.
     *
     * @return true if this heap is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return count == 1;
    }

    /**
     * Empty all the item in this heap.
     */
    @Override
    public void clear() {
        count = 1;
        arr = (E[]) new Object[8];
    }

    /**
     * Creates and returns an array of the items in this heap,
     * in the same order they appear in the backing array.
     * remove all the nulls, only contains meaningful data.
     *
     * @return an array of all the meaningful data in this heap.
     */
    @Override
    public Object[] toArray() {
        E[] returnArr = (E[]) new Object[count - 1];
        for (int i = 0; i < returnArr.length; i++) {
            returnArr[i] = arr[i + 1];
        }
        return returnArr;
    }

    /**
     * Private helper method to deal with adding a whole list in to the heap at
     * the same time. Using buildHeap method can do less percolateDown operation
     * than add every item one by another.
     *
     * @param list the input list, containing all item to be added into this heap.
     */
    private void buildHeap(List<? extends E> list) {
        if (list.isEmpty()) {
            return;
        }
        for (E item : list) {
            if (count == arr.length) {
                doubleSize();
            }
            arr[count] = item;
            count++;
        }
        for (int i = arr.length / 2 - 1; i > 0; i--) {
            percolateDown(i);
        }
    }

    /**
     * Private helper method to deal with newly added item in the heap, percolate
     * it up to the correct position.
     *
     * @param curIndex the index of current item
     */
    private void percolateUp(int curIndex) {
        while (parent(curIndex)!= 0 && innerCompare(curIndex, parent(curIndex)) > 0) {
            swap(curIndex, parent(curIndex));
            curIndex = parent(curIndex);
        }
    }

    /**
     * Private helper method to deal with deleting the maximum item in the heap,
     * after swap the max item with the last item, we need to percolate the last item
     * from top of the heap to its correct position.
     *
     * @param index the index of item, who needs to percolate down.
     */
    private void percolateDown(int index) {
        // check child exist
        while (arr.length > (index * 2 + 1)) {
            // check which child is bigger
            int left = leftChild(index);
            int right = rightChild(index);
            int biggerChildIndex;
            // both exist
            if (arr[left] != null && arr[right] != null) {
                biggerChildIndex = innerCompare(left, right) > 0 ? left : right;
            }
            // both non exist
            else if (arr[left] == null && arr[right] == null) {
                return;
            }
            else {
                biggerChildIndex = arr[left] == null ? right : left;
            }
            // if this item is smaller than biggerChild, then swap
            if (innerCompare(index, biggerChildIndex) < 0) {
                swap(index, biggerChildIndex);
                index = biggerChildIndex;
            }
            else {
                break;
            }
        }

    }

    /**
     * Private helper method to do compare between two item in the heap,
     * dealing the situation with or without a customized comparator.
     *
     * @param index1 the index of first item.
     * @param index2 the index of second item.
     * @return >0 if first item is greater than second item, <0 otherwise, 0 if there is a tie.
     */
    private int innerCompare(int index1, int index2) {
        if (cmp != null) {
            return cmp.compare(arr[index1], arr[index2]);
        }
        return ((Comparable<? super E>)arr[index1]).compareTo(arr[index2]);
    }

    /**
     * Private helper method to double the size of this heap when reaching
     * the maximum capacity.
     */
    private void doubleSize() {
        Object[] temp = new Object[arr.length * 2];
        for (int i = 0; i < arr.length; i++) {
            temp[i] = arr[i];
        }
        arr = (E[]) temp;
    }

    /**
     * Private helper method to find the left child of given item.
     *
     * @param curIndex index of current item.
     * @return index of current item's left child.
     */
    private int leftChild(int curIndex) {
        return curIndex * 2;
    }

    /**
     * Private helper method to find the right child of given item.
     *
     * @param curIndex index of current item.
     * @return index of current item's right child.
     */
    private int rightChild(int curIndex) {
        return curIndex * 2 + 1;
    }

    /**
     * Private helper method to find the parent of given item.
     *
     * @param curIndex index of current item.
     * @return index of current item's parent.
     */
    private int parent(int curIndex) {
        return curIndex / 2;
    }

    /**
     * Private helper method to swap two item in the heap.
     *
     * @param index1 the index of first item.
     * @param index2 the index of second item.
     */
    private void swap(int index1, int index2) {
        E temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }
}
