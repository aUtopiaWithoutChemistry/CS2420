package assign06;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the SinglyLinkedList class.
 *
 * @author Zifan Zuo
 * @version 2025-02-25
 */
class SinglyLinkedListTest {
    public SinglyLinkedList<Integer> emptyList, smallList, largeList;
    public SinglyLinkedList<Character> charList;
    public Integer[] smallArr;
    public Character[] charArr;
    public final int sizeOfLargeList = 1000;
    public final Random rng = new Random(114514);
    
    @BeforeEach
    void setUp() {
        emptyList = new SinglyLinkedList<>();
        smallList = new SinglyLinkedList<>();
        largeList = new SinglyLinkedList<>();
        charList = new SinglyLinkedList<>();
        
        smallList.insert(0, 5);
        smallList.insert(1, 7);
        smallList.insertFirst(9);
        smallList.insertFirst(1);
        
        smallArr = new Integer[]{1, 9, 5, 7};

        charList.insertFirst('a');
        charList.insertFirst('b');
        charList.insertFirst('c');
        charList.insertFirst('d');

        charArr = new Character[]{'d', 'c', 'b', 'a'};

        for (int i = 0; i < sizeOfLargeList; i++) {
            if (i < 2) largeList.insert(0, rng.nextInt(10000));
            else largeList.insert(rng.nextInt(i - 1), rng.nextInt(10000));
        }
    }

    @Test
    void testInsertFirstEmpty() {
        emptyList.insertFirst(10);
        assertEquals(10, emptyList.getFirst());
    }

    @Test
    void testInsertFirstGeneric() {
        charList.insertFirst('e');
        assertArrayEquals(new Character[]{'e', 'd', 'c', 'b', 'a'}, charList.toArray());
    }

    @Test
    void testInsertFirstNonEmpty() {
        emptyList.insertFirst(5);
        emptyList.insertFirst(10);
        assertEquals(10, emptyList.getFirst());
    }

    @Test
    void testInsertFirstOnSmallList() {
        smallList.insertFirst(114);
        assertEquals(114, smallList.getFirst());
    }

    @Test
    void testInsertFirstOnLargeList() {
        largeList.insertFirst(514);
        assertEquals(514, largeList.getFirst());
        assertEquals(sizeOfLargeList + 1, largeList.size());
    }

    @Test
    void testInsertAtZeroEmptyList() {
        emptyList.insert(0, 10);
        emptyList.insert(0, 5);
        assertEquals(5, emptyList.getFirst());
    }

    @Test
    void testInsertAtZeroSmallListBaseOnSize() {
        smallList.insert(0, 114);
        assertEquals(5, smallList.size());
    }

    @Test
    void testInsertAtSecondGeneric() {
        charList.insert(1, 'e');
        assertArrayEquals(new Character[]{'d', 'e', 'c', 'b', 'a'}, charList.toArray());
    }

    @Test
    void testInsertAtSecondAndThirdPlace() {
        smallList.insert(2, 514);
        smallList.insert(1, 114);
        assertArrayEquals(new Integer[]{1, 114, 9, 514, 5, 7}, smallList.toArray());
    }

    @Test
    void testInsertAtMiddleLargeListBaseOnSize() {
        largeList.insert(114, 514);
        assertEquals(sizeOfLargeList + 1, largeList.size());
    }

    @Test
    void testInsertAtEnd() {
        emptyList.insert(0, 5);
        System.out.println(Arrays.toString(emptyList.toArray()));
        emptyList.insert(1, 4);
        System.out.println(Arrays.toString(emptyList.toArray()));
        emptyList.insert(2, 3);
        System.out.println(Arrays.toString(emptyList.toArray()));
        emptyList.insert(3, 2);
        emptyList.insert(4, 1);
        assertArrayEquals(new Integer[]{5, 4, 3, 2, 1}, emptyList.toArray());
    }

    @Test
    void testInsertOutOfBound() {
        assertThrows(IndexOutOfBoundsException.class, () -> emptyList.insert(6, 1919810));
    }

    @Test
    void testGetFirstSmall() {
        assertEquals(1, smallList.getFirst());
    }

    @Test
    void testGetFirstGeneric() {
        assertEquals('d', charList.getFirst());
    }

    @Test
    void testGetFirstLargeSizeNoChange() {
        largeList.getFirst();
        assertEquals(sizeOfLargeList, largeList.size());
    }

    @Test
    void testGetFirstEmpty() {
        assertThrows(NoSuchElementException.class, () -> emptyList.getFirst());
    }

    @Test
    void testGetZeroIndexOnSmall() {
        assertEquals(1, smallList.get(0));
    }

    @Test
    void testGetGeneric() {
        assertEquals('c', charList.get(1));
    }

    @Test
    void testGetMiddleIndexOnLargeNoChangeSize() {
        largeList.get(sizeOfLargeList / 2);
        assertEquals(sizeOfLargeList, largeList.size());
    }

    @Test
    void testGetLastIndexOnLarge() {
        largeList.get(sizeOfLargeList - 1);
        assertThrows(IndexOutOfBoundsException.class, () -> largeList.get(sizeOfLargeList));
    }

    @Test
    void testDeleteFirstOnEmpty() {
        assertThrows(NoSuchElementException.class, () -> emptyList.deleteFirst());
    }

    @Test
    void testDeleteFirstGeneric() {
        assertEquals('d', charList.deleteFirst());
        assertArrayEquals(new Character[]{'c', 'b', 'a'}, charList.toArray());
    }

    @Test
    void testDeleteFirstOnSmallBasedOnArray() {
        smallList.deleteFirst();
        assertArrayEquals(new Integer[]{9, 5, 7}, smallList.toArray());
    }

    @Test
    void testDeleteFirstOnSmallBasedOnIsEmpty() {
        for (int i = 0; i < 4; i++) {
            smallList.deleteFirst();
        }
        assertTrue(smallList.isEmpty());
    }

    @Test
    void testDeleteFirstOnLargeBasedOnSize() {
        for (int i = 0; i < 20; i++) {
            largeList.deleteFirst();
        }
        assertEquals(sizeOfLargeList - 20, largeList.size());
    }

    @Test
    void testDeleteOnEmpty() {
        assertThrows(IndexOutOfBoundsException.class, () -> emptyList.delete(0));
    }

    @Test
    void testDeleteGeneric() {
        assertEquals('a', charList.delete(3));
    }

    @Test
    void testDeleteOnSmallListOutOfBound() {
        assertThrows(IndexOutOfBoundsException.class, () -> smallList.delete(4));
    }

    @Test
    void testDeleteOnSmallListBasedOnArray() {
        smallList.delete(1);
        assertArrayEquals(new Integer[]{1, 5, 7}, smallList.toArray());
    }

    @Test
    void testDeleteOnLargeListBasedOnSize() {
        for (int i = 0; i < 100; i++) {
            largeList.delete(rng.nextInt(sizeOfLargeList - i - 1));
        }
        assertEquals(sizeOfLargeList - 100, largeList.size());
    }

    @Test
    void testDeleteOnSmallListLastIndexBasedOnArray() {
        for (int i = 0; i < 3; i++) {
            smallList.delete(smallList.size() - 1);
        }
        assertEquals(1, smallList.getFirst());
    }

    @Test
    void testIndexOfEmpty() {
        assertEquals(-1, emptyList.indexOf(5));
    }

    @Test
    void testGenericNotFound() {
        assertEquals(-1, charList.indexOf('o'));
    }

    @Test
    void testGenericFound() {
        assertEquals(2, charList.indexOf('b'));
    }

    @Test
    void testIndexOfSmallNotFound() {
        assertEquals(-1, smallList.indexOf(8));
    }

    @Test
    void testIndexOfSmallFound() {
        assertEquals(0, smallList.indexOf(1));
        assertEquals(2, smallList.indexOf(5));
    }

    @Test
    void testIndexOfSmallReturnTheFirstMatch() {
        for (int i = 0; i < 4; i++) {
            smallList.insert(smallList.size() - 1, 1);
        }
        assertEquals(0, smallList.indexOf(1));
    }

    @Test
    void testIndexOfLarge() {
        largeList.insert(largeList.size(), 114514);
        assertEquals(sizeOfLargeList, largeList.indexOf(114514));
    }

    @Test
    void testSizeEmpty() {
        assertEquals(0, emptyList.size());
    }

    @Test
    void testGenericSize() {
        assertEquals(4, charList.size());
    }

    @Test
    void testSizeSmall() {
        assertEquals(4, smallList.size());
    }

    @Test
    void testSizeLarge() {
        assertEquals(sizeOfLargeList, largeList.size());
    }

    @Test
    void testSizeChange() {
        largeList.insertFirst(1);
        assertEquals(sizeOfLargeList + 1, largeList.size());
        largeList.deleteFirst();
        assertEquals(sizeOfLargeList, largeList.size());
        largeList.insert(514, 114);
        assertEquals(sizeOfLargeList + 1, largeList.size());
        largeList.delete(514);
        assertEquals(sizeOfLargeList, largeList.size());
    }

    @Test
    void testIsEmptyOnEmptyList() {
        assertTrue(emptyList.isEmpty());
    }

    @Test
    void testIsEmptyRemoveItemsInSmallList() {
        for (int i = 0; i < 4; i++) {
            smallList.deleteFirst();
        }
        assertTrue(smallList.isEmpty());
    }

    @Test
    void testIsEmptyRemoveItemsInGenericList() {
        for (int i = 0; i < 4; i++) {
            charList.deleteFirst();
        }
        assertTrue(charList.isEmpty());
    }

    @Test
    void testIsEmptyRemoveItemsInLargeList() {
        for (int i = 0; i < sizeOfLargeList; i++) {
            largeList.deleteFirst();
        }
        assertTrue(largeList.isEmpty());
    }

    @Test
    void testIsEmptyByClearItemsInLargeList() {
        largeList.clear();
        assertTrue(largeList.isEmpty());
    }

    @Test
    void testClearBasedOnSize() {
        largeList.clear();
        assertEquals(0, largeList.size());
    }

    @Test
    void testClearBasedOnIsEmpty() {
        smallList.clear();
        assertTrue(smallList.isEmpty());
    }

    @Test
    void testClearBasedOnGetFirstGeneric() {
        charList.clear();
        assertThrows(NoSuchElementException.class, () -> charList.getFirst());
    }

    @Test
    void testClearBasedOnGetFirst() {
        largeList.clear();
        assertThrows(NoSuchElementException.class, () -> largeList.getFirst());
    }

    @Test
    void testClearBasedOnGetZeroIndex() {
        smallList.clear();
        assertThrows(IndexOutOfBoundsException.class, () -> smallList.get(0));
    }

    @Test
    void testClearBasedOnDeleteFirst() {
        smallList.clear();
        assertThrows(NoSuchElementException.class, () -> smallList.deleteFirst());
    }

    @Test
    void testClearBasedOnDeleteZeroIndex() {
        largeList.clear();
        assertThrows(IndexOutOfBoundsException.class, () -> largeList.delete(0));
    }

    @Test
    void testToArrayEmptyList() {
        emptyList.insertFirst(1);
        emptyList.insertFirst(3);
        emptyList.insertFirst(5);
        emptyList.insertFirst(7);
        emptyList.insertFirst(9);
        assertArrayEquals(new Integer[]{9, 7, 5, 3, 1}, emptyList.toArray());

    }

    @Test
    void testToArraySmallList() {
        assertArrayEquals(smallArr, smallList.toArray());
    }

    @Test
    void testToArrayGenericList() {
        assertArrayEquals(charArr, charList.toArray());
    }

    @Test
    void testToArrayLargeList() {
        Object[] largeArr = largeList.toArray();
        assertEquals(sizeOfLargeList, largeArr.length);
    }

    @Test
    void testIteratorHasNext() {
        emptyList.insertFirst(1);
        emptyList.insertFirst(3);
        emptyList.insertFirst(5);
        emptyList.insertFirst(7);
        emptyList.insertFirst(9);
        assertTrue(emptyList.iterator().hasNext());
    }

    @Test
    void testIteratorGenericHasNext() {
        Iterator<Character> myIterator = charList.iterator();
        assertTrue(myIterator.hasNext());
    }

    @Test
    void testIteratorNext() {
        emptyList.insertFirst(1);
        emptyList.insertFirst(3);
        emptyList.insertFirst(5);
        emptyList.insertFirst(7);
        emptyList.insertFirst(9);
        assertEquals(9, emptyList.iterator().next());
    }

    @Test
    void testIteratorNextGeneric() {
        Iterator<Character> myIterator = charList.iterator();
        assertEquals('d', myIterator.next());
        assertEquals('c', myIterator.next());
        assertEquals('b', myIterator.next());
        assertEquals('a', myIterator.next());
    }

    @Test
    void testIteratorNextEmpty() {
        Iterator<Integer> myIterator = emptyList.iterator();
        assertThrows(NoSuchElementException.class, () -> myIterator.next());
    }

    @Test
    void testIteratorRemoveOneElement() {
        emptyList.insertFirst(1);
        Iterator<Integer> myIterator = emptyList.iterator();
        assertEquals(1, myIterator.next());
        myIterator.remove();
        assertEquals(true, emptyList.isEmpty());
    }

    @Test
    void testIteratorRemoveThreeElement() {
        emptyList.insertFirst(1);
        emptyList.insertFirst(3);
        emptyList.insertFirst(5);
        Iterator<Integer> myIterator = emptyList.iterator();
        assertEquals(5, myIterator.next());
        myIterator.remove();
        assertEquals(3, myIterator.next());
        myIterator.remove();
        assertEquals(1, myIterator.next());
        myIterator.remove();
        assertEquals(true, emptyList.isEmpty());
    }

    @Test
    void testIteratorRemoveItemFromEmpty() {
        Iterator<Integer> myIterator = emptyList.iterator();
        assertThrows(UnsupportedOperationException.class, () -> myIterator.remove());
    }

    @Test
    void testIteratorRemoveTwiceFromSmall() {
        Iterator<Integer> myIterator = smallList.iterator();
        myIterator.next();
        myIterator.remove();
        assertThrows(IllegalStateException.class, () -> myIterator.remove());
    }

    @Test
    void testIteratorRemoveElementGeneric() {
        Iterator<Character> myIterator = charList.iterator();
        myIterator.next();
        myIterator.remove();
        assertArrayEquals(new Character[]{'c', 'b', 'a'}, charList.toArray());
    }

    @Test
    void testIteratorRemoveFiveElements() {
        emptyList.insertFirst(1);
        emptyList.insertFirst(3);
        emptyList.insertFirst(5);
        emptyList.insertFirst(7);
        emptyList.insertFirst(9);
        Iterator<Integer> myIterator = emptyList.iterator();
        assertEquals(9, myIterator.next());
        myIterator.remove();
        assertArrayEquals(new Integer[]{7, 5, 3, 1}, emptyList.toArray());
    }

    @Test
    void testIteratorRemoveFiveElementsRemoveMiddle() {
        emptyList.insertFirst(1);
        emptyList.insertFirst(3);
        emptyList.insertFirst(5);
        emptyList.insertFirst(7);
        emptyList.insertFirst(9);
        Iterator<Integer> myIterator = emptyList.iterator();
        myIterator.next();
        myIterator.next();
        myIterator.next();
        assertEquals(3, myIterator.next());
        myIterator.remove();
        assertArrayEquals(new Integer[]{9, 7, 5, 1}, emptyList.toArray());
    }
}
