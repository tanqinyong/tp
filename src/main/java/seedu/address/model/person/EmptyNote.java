package seedu.address.model.person;

/**
 * An Empty Note class, for use when no field is listed in a person's note during creation
 */
public class EmptyNote extends Note {
    public EmptyNote() {
        super();
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other instanceof EmptyNote) {
            return true;
        }

        return false;
    }
}
