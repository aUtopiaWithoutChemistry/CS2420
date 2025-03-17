package assign08;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BinarySearchTreeTest {
    private BinarySearchTree<Integer> emptyTree, smallTree;
    @BeforeEach
    void setUp() {
        emptyTree = new BinarySearchTree<>();
        smallTree = new BinarySearchTree<>();

        smallTree.add(4);
        smallTree.add(2);
        smallTree.add(6);
        smallTree.add(1);
        smallTree.add(3);
        smallTree.add(5);
        smallTree.add(7);
    }

    @Test
    void testAddEmpty() {
        assertTrue(emptyTree.add(1));
    }

    @Test
    void testToString() {
        emptyTree.add(5);
        emptyTree.add(1);
        emptyTree.add(9);
        emptyTree.add(3);
        emptyTree.add(2);
        emptyTree.add(4);
        assertEquals("""
                digraph BST {
                  5 -> 1;
                  5 -> 9;
                  1 -> 3;
                  3 -> 2;
                  3 -> 4;
                }""", emptyTree.toString());
    }

    @Test
    void testAddAll() {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(5);
        numbers.add(1);
        numbers.add(9);
        numbers.add(3);
        numbers.add(2);
        numbers.add(4);
        emptyTree.addAll(numbers);
        assertEquals("""
                digraph BST {
                  5 -> 1;
                  5 -> 9;
                  1 -> 3;
                  3 -> 2;
                  3 -> 4;
                }""", emptyTree.toString());
    }

    @Test
    void testContains() {
        assertTrue(smallTree.contains(1));
        assertTrue(smallTree.contains(2));
        assertTrue(smallTree.contains(3));
        assertTrue(smallTree.contains(4));
        assertTrue(smallTree.contains(5));
        assertTrue(smallTree.contains(6));
        assertTrue(smallTree.contains(7));
        assertFalse(smallTree.contains(0));
    }

    @Test
    void testSize() {
        assertEquals(7, smallTree.size());
    }

    @Test
    void testEmpty() {
        assertTrue(emptyTree.isEmpty());
        assertFalse(smallTree.isEmpty());
    }
}