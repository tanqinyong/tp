package seedu.address.model.person;

public class Level {
    public static final String MESSAGE_CONSTRAINTS = "Levels should only be P1, P2, P3, P4, P5 or P6";

    private final LevelEnum internalLevel;

    public Level(String level) {
        this.internalLevel = LevelEnum.valueOf(level);
    }

    public Level() {
        this.internalLevel = null;
    }

    public String toString() {
        return internalLevel.toString();
    }

    public static boolean isValidLevel(String test) {
        try {
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
