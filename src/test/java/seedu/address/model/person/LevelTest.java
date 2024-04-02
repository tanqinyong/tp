package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class LevelTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Level(null));
    }

    @Test
    public void constructor_invalidLevel_throwsIllegalArgumentException() {
        String invalidLevel = "BOB";
        assertThrows(IllegalArgumentException.class, () -> new Level(invalidLevel));
    }

    @Test
    public void isValidLevel() {
        // null level
        assertThrows(NullPointerException.class, () -> Level.isValidLevel(null));

        // invalid levels
        assertFalse(Level.isValidLevel(""));
        assertFalse(Level.isValidLevel("BOB"));

        // valid levels
        assertTrue(Level.isValidLevel("P1"));
        assertTrue(Level.isValidLevel("P2"));
        assertTrue(Level.isValidLevel("P3"));
        assertTrue(Level.isValidLevel("P4"));
        assertTrue(Level.isValidLevel("P5"));
        assertTrue(Level.isValidLevel("P6"));
    }

    @Test
    public void equals() {
        Level level = new Level("P1");

        // same values -> returns true
        assertTrue(level.equals(new Level("P1")));

        // same object -> returns true
        assertTrue(level.equals(level));

        // null -> returns false
        assertFalse(level.equals(null));

        // different types -> returns false
        assertFalse(level.equals(5.0f));

        // different values -> returns false
        assertFalse(level.equals(new Level("P2")));
    }
}
