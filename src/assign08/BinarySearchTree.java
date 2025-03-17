package assign08;

import java.util.*;

public class BinarySearchTree<Type extends Comparable<? super Type>> implements SortedSet<Type> {
    private Node<Type> root;
    private int size;

    private static class Node<Type> {
        public Type data;
        public Node<Type> leftChild, rightChild, parent;

        public Node(Type data) {
            this.data = data;
            this.leftChild = null;
            this.rightChild = null;
        }

        public Node(Type data, Node<Type> parent) {
            this.data = data;
            this.parent = parent;
            this.leftChild = null;
            this.rightChild = null;
        }

        public Node(Type data, Node<Type> leftChild, Node<Type> rightChild, Node<Type> parent) {
            this.data = data;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
            this.parent = parent;
        }

        public String toString() {
            StringBuilder s = new StringBuilder();
            if (leftChild != null) s.append("  ").append(data).append(" -> ").append(leftChild.data).append(";\n");
            if (rightChild!= null) s.append("  ").append(data).append(" -> ").append(rightChild.data).append(";\n");
            return s.toString();
        }
    }

    public BinarySearchTree() {
        this.root = null;
        this.size = 0;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("digraph BST {\n");
        Queue<Node<Type>> allNodes = new LinkedList<>();
        allNodes.offer(root);
        while (!allNodes.isEmpty()) {
            Node<Type> cur = allNodes.poll();
            s.append(cur.toString());
            if (cur.leftChild != null) {
                allNodes.offer(cur.leftChild);
            }
            if (cur.rightChild != null) {
                allNodes.offer(cur.rightChild);
            }
        }
        s.append("}");
        return s.toString();
    }

    @Override
    public boolean add(Type item) {
        // if the tree is empty
        if (size == 0) {
            root = new Node<>(item);
            size++;
            return true;
        }
        Node<Type> cur = root;
        int itemIsLarger = item.compareTo(cur.data);
        // while new item is not equals to the data in current node
        while (itemIsLarger != 0){
            // if the item is greater than current node
            if (itemIsLarger > 0) {
                if (cur.rightChild == null) {
                    cur.rightChild = new Node<>(item, cur);
                    size++;
                    return true;
                } else {
                    cur = cur.rightChild;
                    itemIsLarger = item.compareTo(cur.data);
                }
            }
            // if the item is smaller than current node
            else {
                if (cur.leftChild == null) {
                    cur.leftChild = new Node<>(item, cur);
                    size++;
                    return true;
                } else {
                    cur = cur.leftChild;
                    itemIsLarger = item.compareTo(cur.data);
                }
            }
        }
        return false;
    }

    @Override
    public void addAll(Collection<? extends Type> items) {
        for (Type item : items) {
            add(item);
        }
    }

    @Override
    public boolean contains(Type item) {
        Node<Type> cur = root;
        while (cur != null) {
            // item equals to data in current node
            if (item.equals(cur.data)) return true;
            // item is larger than the data in cur node, search right subtree of cur node
            else if (item.compareTo(cur.data) > 0) cur = cur.rightChild;
            // item is smaller than data in cur node, search left subtree of cur node
            else cur = cur.leftChild;
        }
        return false;
    }

    @Override
    public boolean remove(Type item) {
        // to find the node contains item
        Node<Type> cur = root;
        while (cur != null) {
            // item equals to data in current node
            if (item.equals(cur.data)) break;
            // item is larger than the data in cur node, search right subtree of cur node
            else if (item.compareTo(cur.data) > 0) cur = cur.rightChild;
            // item is smaller than data in cur node, search left subtree of cur node
            else cur = cur.leftChild;
        }
        // item not found
        if (cur == null) return false;
        delete(cur);
        return true;
    }

    @Override
    public Type first() throws NoSuchElementException {
        if (size == 0) throw new NoSuchElementException();
        Node<Type> cur = root;
        while (cur.leftChild != null) {
            cur = cur.leftChild;
        }
        return cur.data;
    }

    @Override
    public Type last() throws NoSuchElementException {
        if (size == 0) throw new NoSuchElementException();
        Node<Type> cur = root;
        while (cur.rightChild != null) {
            cur = cur.rightChild;
        }
        return cur.data;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        size = 0;
        root = null;
    }

    private Node<Type> findSuccessor(Node<Type> cur) {
        Node<Type> successor = cur.rightChild;
        while (successor.leftChild != null) successor = successor.leftChild;
        return successor;
    }

    private void delete(Node<Type> cur) {
        // case 1: remove leaf node
        if (cur.leftChild == null && cur.rightChild == null) {
            if (cur.parent.leftChild.equals(cur)) cur.parent.leftChild = null;
            else cur.parent.rightChild = null;
        }
        // case 2: remove node that only have one child
        if (cur.leftChild != null ^ cur.rightChild != null) {
            Node<Type> curChild = cur.leftChild != null ? cur.leftChild : cur.rightChild;
            if (cur.parent.leftChild.equals(cur)) cur.parent.leftChild = curChild;
            else cur.parent.rightChild = curChild;
        }
        // case 3: remove node that have both child
        if (cur.leftChild != null && cur.rightChild != null) {
            // find successor
            Node<Type> successor = findSuccessor(cur);
            // replace cur node with successor
            cur.data = successor.data;
            // remove successor
            if (successor.parent.leftChild.equals(successor)) successor.parent.leftChild = null;
            else successor.parent.rightChild = null;
        }
    }

    @Override
    public Iterator<Type> iterator() {
        return new BSTIterator();
    }

    private class BSTIterator implements Iterator<Type> {
        private int count;
        private boolean hasCalledNext;
        private Node<Type> cur, deletePt;

        public BSTIterator() {
            count = 0;
            hasCalledNext = false;
            cur = root;
            while (cur.leftChild != null) {
                cur = cur.leftChild;
            }
        }

        @Override
        public boolean hasNext() {
            return count < size;
        }

        @Override
        public Type next() {
            Type data = null;
            if (hasNext()) {
                deletePt = cur;
                // if current node don't have right child, then go to its parent
                if (cur.rightChild == null) {
                    data = cur.data;
                    cur = cur.parent;
                }
                // if current node have right child, then go to its successor
                else {
                    data = cur.data;
                    cur = findSuccessor(cur);
                }
                count++;
                hasCalledNext = true;
            }
            return data;
        }

        @Override
        public void remove() {
            delete(deletePt);
        }
    }
}
