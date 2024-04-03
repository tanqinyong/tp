package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Subject;
import seedu.address.model.tag.Tag;


/**
 * Jackson-friendly version of {@link seedu.address.model.person.Subject}.
 */
public class JsonAdaptedSubject {

    private final String subject;

    /**
     * Constructs a {@code JsonAdaptedSubject} with the given {@code subject}.
     */
    @JsonCreator
    public JsonAdaptedSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Converts a given {@code Subject} into this class for Jackson use.
     */
    public JsonAdaptedSubject(Subject source) {
        subject = source.getSubject();
    }

    @JsonValue
    public String getSubject() {
        return subject;
    }

    /**
     * Converts this Jackson-friendly adapted subject object into the model's {@code Subject} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted subject.
     */
    public Subject toModelType() throws IllegalValueException {
        if (!Subject.isValidSubject(subject)) {
            throw new IllegalValueException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Subject(subject);
    }
}
