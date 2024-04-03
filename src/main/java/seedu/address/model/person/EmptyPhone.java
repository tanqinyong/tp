package seedu.address.model.person;

/**
 * An Empty Phone class, for use when no field is listed in a person's phone during creation
 */
public class EmptyPhone extends Phone {
    public EmptyPhone() {
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

        if (other instanceof EmptyPhone) {
            return true;
        }

        return false;
    }
}
