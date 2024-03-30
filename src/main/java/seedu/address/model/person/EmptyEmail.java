package seedu.address.model.person;

/**
 * An Empty Email class, for use when no field is listed in a person's email during creation
 */
public class EmptyEmail extends Email {
    public EmptyEmail() {
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

        if (other instanceof EmptyEmail) {
            return true;
        }

        return false;
    }
}
