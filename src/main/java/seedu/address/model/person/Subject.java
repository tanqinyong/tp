package seedu.address.model.person;

/**
 * Represents a Person's subject in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSubject(String)}
 */
public class Subject {

    public static final String MESSAGE_CONSTRAINTS = "Subjects should only be ENGLISH, MATH, or SCIENCE";
    private final SubjectEnum internalSubject;

    public Subject(String subject) {
        this.internalSubject = SubjectEnum.valueOf(subject);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + internalSubject.toString() + ']';
    }

    public String getSubject() {
        return internalSubject.toString();
    }

    /**
     * Returns true if a given string is a valid subject.
     */
    public static boolean isValidSubject(String test) {
        try {
            SubjectEnum.valueOf(test);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return internalSubject.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Subject)) {
            return false;
        }

        Subject otherSubject = (Subject) other;
        return internalSubject.equals(otherSubject.internalSubject);
    }
}
