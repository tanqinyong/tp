package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
public class EmptyEmailTest {

    @Test
    public void checkEquality() {
        EmptyEmail emptyEmail = new EmptyEmail();
        Email nonEmptyEmail = new Email("test@example");

        // Another Empty address -> returns true
        assertTrue(emptyEmail.equals(new EmptyEmail()));

        // Itself -> returns true
        assertTrue(emptyEmail.equals(emptyEmail));

        // A non-empty address -> returns false
        assertFalse(emptyEmail.equals(nonEmptyEmail));

        // null -> returns false
        assertFalse(emptyEmail.equals(null));

        // Primitive data type -> returns false
        assertFalse(emptyEmail.equals("some words"));

        // Non-primitive data type -> returns false
        assertFalse(emptyEmail.equals(new EmptyPhone()));
    }

    @Test
    public void checkEmptiness() {
        assertTrue(new EmptyEmail().isEmpty());
    }
}
