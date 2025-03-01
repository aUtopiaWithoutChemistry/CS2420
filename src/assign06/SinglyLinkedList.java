package assign06;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An implementation of SinglyLinkedList, provided insert, get, delete, indexOf,
 * size, clear methods, and also provided an Iterator.
 *
 * @author Zifan Zuo
 * @version 2025-02-25
 */
public class SinglyLinkedList <T> implements List<T>{
    // the first Node
    private Node<T> head;
    private int count;

    /**
     * A private node class, which stores the data and a pointer to the next Node.
     */
    private class Node<T> {
        public T data;
        public Node<T> next;
        public Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }
    }

    /**
     * Constructor of SinglyLinkedList
     */
    public SinglyLinkedList() {
        head = null;
        count = 0;
    }

    @Override
    public void insertFirst(T element) {
        insert(0, element);
    }

    @Override
    public void insert(int index, T element) throws IndexOutOfBoundsException {
        if (index > count) throw new IndexOutOfBoundsException();
        if (head == null) head = new Node<>(null, null);
        Node<T> newNode = new Node<>(element, null);
        if (count == 0 && index == 0) {
            // when the list is empty
            head.data = element;
        } else if (index == 0) {
            // when insert to the first place
            newNode.next = head;
            head = newNode;
        } else {
            // when insert to any place in the rest of the list
            Node<T> pointer = goToIndex(index - 1);
            newNode.next = pointer.next;
            pointer.next = newNode;
        }
        count++;
    }

    @Override
    public T getFirst() throws NoSuchElementException {
        if (count == 0) throw new NoSuchElementException();
        return head.data;
    }

    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        if (index >= count) throw new IndexOutOfBoundsException();
        Node<T> pointer = goToIndex(index);
        return pointer.data;
    }

    @Override
    public T deleteFirst() throws NoSuchElementException {
        if (count == 0) throw new NoSuchElementException();
        T temp = head.data;
        head = head.next;
        count--;
        return temp;
    }

    @Override
    public T delete(int index) throws IndexOutOfBoundsException {
        if (index >= count) throw new IndexOutOfBoundsException();
        T temp;
        if (count == 1) {
            temp = head.data;
            head = null;
        } else {
            Node<T> pointer = goToIndex(index - 1);
            temp = pointer.next.data;
            pointer.next = pointer.next.next;
        }
        count--;
        return temp;
    }

    @Override
    public int indexOf(T element) {
        Node<T> cur = head;
        int index = 0;
        while (cur != null && cur.data != null) {
            if (cur.data.equals(element)) return index;
            cur = cur.next;
            index++;
        }
        return -1;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public void clear() {
        count = 0;
        head = null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T[] toArray() {
        T[] returnArray = (T[]) new Object[size()];
        int size = this.size();
        Node<T> pointer = head;
        for (int i = 0; i < size; i++) {
            returnArray[i] = pointer.data;
            pointer = pointer.next;
        }
        return returnArray;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    /**
     * A private helper method to loop over and find the target index,
     * return a Node that pointing to the item located to the target index.
     *
     * @param index the index of target item
     * @return the pointer node
     */
    private Node<T> goToIndex(int index) {
        if (index > count) return null;
        Node<T> pointer = head;
        for (int i = 0; i < index; i++) {
            pointer = pointer.next;
        }
        return pointer;
    }

    /**
     * The iterator for SinglyLinkedList.
     */
    private class LinkedListIterator implements Iterator<T> {
        private Node<T> curPointer = head;
        private Node<T> deletePointer = new Node<>(null, new Node<>(null, head));
        private int deleteIndex = -1;
        private boolean hasCalledNext = false;
        private boolean justCalledRemove = false;

        @Override
        public boolean hasNext() {
            return curPointer != null && curPointer.data != null;
        }

        @Override
        public T next() {
            if (hasNext()) {
                T temp = curPointer.data;
                curPointer = curPointer.next;
                if (!justCalledRemove) {
                    deletePointer = deletePointer.next;
                }
                justCalledRemove = false;
                hasCalledNext = true;
                deleteIndex++;
                return temp;
            } else {
                throw new NoSuchElementException();
            }
        }

        @Override
        public void remove() {
            if (count == 0) throw new NoSuchElementException();
            if (!hasCalledNext) throw new IllegalStateException();
            else if (deleteIndex == 0) {
                head = head.next;
                deletePointer.next = head;
            } else {
                // deletePointer.next is point to the item before the item to be removed
                deletePointer.next = deletePointer.next.next;
            }
            hasCalledNext = false;
            justCalledRemove = true;
            deleteIndex--;
            count--;
        }
    }
}
