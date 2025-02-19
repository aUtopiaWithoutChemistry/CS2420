package test;

import java.util.ArrayList;

public interface LinkedStructure<T> {
    class NodeA<T> {
        public T data;
        public NodeA<T> next;
    }

    class NodeB<T> {
        public T data;
        public ArrayList<NodeB<T>> nexts;
    }

    public T next();

    public void add(T item);

    public void insert(int index, T item);

    public T remove(int index);

    public void remove(T item);
}
