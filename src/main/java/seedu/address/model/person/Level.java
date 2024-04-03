package seedu.address.model.person;

/**
 * Represents a Person's level in the address book.
 * Guarantees: immutable; is always valid
 */
public class Level {
    public static final String MESSAGE_CONSTRAINTS = "Levels should only be P1, P2, P3, P4, P5 or P6";

    private final LevelEnum internalLevel;

    /**
     * Constructs a {@code Level}.
     *
     * @param level A valid level.
     */
    public Level(String level) {
        level = level.trim().toUpperCase();
        this.internalLevel = LevelEnum.valueOf(level);
    }

    public Level() {
        this.internalLevel = null;
    }

    @Override
    public String toString() {
        return internalLevel.toString();
    }

    /**
     * Returns if a given string is a valid level.
     */
    public static boolean isValidLevel(String test) {
        try {
            test = test.trim().toUpperCase();
            LevelEnum.valueOf(test);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Level)) {
            return false;
        }

        if (other instanceof EmptyLevel) {
            return false;
        }

        Level otherLevel = (Level) other;
        return internalLevel.equals(otherLevel.internalLevel);
    }
}
