package assign10;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class BinaryMaxHeap<E> implements PriorityQueue<E> {
    private E[] arr;
    private int count;
    private Comparator<? super E> cmp;

    public BinaryMaxHeap() {
        this(new ArrayList<>(), (o1, o2) -> ((Comparable<? super E>)o1).compareTo(o2));
    }

    public BinaryMaxHeap(Comparator<? super E> cmp) {
        this(new ArrayList<>(), cmp);
    }

    public BinaryMaxHeap(List<? extends E> list) {
        this(list, (o1, o2) -> ((Comparable<? super E>)o1).compareTo(o2));
    }

    public BinaryMaxHeap(List<? extends E> list, Comparator<? super E> cmp) {
        this.cmp = cmp;
        count = 1;
        arr = (E[]) new Object[8];
        buildHeap(list);
    }

    @Override
    public void add(E item) {
        if (count == arr.length) {
            doubleSize();
        }
        arr[count] = item;
        percolateUp(count);
        count++;
    }

    @Override
    public E peek() throws NoSuchElementException {
        if (count == 1) {
            throw new NoSuchElementException();
        }
        return arr[1];
    }

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

    @Override
    public int size() {
        return count - 1;
    }

    @Override
    public boolean isEmpty() {
        return count == 1;
    }

    @Override
    public void clear() {
        count = 1;
        arr = (E[]) new Object[8];
    }

    @Override
    public Object[] toArray() {
        return arr;
    }

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

    private void percolateUp(int curIndex) {
        while (parent(curIndex)!= 0 && innerCompare(curIndex, parent(curIndex)) > 0) {
            swap(curIndex, parent(curIndex));
            curIndex = parent(curIndex);
        }
    }

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

    private int innerCompare(int index1, int index2) {
        if (cmp != null) {
            return cmp.compare(arr[index1], arr[index2]);
        }
        return ((Comparable<? super E>)arr[index1]).compareTo(arr[index2]);
    }

    private void doubleSize() {
        Object[] temp = new Object[arr.length * 2];
        for (int i = 0; i < arr.length; i++) {
            temp[i] = arr[i];
        }
        arr = (E[]) temp;
    }

    private int leftChild(int curIndex) {
        return curIndex * 2;
    }

    private int rightChild(int curIndex) {
        return curIndex * 2 + 1;
    }

    private int parent(int curIndex) {
        return curIndex / 2;
    }

    private void swap(int index1, int index2) {
        E temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }
}
