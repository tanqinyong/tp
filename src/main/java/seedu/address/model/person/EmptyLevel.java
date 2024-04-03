package seedu.address.model.person;

/**
 * An Empty Level class, for use when no field is listed in a person's level during creation
 */
public class EmptyLevel extends Level {
    public EmptyLevel() {
        super();
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other instanceof EmptyLevel) {
            return true;
        }

        return false;
    }
}
