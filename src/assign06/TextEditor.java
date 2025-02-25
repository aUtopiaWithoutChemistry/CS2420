package assign06;

/**
 * Simulates the edit, undo, and redo functionality of a text editor. Edit will
 * update the current String and the stack of undo actions. Undo will reverse
 * the most recent edit and update the redo stack. Redo will reapply the last
 * undone edit and update the undo stack.
 * 
 * @author CS 2420 course staff and Zifan Zuo
 * @version 2025-02-25
 */
public class TextEditor {
	private StringBuilder text;
	private Stack<Edit> undoStack, redoStack;
	
	/**
	 * Constructs a TextEditor with empty text and undo/redo stacks.
	 */
	public TextEditor() {
		text = new StringBuilder();
		undoStack = new LinkedListStack<Edit>();
		redoStack = new LinkedListStack<Edit>();
	}
	
	/**
	 * Inserts one character at a specified position in the text.
	 * Note that this will result in an exception if the index is not valid.
	 * You don't need to handle or check for this situation in this method.
	 * 
	 * @param character to insert
	 * @param position in string at which to insert
	 */
	public void insert(char character, int position) {
		Edit e = new Edit(character, position);
		e.apply(text);
		undoStack.push(e);
	}
	
	/**
	 * Undoes the one edit that has been applied to the text.
	 * This is done in the reverse of the order in which they were applied.
	 */
	public void undo() {
		Edit e = undoStack.pop();
		e.revert(text);
		redoStack.push(e);
	}
	
	/**
	 * Re-applies one edit that was previously undone.
	 * This is done in the reverse of the order in which they were undone.
	 */
	public void redo() {
		Edit e = redoStack.pop();
		e.apply(text);
		undoStack.push(e);
	}
	
	/**
	 * Get a list of Edits representing the history of the text edits.
	 * The state of the object after calling this method must be exactly 
	 * the same as before.
	 * Edits are ordered from oldest to newest.
	 * 
	 * @return list of Edits in the order in which they were applied
	 */
	public SinglyLinkedList<Edit> history() {
		SinglyLinkedList<Edit> history = new SinglyLinkedList<>();
		int undoSize = undoStack.size();
		while (!undoStack.isEmpty()) {
			history.insert(0, undoStack.pop());
		}
		for (int i = 0; i < undoSize; i++) {
			undoStack.push(history.get(i));
		}
		int redoSize = redoStack.size();
		while (!redoStack.isEmpty()) {
			history.insert(0, redoStack.pop());
		}
		for (int i = 0; i < redoSize; i++) {
			undoStack.push(history.get(i));
		}
		return history;
	}
	
	/**
	 * Get the text being held in the editor.
	 * 
	 * @return a string representing the text in the editor
	 */
	public String toString() {
		return text.toString();
	}
}
