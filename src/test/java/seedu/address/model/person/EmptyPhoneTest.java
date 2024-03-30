package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
public class EmptyPhoneTest {

    @Test
    public void checkEquality() {
        EmptyPhone emptyPhone = new EmptyPhone();
        Phone nonEmptyPhone = new Phone("776");

        // Another Empty address -> returns true
        assertTrue(emptyPhone.equals(new EmptyPhone()));

        // Itself -> returns true
        assertTrue(emptyPhone.equals(emptyPhone));

        // A non-empty address -> returns false
        assertFalse(emptyPhone.equals(nonEmptyPhone));

        // null -> returns false
        assertFalse(emptyPhone.equals(null));

        // Primitive data type -> returns false
        assertFalse(emptyPhone.equals("Some Words"));

        // Non-primitive data type -> returns false
        assertFalse(emptyPhone.equals(new EmptyAddress()));
    }

    @Test
    public void checkEmptiness() {
        assertTrue(new EmptyPhone().isEmpty());
    }
}
