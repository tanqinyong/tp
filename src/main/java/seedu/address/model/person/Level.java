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
}
