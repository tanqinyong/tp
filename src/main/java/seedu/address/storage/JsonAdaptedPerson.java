package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmptyAddress;
import seedu.address.model.person.EmptyEmail;
import seedu.address.model.person.EmptyLevel;
import seedu.address.model.person.EmptyNote;
import seedu.address.model.person.EmptyPhone;
import seedu.address.model.person.Level;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Subject;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String note;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final List<JsonAdaptedAppointment> appointments = new ArrayList<>();
    private final List<JsonAdaptedSubject> subjects = new ArrayList<>();
    private final String level;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("note") String note, @JsonProperty("tags") List<JsonAdaptedTag> tags,
            @JsonProperty("appointments") List<JsonAdaptedAppointment> appointments,
            @JsonProperty("subjects") List<JsonAdaptedSubject> subjects, @JsonProperty("level") String level) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.note = note;
        this.level = level;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        if (appointments != null) {
            this.appointments.addAll(appointments);
        }
        if (subjects != null) {
            this.subjects.addAll(subjects);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        note = source.getNote().value;
        level = source.getLevel().toString();
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        appointments.addAll(source.getAppointments().stream()
                .map(JsonAdaptedAppointment::new)
                .collect(Collectors.toList()));
        subjects.addAll(source.getSubjects().stream()
                .map(JsonAdaptedSubject::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        final List<Appointment> personAppointments = new ArrayList<>();
        final List<Subject> personSubjects = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }
        for (JsonAdaptedAppointment appointment : appointments) {
            personAppointments.add(appointment.toModelType());
        }
        for (JsonAdaptedSubject subject : subjects) {
            personSubjects.add(subject.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        Phone usedPhone;
        if (phone == null) {
            usedPhone = new EmptyPhone();
        } else if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        } else {
            usedPhone = new Phone(phone);
        }
        final Phone modelPhone = usedPhone;

        Email usedEmail;
        if (email == null) {
            usedEmail = new EmptyEmail();
        } else if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        } else {
            usedEmail = new Email(email);
        }
        final Email modelEmail = usedEmail;

        Address usedAddress;
        if (address == null) {
            usedAddress = new EmptyAddress();
        } else if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        } else {
            usedAddress = new Address(address);
        }
        final Address modelAddress = usedAddress;

        Note usedNote;
        if (note == null) {
            usedNote = new EmptyNote();
        } else {
            usedNote = new Note(note);
        }
        final Note modelNote = usedNote;

        Level usedLevel;
        if (level == null) {
            usedLevel = new EmptyLevel();
        } else {
            usedLevel = new Level(level);
        }
        final Level modelLevel = usedLevel;

        final Set<Tag> modelTags = new HashSet<>(personTags);

        final Set<Appointment> modelAppointments = new HashSet<>(personAppointments);

        final Set<Subject> modelSubjects = new HashSet<>(personSubjects);

        return new Person(modelName, modelPhone, modelEmail, modelAddress,
        modelNote, modelTags, modelAppointments, modelSubjects, modelLevel);
    }

}
