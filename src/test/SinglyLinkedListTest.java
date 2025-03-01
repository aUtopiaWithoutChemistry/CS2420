package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import assign06.SinglyLinkedList;

class SinglyLinkedListTest {

    private SinglyLinkedList<Integer> numbers;
    private SinglyLinkedList<Integer> emptyNumbers;
    private SinglyLinkedList<String> colors;
    private SinglyLinkedList<String> emptyColors;
    private SinglyLinkedList<Character> letters;
    private SinglyLinkedList<Character> emptyLetters;
    private SinglyLinkedList<Double> decimals;
    private SinglyLinkedList<Double> emptyDecimals;
    private SinglyLinkedList<Integer> smallArray;
    private SinglyLinkedList<Integer> bigerArray;
    private SinglyLinkedList<Integer> duplicateDigits;


    @BeforeEach
    void setUp() throws Exception {
        numbers = new SinglyLinkedList<>();
        for (int i = 0; i < 10; i++) {
            numbers.insertFirst(i);
        }
        emptyNumbers = new SinglyLinkedList<>();

        colors = new SinglyLinkedList<>();
        colors.insertFirst("red");
        colors.insertFirst("orange");
        colors.insertFirst("yellow");
        colors.insertFirst("green");
        colors.insertFirst("blue");
        colors.insertFirst("purple");
        emptyColors = new SinglyLinkedList<>();

        letters = new SinglyLinkedList<>();
        letters.insertFirst('a');
        letters.insertFirst('b');
        letters.insertFirst('c');
        letters.insertFirst('d');
        letters.insertFirst('e');
        letters.insertFirst('f');
        letters.insertFirst('g');
        letters.insertFirst('h');
        letters.insertFirst('i');
        letters.insertFirst('j');
        emptyLetters = new SinglyLinkedList<>();

        decimals = new SinglyLinkedList<>();
        double start = 1.0;
        for (int i = 0; i < 100; i++) {
            decimals.insertFirst(i + start);
        }
        emptyDecimals = new SinglyLinkedList<>();

        smallArray = new SinglyLinkedList<>();
        for (int i = 0; i < 10; i++) {
            for (int j = 4; j < 14; j++) {
                smallArray.insert(i, j);
            }
        }

        bigerArray = new SinglyLinkedList<>();
        for (int i = 0; i < 1024; i++) {
            bigerArray.insertFirst(i);
        }

        duplicateDigits = new SinglyLinkedList<>();

        for (int i = 0; i < 20000; i++) {
            duplicateDigits.insertFirst(1);

        }

    }


    //-------------------------------------------Test InsertFirst Method ---------------------------------------------
    @Test

    public void testSmallIntegerArrayInsertFirstMethod() {

        numbers.insertFirst(100);
        assertEquals(100, numbers.getFirst());


        colors.insertFirst("white");
        //The first element should be white
        assertEquals(colors.getFirst(), "white");


        letters.insertFirst('s');
        assertEquals(letters.getFirst(), 's');

    }


    @Test

    public void testBigerIntegerArrayInsertFirstMethod() {

        decimals.insertFirst(10000.0);
        assertEquals(decimals.getFirst(), 10000.0, "The first element should be 10000.0");


        smallArray.insertFirst(10000);
        assertEquals(smallArray.getFirst(), 10000, "The first element should be 10000");


        bigerArray.insertFirst(10000);
        assertEquals(bigerArray.getFirst(), 10000, "The first element should be 10000");


        duplicateDigits.insertFirst(10000);
        assertEquals(duplicateDigits.getFirst(), 10000, "The first element should be 10000");


    }


    @Test

    public void testEmptyArrayInsertFirstMethodonEdgeCase() {

        emptyNumbers.insertFirst(null);
        assertEquals(emptyNumbers.getFirst(), null, "The first element should be null");
        assertEquals(emptyNumbers.size(), 1, "The array size should be 0");

        emptyColors.insertFirst(" ");
        //"The first element should be empty"
        assertEquals(emptyColors.getFirst(), " ");
        assertEquals(emptyColors.size(), 1, "The array size should be 0");


        emptyLetters.insertFirst(null);
        assertEquals(emptyLetters.getFirst(), null, "The first element should be null");
        assertEquals(emptyLetters.size(), 1, "The array size should be 0");


        emptyDecimals.insertFirst(null);
        assertEquals(emptyDecimals.getFirst(), null, "The first element should be null");
        assertEquals(emptyDecimals.size(), 1, "The array size should be 0");

    }


    //---------------------------------------Test Insert Method ------------------------------------------------------------

    @Test

    public void testSmallIntegerArrayInsertMethod() {

        assertEquals(numbers.indexOf(9), 0, "The first element(9) index should be 0");
        numbers.insert(3, 10);
        assertEquals(numbers.indexOf(10), 3, "The element index should be 3");


        assertEquals(colors.indexOf("purple"), 0, "The first element(purple) index should be 0");
        colors.insert(0, "white");
        // The element(white) index should be 0
        assertEquals(colors.getFirst(), "white");

        assertEquals(letters.indexOf('j'), 0, "The first element(j) index should be 0");
        letters.insert(5, 's');
        assertEquals(letters.indexOf('s'), 5, "The element(s) index should be 5");


    }


    @Test

    public void testBigerIntegerArrayInsertMethod() {

        decimals.insert(0, 1.0);
        decimals.insert(1, 2.0);
        assertEquals(decimals.indexOf(1.0), 0, "The element index should be 0");
        assertEquals(decimals.indexOf(2.0), 1, "The element index should be 1");


        for (int i = 0; i < 10; i++) {
            bigerArray.insert(i, i);
        }

        for (int i = 9; i >= 0; i--) {
            assertEquals(bigerArray.get(i), i, "The elements should be equals");
        }

        assertEquals(bigerArray.size(), 1034, "The array size should be 1034");

    }


    @Test

    public void testEmptyArrayInsertMethodonEdgeCase() {

        assertThrows(IndexOutOfBoundsException.class, () -> {
            emptyDecimals.insert(100, null);
        });

        emptyNumbers.insert(0, null);
        assertEquals(emptyNumbers.getFirst(), null, "The first element should be null");
        assertEquals(emptyNumbers.size(), 1, "The array size should be 0");

        emptyColors.insert(0, " ");
        //"The first element should be empty"
        assertEquals(emptyColors.getFirst(), " ");
        assertEquals(emptyColors.size(), 1, "The array size should be 0");


        emptyLetters.insert(0, null);
        assertEquals(emptyLetters.getFirst(), null, "The first element should be null");
        assertEquals(emptyLetters.size(), 1, "The array size should be 0");


        emptyDecimals.insertFirst(null);
        assertEquals(emptyDecimals.getFirst(), null, "The first element should be null");
        assertEquals(emptyDecimals.size(), 1, "The array size should be 0");

        smallArray.insert(0, 1);
        smallArray.insert(0, 1);
        smallArray.insert(0, 1);
        assertEquals(smallArray.getFirst(), 1, "The first element should be 1");
        assertEquals(smallArray.indexOf(1), 0, "The element index should be 0");
        assertEquals(smallArray.get(0), 1, "The element should be 1");
        assertEquals(smallArray.get(1), 1, "The element should be 1");
        assertEquals(smallArray.get(2), 1, "The element should be 1");
    }


    //--------------------------------Test GetFirst Method ----------------------------------------------------------------------

    @Test

    public void testSmallIntegerArrayGetFirstMethod() {

        assertEquals(numbers.getFirst(), 9, "The first element should be 9");

        assertEquals(colors.getFirst(), "purple");

        assertEquals(letters.getFirst(), 'j', "The first element should be j");


    }


    @Test

    public void testBigerIntegerArrayGetFirstMethod() {


        assertEquals(decimals.getFirst(), 100.0, "The first element should be 100.0");

        assertEquals(smallArray.getFirst(), 13, "The first element should be 13");

        assertEquals(bigerArray.getFirst(), 1023, "The first element should be 1023");

        assertEquals(duplicateDigits.getFirst(), 1, "The first element should be 1");


    }


    @Test

    public void testEmptyArrayGetFirstMethodonEdgeCase() {

        assertThrows(NoSuchElementException.class, () -> {
            emptyNumbers.getFirst();
        });
        assertThrows(NoSuchElementException.class, () -> {
            emptyColors.getFirst();
        });
        assertThrows(NoSuchElementException.class, () -> {
            emptyLetters.getFirst();
        });
        assertThrows(NoSuchElementException.class, () -> {
            emptyDecimals.getFirst();
        });


    }

    //------------------------------------------Test Get Method ---------------------------------------------------------------------------------

    @Test
    public void testSmallIntegerArrayGetMethod() {
        int number = 9;
        for (int i = 0; i > 10; i++) {
            assertEquals(numbers.get(0), number - 1, "The elements should be equals");
        }

        colors.insertFirst("white");
        //The first element should be white
        assertEquals(colors.get(0), "white");
        assertEquals(colors.get(6), "red");

        assertEquals(letters.get(0), 'j', "The first element should be j");
        assertEquals(letters.get(9), 'a', "The first element should be a");


    }


    @Test

    public void testBigerIntegerArrayGetMethod() {

        decimals.insertFirst(10000.0);
        assertEquals(decimals.get(0), 10000.0, "The first element should be 10000.0");
        assertEquals(decimals.get(1), 100.0, "The first element should be 100.0");


        smallArray.insertFirst(10000);
        assertEquals(smallArray.get(0), 10000, "The first element should be 10000");


        bigerArray.insertFirst(10000);
        assertEquals(bigerArray.get(0), 10000, "The first element should be 10000");


        duplicateDigits.insertFirst(10000);
        assertEquals(duplicateDigits.get(0), 10000, "The first element should be 10000");


    }


    @Test

    public void testEmptyArrayGetMethodonEdgeCase() {

        emptyNumbers.insertFirst(null);
        assertEquals(emptyNumbers.get(0), null, "The first element should be null");
        assertEquals(emptyNumbers.size(), 1, "The array size should be 0");

        emptyColors.insertFirst(" ");
        //"The first element should be empty"
        assertEquals(emptyColors.get(0), " ");
        assertEquals(emptyColors.size(), 1, "The array size should be 0");


        emptyLetters.insertFirst(null);
        assertEquals(emptyLetters.get(0), null, "The first element should be null");
        assertEquals(emptyLetters.size(), 1, "The array size should be 0");


        emptyDecimals.insertFirst(null);
        assertEquals(emptyDecimals.getFirst(), null, "The first element should be null");
        assertEquals(emptyDecimals.size(), 1, "The array size should be 0");

    }


    //-------------------------------------------Test DeleteFirst Method-----------------------------------------------

    @Test

    public void testSmallIntegerArrayDeleteFirstMethod() {


        assertEquals(numbers.deleteFirst(), 9, "The first element should be 100");

        assertEquals(colors.deleteFirst(), "purple");

        assertEquals(letters.deleteFirst(), 'j', "The first element should be j");

    }


    @Test

    public void testBigerIntegerArrayDeleteFirstMethod() {

        assertEquals(decimals.deleteFirst(), 100.0, "The first element should be 100.0");

        assertEquals(smallArray.deleteFirst(), 13, "The first element should be 13");

        assertEquals(bigerArray.deleteFirst(), 1023, "The first element should be 1023");

        assertEquals(duplicateDigits.deleteFirst(), 1, "The first element should be 1");


    }


    @Test

    public void testEmptyArrayInsertDeleteFirstMethodonEdgeCase() {

        emptyNumbers.insertFirst(null);
        assertEquals(emptyNumbers.deleteFirst(), null, "The first element should be null");


        emptyColors.insertFirst(" ");
        //"The first element should be empty"
        assertEquals(emptyColors.deleteFirst(), " ");


        emptyLetters.insertFirst(null);
        assertEquals(emptyLetters.deleteFirst(), null, "The first element should be null");


        assertThrows(NoSuchElementException.class, () -> {
            emptyDecimals.deleteFirst();
        });
        emptyDecimals.insertFirst(null);
        assertEquals(emptyDecimals.deleteFirst(), null, "The first element should be null");


    }

    //---------------------------------------Test Delete Method ----------------------------------------

    @Test

    public void testSmallIntegerArrayDeleteMethod() {


        assertEquals(numbers.delete(3), 6, "The first element should be 6");

        assertEquals(colors.delete(2), "green");

        assertEquals(letters.delete(9), 'a', "The first element should be a");

    }


    @Test

    public void testBigerIntegerArrayDeleteMethod() {

        assertEquals(decimals.delete(98), 2.0, "The first element should be 2.0");

        assertEquals(smallArray.delete(5), 13, "The first element should be 13");

        assertEquals(bigerArray.delete(1000), 23, "The first element should be 23");

        assertEquals(duplicateDigits.delete(0), 1, "The first element should be 1");


    }


    @Test

    public void testEmptyArrayInsertDeleteMethodonEdgeCase() {

        assertThrows(IndexOutOfBoundsException.class, () -> {
            emptyNumbers.delete(0);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            emptyColors.delete(0);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            emptyLetters.delete(0);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            emptyDecimals.delete(0);
        });


    }

    //----------------------------------Test IndexOf Method-------------------------------------------

    @Test

    public void testSmallIntegerArrayIndexOfMethod() {


        assertEquals(numbers.indexOf(2), 7, "The index should be 8");

        assertEquals(numbers.indexOf(11), -1, "The index should be -1 because It isn't in the array");

        assertEquals(colors.indexOf("red"), 5);
        assertEquals(colors.indexOf("white"), -1);

        assertEquals(letters.indexOf('a'), 9, "The index should be 9");
        assertEquals(letters.indexOf('s'), -1, "The index should be -1 because It isn't in the array");

    }


    @Test

    public void testBigerIntegerArrayIndexOfMethod() {

        assertEquals(decimals.indexOf(99.0), 1, "The index should be 1");

        assertEquals(smallArray.indexOf(6), 16, "The index should be 16");

        assertEquals(bigerArray.indexOf(1020), 3, "The index should be 3");

        assertEquals(duplicateDigits.indexOf(1), 0, "The index should be 0");


    }


    @Test

    public void testEmptyArrayInsertIndexOfMethodonEdgeCase() {

        assertEquals(emptyNumbers.indexOf(0), -1);
        assertEquals(emptyColors.indexOf("hello"), -1);
        assertEquals(emptyLetters.indexOf('a'), -1);
        assertEquals(emptyDecimals.indexOf(1.0), -1);

    }

    //-------------------------------------Test Size Method--------------------------------------------
    @Test

    public void testSmallIntegerArraySizeMethod() {


        assertEquals(numbers.size(), 10, "The array size should be 10.");
        //"The array size should be 6."
        assertEquals(colors.size(), 6);
        assertEquals(letters.size(), 10, "The array size should be 10.");

    }


    @Test

    public void testBigerIntegerArraySizeMethod() {

        assertEquals(decimals.size(), 100, "The array size should be 100.");

        assertEquals(smallArray.size(), 100, "The array size should be 100.");

        assertEquals(bigerArray.size(), 1024, "The array size should be 1024.");

        assertEquals(duplicateDigits.size(), 20000, "The array size should be 20000.");


    }


    @Test

    public void testEmptyArraySizeMethodonEdgeCase() {

        assertEquals(emptyNumbers.size(), 0, "The array size should be 0.");
        assertEquals(emptyColors.size(), 0, "The array size should be 0.");
        assertEquals(emptyLetters.size(), 0, "The array size should be 0.");
        assertEquals(emptyDecimals.size(), 0, "The array size should be 0.");

    }

    //-------------------------------------Test IsEmpty Method-----------------------------------------
    @Test

    public void testSmallIntegerArrayisEmptyMethod() {


        assertFalse(numbers.isEmpty(), "The array size should be 10, and the result should be false");
        numbers.clear();
        assertTrue(numbers.isEmpty(), "The array size should be 0, and the result should be true");

        assertFalse(colors.isEmpty(), "The array size should be 6, and the result should be false");
        colors.clear();
        assertTrue(colors.isEmpty(), "The array size should be 0, and the result should be true");

        assertFalse(letters.isEmpty(), "The array size should be 10, and the result should be false");
        letters.clear();
        assertTrue(letters.isEmpty(), "The array size should be 0, and the result should be true");


    }


    @Test

    public void testBigerIntegerArrayisEmptyMethod() {

        assertFalse(decimals.isEmpty(), "The array size should be 100, and the result should be false");
        decimals.clear();
        assertTrue(decimals.isEmpty(), "The array size should be 0, and the result should be true");

        assertFalse(smallArray.isEmpty(), "The array size should be 100, and the result should be false");
        smallArray.clear();
        assertTrue(smallArray.isEmpty(), "The array size should be 0, and the result should be true");

        assertFalse(bigerArray.isEmpty(), "The array size should be 1024, and the result should be false");
        bigerArray.clear();
        assertTrue(bigerArray.isEmpty(), "The array size should be 0, and the result should be true");

        assertFalse(duplicateDigits.isEmpty(), "The array size should be 20000, and the result should be false");
        duplicateDigits.clear();
        assertTrue(duplicateDigits.isEmpty(), "The array size should be 0, and the result should be true");

    }


    @Test

    public void testEmptyArrayisEmptyMethodonEdgeCase() {

        assertTrue(emptyNumbers.isEmpty(), "The array size should be 0, and the result should be true");
        emptyNumbers.insert(0, null);
        assertFalse(emptyNumbers.isEmpty(), "The array size should be 1, and the result should be false");

        assertTrue(emptyColors.isEmpty(), "The array size should be 0, and the result should be true");
        emptyColors.insert(0, null);
        assertFalse(emptyColors.isEmpty(), "The array size should be 1, and the result should be false");

        assertTrue(emptyLetters.isEmpty(), "The array size should be 0, and the result should be true");
        emptyLetters.insert(0, null);
        assertFalse(emptyLetters.isEmpty(), "The array size should be 1, and the result should be false");

        assertTrue(emptyDecimals.isEmpty(), "The array size should be 0, and the result should be true");
        emptyDecimals.insert(0, null);
        assertFalse(emptyDecimals.isEmpty(), "The array size should be 1, and the result should be false");


    }


    //------------------------------------------Test Clear Method---------------------------------------

    @Test

    public void testClearMethodonDiffrentArrays() {

        assertEquals(numbers.size(), 10, "The array size should be 10.");
        numbers.clear();
        assertEquals(numbers.size(), 0, "The array size should be 0.");

        assertEquals(colors.size(), 6);
        colors.clear();
        assertEquals(colors.size(), 0, "The array size should be 0.");

        assertEquals(letters.size(), 10, "The array size should be 10.");
        letters.clear();
        assertEquals(letters.size(), 0, "The array size should be 0.");

        decimals.clear();
        smallArray.clear();
        bigerArray.clear();
        duplicateDigits.clear();

        assertEquals(decimals.size(), 0, "The array size should be 0.");

        assertEquals(smallArray.size(), 0, "The array size should be 0.");

        assertEquals(bigerArray.size(), 0, "The array size should be 0.");

        assertEquals(duplicateDigits.size(), 0, "The array size should be 0.");

    }

    //--------------------------Test ToArray Method--------------------------------------------------

    @Test

    public void testToArrayonEmptyArray() {

        emptyNumbers.insertFirst(10);
        emptyNumbers.insertFirst(20);
        Object[] array = emptyNumbers.toArray();
        assertEquals(2, array.length, "The array size should be 2");
        assertEquals(20, array[0], "The first element should be 20");
        assertEquals(10, array[1], "The second element should be 10");

        emptyColors.insertFirst("white");
        emptyColors.insertFirst("black");
        Object[] stringArray = emptyColors.toArray();
        assertEquals(2, stringArray.length, "The array size should be 2");
        assertEquals("black", stringArray[0]);
        assertEquals("white", stringArray[1]);

    }


    //-------------------------------Test Iterator Method----------------------------------------------

    @Test

    public void testIteratorMethodonEmptyArray() {

        emptyLetters.insertFirst('a');
        emptyLetters.insertFirst('b');
        emptyLetters.insertFirst('c');
        Iterator<Character> iterator = emptyLetters.iterator();

        assertTrue(iterator.hasNext());

        assertEquals('c', iterator.next());
        assertEquals('b', iterator.next());
        assertEquals('a', iterator.next());
        assertFalse(iterator.hasNext(), "The array doesn't have any elements");

        assertThrows(NoSuchElementException.class, iterator::next);


        emptyDecimals.insertFirst(1.0);
        emptyDecimals.insertFirst(2.0);
        emptyDecimals.insertFirst(3.0);
        Iterator<Double> iterator1 = emptyDecimals.iterator();

        assertTrue(iterator1.hasNext());

        assertEquals(3.0, iterator1.next());
        assertEquals(2.0, iterator1.next());
        assertEquals(1.0, iterator1.next());
        assertFalse(iterator1.hasNext(), "The array doesn't have any elements");

        assertThrows(NoSuchElementException.class, iterator1::next);
    }
}
