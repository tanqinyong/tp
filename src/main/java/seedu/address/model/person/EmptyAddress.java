package seedu.address.model.person;

/**
 * An Empty Address class, for use when no field is listed in a person's address during creation
 */
public class EmptyAddress extends Address {
    public EmptyAddress() {
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

        if (other instanceof EmptyAddress) {
            return true;
        }

        return false;
    }
}
