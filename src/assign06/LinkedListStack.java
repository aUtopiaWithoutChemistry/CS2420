package assign06;

import java.util.NoSuchElementException;

/**
 * An implementation of Stack backed by SinglyLinkedList, provides clear, peek,
 * pop, push, size, and isEmpty methods.
 *
 * @author Zifan Zuo
 * @version 2025-02-25
 */
public class LinkedListStack <T> implements Stack<T>{
    private SinglyLinkedList<T> myList;

    /**
     * constructor of LinkedListStack
     */
    public LinkedListStack() {
        myList = new SinglyLinkedList<>();
    }

    @Override
    public void clear() {
        myList.clear();
    }

    @Override
    public boolean isEmpty() {
        return myList.isEmpty();
    }

    @Override
    public T peek() throws NoSuchElementException {
        if (myList.isEmpty()) throw new NoSuchElementException();
        return myList.getFirst();
    }

    @Override
    public T pop() throws NoSuchElementException {
        if (myList.isEmpty()) throw new NoSuchElementException();
        return myList.deleteFirst();
    }

    @Override
    public void push(T element) {
        myList.insertFirst(element);
    }

    @Override
    public int size() {
        return myList.size();
    }
}
