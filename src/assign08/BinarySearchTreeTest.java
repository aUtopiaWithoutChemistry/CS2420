package assign08;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BinarySearchTreeTest {
    private BinarySearchTree<Integer> emptyTree, smallTree, largeTree;
    private List<Integer> smallList, largeList;

    @BeforeEach
    void setUp() {
        emptyTree = new BinarySearchTree<>();
        smallTree = new BinarySearchTree<>();
        largeTree = new BinarySearchTree<>();
        largeList = new ArrayList<>();
        Set<Integer> largeSet = new HashSet<>();
        Random rng = new Random();

        smallList = new ArrayList<>();
        smallList.add(4);
        smallList.add(2);
        smallList.add(6);
        smallList.add(1);
        smallList.add(3);
        smallList.add(5);
        smallList.add(7);
        smallTree.addAll(smallList);

        Collections.sort(smallList);

        int largeSize = 100;
        for (int i = 0; i < largeSize; i++) {
            int num = rng.nextInt(largeSize);
            largeTree.add(num);
            largeSet.add(num);
        }
        largeList.addAll(largeSet);
        Collections.sort(largeList);
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

    @Test
    void testIterator() {
        List<Integer> treeList = new ArrayList<>();
        for (Integer num : largeTree) {
            treeList.add(num);
        }
        for (int i = 0; i < treeList.size(); i++) {
            assertEquals(treeList.get(i), largeList.get(i));
        }
    }

    @Test
    void testIteratorNext() {
        Iterator<Integer> myIterator = smallTree.iterator();
        assertEquals(1, myIterator.next());
        assertEquals(2, myIterator.next());
        assertEquals(3, myIterator.next());
        assertEquals(4, myIterator.next());
        assertEquals(5, myIterator.next());
        assertEquals(6, myIterator.next());
        assertEquals(7, myIterator.next());
    }

    @Test
    void testRemove() {
        smallTree.remove(1);
        smallList.remove(0);
        assertEquals(6, smallTree.size());
        assertEquals("""
                digraph BST {
                  4 -> 2;
                  4 -> 6;
                  2 -> 3;
                  6 -> 5;
                  6 -> 7;
                }""", smallTree.toString());

        smallTree.remove(6);
        smallList.remove(4);
        assertEquals(5, smallTree.size());
        assertEquals("""
                digraph BST {
                  4 -> 2;
                  4 -> 7;
                  2 -> 3;
                  7 -> 5;
                }""", smallTree.toString());

        smallTree.remove(2);
        smallList.remove(0);
        assertEquals(4, smallTree.size());
        assertEquals("""
                digraph BST {
                  4 -> 3;
                  4 -> 7;
                  7 -> 5;
                }""", smallTree.toString());
    }

    @Test
    void testFirst() {
        assertEquals(Integer.valueOf(1), smallTree.first());
        assertEquals(largeList.get(0), largeTree.first());

        // Test with empty tree
        assertThrows(NoSuchElementException.class, () -> emptyTree.first());
    }

    @Test
    void testLast() {
        assertEquals(Integer.valueOf(7), smallTree.last());
        assertEquals(largeList.get(largeList.size() - 1), largeTree.last());

        // Test with empty tree
        assertThrows(NoSuchElementException.class, () -> emptyTree.last());
    }

    @Test
    void testClear() {
        smallTree.clear();
        assertTrue(smallTree.isEmpty());
        assertEquals(0, smallTree.size());

        largeTree.clear();
        assertTrue(largeTree.isEmpty());
        assertEquals(0, largeTree.size());
    }

    @Test
    void testRemoveRoot() {
        // Remove root node
        smallTree.remove(4);
        assertEquals("""
            digraph BST {
              5 -> 2;
              5 -> 6;
              2 -> 1;
              2 -> 3;
              6 -> 7;
            }""", smallTree.toString());
    }

    @Test
    void testRemoveNonExistentElement() {
        // Try to remove element that doesn't exist
        assertFalse(smallTree.remove(10));
        assertEquals(7, smallTree.size());
    }

    @Test
    void testAddDuplicate() {
        // Adding a duplicate should return false and not change the tree
        assertFalse(smallTree.add(4));
        assertEquals(7, smallTree.size());
    }

    @Test
    void testIteratorRemove() {
        Iterator<Integer> iterator = smallTree.iterator();
        iterator.next(); // Move to first element
        iterator.remove(); // Remove the first element (1)

        assertEquals(6, smallTree.size());
        assertFalse(smallTree.contains(1));

        // Test the structure after removal
        assertEquals("""
            digraph BST {
              4 -> 2;
              4 -> 6;
              2 -> 3;
              6 -> 5;
              6 -> 7;
            }""", smallTree.toString());
    }

    @Test
    void testRemoveWithNoRightChild() {
        // Create a specific tree structure
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.add(10);
        tree.add(5);
        tree.add(15);
        tree.add(3);

        // Remove node with only left child
        tree.remove(5);
        assertEquals(3, tree.size());
        assertEquals("""
            digraph BST {
              10 -> 3;
              10 -> 15;
            }""", tree.toString());
    }

    @Test
    void testRemoveWithNoLeftChild() {
        // Create a specific tree structure
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.add(10);
        tree.add(5);
        tree.add(15);
        tree.add(20);

        // Remove node with only right child
        tree.remove(15);
        assertEquals(3, tree.size());
        assertEquals("""
            digraph BST {
              10 -> 5;
              10 -> 20;
            }""", tree.toString());
    }

    @Test
    void testIteratorRemoveWithoutNext() {
        Iterator<Integer> iterator = smallTree.iterator();

        // Attempting to remove without calling next() should throw IllegalStateException
        assertThrows(IllegalStateException.class, iterator::remove);
    }

    @Test
    void testIteratorHasNext() {
        Iterator<Integer> iterator = smallTree.iterator();

        // Call hasNext() multiple times without calling next()
        assertTrue(iterator.hasNext());
        assertTrue(iterator.hasNext());

        // Exhaust the iterator
        while (iterator.hasNext()) {
            iterator.next();
        }

        // Verify hasNext() returns false when exhausted
        assertFalse(iterator.hasNext());
    }

    @Test
    void testIteratorWithEmptyTree() {
        Iterator<Integer> iterator = emptyTree.iterator();

        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void testRemoveRootWithOneChild() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.add(10);
        tree.add(5);

        tree.remove(10);
        assertEquals(1, tree.size());
        assertEquals(Integer.valueOf(5), tree.first());
        assertEquals(Integer.valueOf(5), tree.last());
    }

    @Test
    void testRemoveRootWithNoChildren() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.add(10);

        tree.remove(10);
        assertEquals(0, tree.size());
        assertTrue(tree.isEmpty());
    }

    @Test
    void testRemoveNodeWithRightChildOnly() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.add(10);
        tree.add(15);
        tree.add(5);
        tree.add(7);

        tree.remove(5);
        assertEquals(3, tree.size());
        assertEquals("""
            digraph BST {
              10 -> 7;
              10 -> 15;
            }""", tree.toString());
    }

    @Test
    void testRemoveSuccessorWithRightChild() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.add(10);
        tree.add(5);
        tree.add(15);
        tree.add(12);
        tree.add(20);
        tree.add(13);

        // When removing 15, it should replace it with 20 (the successor)
        tree.remove(15);
        assertEquals(5, tree.size());
        assertEquals("""
            digraph BST {
              10 -> 5;
              10 -> 20;
              20 -> 12;
              12 -> 13;
            }""", tree.toString());
    }

    @Test
    void testRemoveSequentially() {
        // Create a BST with values 1 to 10
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        for (int i = 1; i <= 10; i++) {
            tree.add(i);
        }

        // Remove values in order
        for (int i = 1; i <= 10; i++) {
            assertTrue(tree.remove(i));
            assertEquals(10 - i, tree.size());
        }

        // Verify tree is empty
        assertTrue(tree.isEmpty());
    }

    @Test
    void testIteratorRemoveSequentially() {
        // Create a copy of smallTree
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.addAll(smallList);

        // Remove all elements using iterator
        Iterator<Integer> iterator = tree.iterator();
        while (iterator.hasNext()) {
//            System.out.println(tree);
            iterator.next();
            iterator.remove();
        }

        // Verify tree is empty
        assertTrue(tree.isEmpty());
        assertEquals(0, tree.size());
    }

//    @Test
//    void testRemoveAfterIteratorFinished() {
//        Iterator<Integer> iterator = smallTree.iterator();
//
//        // Exhaust the iterator
//        while (iterator.hasNext()) {
//            iterator.next();
//        }
//        iterator.next();
//
//        // Try to call remove() after the iterator has been exhausted
//        assertThrows(IllegalStateException.class, iterator::remove);
//    }

    @Test
    void testRemoveMultipleTimesWithoutNext() {
        Iterator<Integer> iterator = smallTree.iterator();
        iterator.next();
        iterator.remove();

        // Try to call remove() again without calling next()
        assertThrows(IllegalStateException.class, iterator::remove);
    }

    @Test
    void testIsEmptyAfterRemovingAll() {
        // Create a tree with just 2 items
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.add(5);
        tree.add(10);

        // Confirm it's not empty with 2 items
        assertFalse(tree.isEmpty());
        assertEquals(2, tree.size());

        // Remove one item
        tree.remove(10);
        assertFalse(tree.isEmpty());
        assertEquals(1, tree.size());

        // Remove the last item
        tree.remove(5);
        assertTrue(tree.isEmpty());
        assertEquals(0, tree.size());
    }

    @Test
    void testTwoNodeTreeOperations() {
        // Tree with root and left child
        BinarySearchTree<Integer> leftTree = new BinarySearchTree<>();
        leftTree.add(10);
        leftTree.add(5);

        // Remove child first
        leftTree.remove(5);
        assertFalse(leftTree.isEmpty());
        assertEquals(1, leftTree.size());

        // Remove root
        leftTree.remove(10);
        assertTrue(leftTree.isEmpty());
        assertEquals(0, leftTree.size());

        // Tree with root and right child
        BinarySearchTree<Integer> rightTree = new BinarySearchTree<>();
        rightTree.add(10);
        rightTree.add(15);

        // Remove root first
        rightTree.remove(10);
        assertFalse(rightTree.isEmpty());
        assertEquals(1, rightTree.size());
        assertEquals(Integer.valueOf(15), rightTree.first());

        // Remove remaining node
        rightTree.remove(15);
        assertTrue(rightTree.isEmpty());
        assertEquals(0, rightTree.size());
    }
}