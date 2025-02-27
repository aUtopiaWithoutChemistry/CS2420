package assign06;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the TextEditor class.
 * 
 * @author CS 2420 course staff and Zifan Zuo
 * @version 2025-02-25
 */
class TextEditorTester {
	private Edit[] mediumEdits;
	private TextEditor empty, small, medium;
	
	@BeforeEach
	void setUp() throws Exception {
		empty = new TextEditor();
		small = new TextEditor();
		medium = new TextEditor();
		
		// Insert characters into a TextEditor
		small.insert('e', 0);
		small.insert('l', 1);
		small.insert('l', 2);
		small.insert('h', 0); // <- characters can be inserted at any valid index
		small.insert('o', 4);
		// Now small should contain "hello"
		// Note that inserting at an invalid index will result in an exception
		
		// Edit objects can also be made for testing the history method
		mediumEdits = new Edit[29];
		for(int i = 0; i < 26; i++) {
			mediumEdits[i] = new Edit((char)('a' + i), i);
			medium.insert((char)('a' + i), i);
		}
		mediumEdits[26] = new Edit('3', 3);
		mediumEdits[27] = new Edit('3', 3);
		mediumEdits[28] = new Edit('3', 3);
		medium.insert('3', 3);
		medium.insert('3', 3);
		medium.insert('3', 3);
	}

	@Test
	void testEmptyToString() {
		assertEquals("", empty.toString());
	}
	
	@Test
	void testEmptyInsert() {
		empty.insert('Y', 0);
		assertEquals("Y", empty.toString());
	}
	
	@Test
	void testEmptyInsertUndo() {
		empty.insert('Z', 0);
		empty.undo();
		assertEquals("", empty.toString());
	}

	@Test
	void testEmptyUndo() {
		assertThrows(UnsupportedOperationException.class, () -> empty.undo());
	}

	@Test
	void testSmallRedo() {
		assertThrows(UnsupportedOperationException.class, () -> small.redo());
	}
	
	@Test
	void testEmptyInsertUndoRedo() {
		empty.insert('Z', 0);
		empty.undo();
		empty.redo();
		assertEquals("Z", empty.toString());
	}

	@Test
	void testEmptyHistorySize() {
		SinglyLinkedList<Edit> hist = empty.history();
		assertEquals(0, hist.size());
	}
	
	@Test
	void testSmallToString() {
		assertEquals("hello", small.toString());
	}

	@Test
	void testSmallInsert() {
		small.insert('!', 5);
		assertEquals("hello!", small.toString());
	}

	@Test
	void testSmallInsertUndo() {
		small.insert('Z', 0);
		small.undo();
		assertEquals("hello", small.toString());
	}

	@Test
	void testSmallInsertUndoRedo() {
		small.insert('!', 5);
		small.undo();
		small.redo();
		assertEquals("hello!", small.toString());
	}

	@Test
	void testSmallHistorySize() {
		SinglyLinkedList<Edit> hist = small.history();
		assertEquals(5, hist.size());
	}

	@Test
	void testSmallHistoryLastElement() {
		SinglyLinkedList<Edit> hist = small.history();
		assertEquals(new Edit('o', 4).toString(), hist.get(4).toString());
	}

	@Test
	void testSmallHistoryUndo() {
		small.undo();
		small.undo();
		SinglyLinkedList<Edit> hist = small.history();
		assertEquals(5, hist.size());
	}

	@Test
	void testSmallHistoryToString() {
		small.undo();
		small.insert('!', 4);
		small.redo();
		assertEquals("hello!", small.toString());
	}

	@Test
	void testSmallHistorySizeAfterUndoInsertAndRedo() {
		small.undo();
		small.insert('!', 4);
		small.redo();
		SinglyLinkedList<Edit> hist = small.history();
		assertEquals(6, hist.size());
	}
	
	@Test
	void testMediumHistorySize() {
		SinglyLinkedList<Edit> hist = medium.history();
		assertEquals(mediumEdits.length, hist.size());
	}

	@Test
	void testMediumHistorySizeAfterMultipleUndoRedo() {
		medium.undo();
		medium.redo();
		medium.undo();
		medium.redo();
		SinglyLinkedList<Edit> hist = medium.history();
		assertEquals(mediumEdits.length, hist.size());
	}

	@Test
	void testMediumHistorySizeAfterUndoInsertAndRedo() {
		medium.undo();
		medium.insert('a', 27);
		medium.redo();
		SinglyLinkedList<Edit> hist = medium.history();
		assertEquals(30, hist.size());
	}
}
