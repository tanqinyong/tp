package seedu.address.model.person;

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

    public static boolean isValidSubject(String test) {
        try {
            SubjectEnum.valueOf(test);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
