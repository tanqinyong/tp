package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
public class EmptyAddressTest {

    @Test
    public void checkEquality() {
        EmptyAddress emptyAddress = new EmptyAddress();
        Address nonEmptyAddress = new Address("Harth");

        // Another Empty address -> returns true
        assertTrue(emptyAddress.equals(new EmptyAddress()));

        // Itself -> returns true
        assertTrue(emptyAddress.equals(emptyAddress));

        // A non-empty address -> returns false
        assertFalse(emptyAddress.equals(nonEmptyAddress));

        // null -> returns false
        assertFalse(emptyAddress.equals(null));

        // Primitive data type -> returns false
        assertFalse(emptyAddress.equals("Some words"));

        // Non-primitive data type -> returns false
        assertFalse(emptyAddress.equals(new EmptyPhone()));
    }

    @Test
    public void checkEmptiness() {
        assertTrue(new EmptyAddress().isEmpty());
    }
}
