package seedu.address.model.person;

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
}
