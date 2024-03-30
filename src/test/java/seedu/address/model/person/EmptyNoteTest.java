package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
public class EmptyNoteTest {

    @Test
    public void checkEquality() {
        EmptyNote emptyNote = new EmptyNote();
        Note nonEmptyNote = new Note("Why");

        // Another Empty address -> returns true
        assertTrue(emptyNote.equals(new EmptyNote()));

        // Itself -> returns true
        assertTrue(emptyNote.equals(emptyNote));

        // A non-empty address -> returns false
        assertFalse(emptyNote.equals(nonEmptyNote));

        // null -> returns false
        assertFalse(emptyNote.equals(null));

        // Primitive data type -> returns false
        assertFalse(emptyNote.equals("Some words"));

        // Non-primitive data type -> returns false
        assertFalse(emptyNote.equals(new EmptyPhone()));
    }

    @Test
    public void checkEmptiness() {
        assertTrue(new EmptyNote().isEmpty());
    }
}
