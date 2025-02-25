package assign06;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the LinkedListStack class.
 *
 * @author Zifan Zuo
 * @version 2025-02-25
 */
class LinkedListStackTest {
    public LinkedListStack<Integer> emptyStack, smallStack, largeStack;
    public LinkedListStack<Character> charStack;
    public final int sizeOfLargeStack = 1000;
    public final Random rng = new Random(1919810);

    @BeforeEach
    void setUp() {
        emptyStack = new LinkedListStack<>();
        smallStack = new LinkedListStack<>();
        largeStack = new LinkedListStack<>();
        charStack = new LinkedListStack<>();

        smallStack.push(3);
        smallStack.push(5);
        smallStack.push(4);
        smallStack.push(1);

        charStack.push('+');
        charStack.push('-');
        charStack.push('*');
        charStack.push('/');

        for (int i = 0; i < sizeOfLargeStack; i++) {
            largeStack.push(rng.nextInt(1000));
        }
    }

    @Test
    void testClearEmptyBasedOnIsEmpty() {
        emptyStack.clear();
        assertTrue(emptyStack.isEmpty());
    }

    @Test
    void testClearSmallBasedOnSize() {
        smallStack.clear();
        assertEquals(0, smallStack.size());
    }

    @Test
    void testClearLargeBasedOnPeek() {
        largeStack.clear();
        assertThrows(NoSuchElementException.class, () -> largeStack.peek());
    }

    @Test
    void testClearGenericBasedOnPop() {
        charStack.clear();
        assertThrows(NoSuchElementException.class, () -> charStack.pop());
    }

    @Test
    void testIsEmptyOnEmptyStack() {
        assertTrue(emptyStack.isEmpty());
    }

    @Test
    void testIsEmptySmallAfterClear() {
        smallStack.clear();
        assertTrue(smallStack.isEmpty());
    }

    @Test
    void testIsEmptyLargeAfterPopEveryElement() {
        for(int i = 0; i < sizeOfLargeStack; i++) {
            largeStack.pop();
        }
        assertTrue(largeStack.isEmpty());
    }

    @Test
    void testIsEmptyGenericAfterClear() {
        charStack.clear();
        assertTrue(charStack.isEmpty());
    }

    @Test
    void testPeekEmpty() {
        assertThrows(NoSuchElementException.class, () -> emptyStack.peek());
    }

    @Test
    void testPeekSmallBasedOnReturnValue() {
        assertEquals(1, smallStack.peek());
    }

    @Test
    void testPeekGenericBasedOnReturnValue() {
        assertEquals('/', charStack.peek());
    }

    @Test
    void testPeekLargeDoNotChangeSize() {
        int previousSize = largeStack.size();
        largeStack.peek();
        assertEquals(previousSize, largeStack.size());
    }

    @Test
    void testPopEmpty() {
        assertThrows(NoSuchElementException.class, () -> emptyStack.peek());
    }

    @Test
    void testPopSmallBasedOnReturnValue() {
        assertEquals(1, smallStack.peek());
    }

    @Test
    void testPopGenericBasedOnReturnValue() {
        assertEquals('/', charStack.peek());
    }

    @Test
    void testPopLargeDoNotChangeSize() {
        int previousSize = largeStack.size();
        largeStack.pop();
        assertEquals(previousSize - 1, largeStack.size());
    }

    @Test
    void testPushEmptySize() {
        emptyStack.push(4);
        assertEquals(1, emptyStack.size());
    }

    @Test
    void testPushSmallBasedOnPeek() {
        smallStack.push(114);
        assertEquals(114, smallStack.peek());
    }

    @Test
    void testPushLargeBasedOnPop() {
        largeStack.push(1919810);
        assertEquals(1919810, largeStack.pop());
    }

    @Test
    void testPushGeneric() {
        charStack.push('c');
        assertEquals('c', charStack.pop());
    }

    @Test
    void testSizeEmpty() {
        assertEquals(0, emptyStack.size());
    }

    @Test
    void testSizeSmall() {
        assertEquals(4, smallStack.size());
    }

    @Test
    void testSizeLarge() {
        assertEquals(sizeOfLargeStack, largeStack.size());
    }

    @Test
    void testSizeGeneric() {
        assertEquals(4, charStack.size());
    }
}
