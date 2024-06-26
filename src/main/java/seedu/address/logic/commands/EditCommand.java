package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.AppointmentList;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Level;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Subject;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_NOTE + "NOTE]"
            + "[" + PREFIX_APPOINTMENT + "APPOINTMENT]"
            + "[" + PREFIX_TAG + "TAG] ...\n"
            + "[" + PREFIX_SUBJECT + "SUBJECT] "
            + "[" + PREFIX_LEVEL + "LEVEL]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_NEAR_DUPLICATES = "Edited Person: %1$s \nPossible duplicate contacts: %2$s";
    public static final String MESSAGE_OVERLAPPING_APPOINTMENT =
            "This person's appointments clash with an existing appointment";
    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        // Duplicate Detection feature
        List<String> duplicateNames = model.findNearDuplicates(editedPerson);

        // Overlapping appointment detection
        if (editedPerson.getAppointments().isOverlapping()) {
            throw new CommandException(MESSAGE_OVERLAPPING_APPOINTMENT);
        }

        AppointmentList editedAppointmentList = new AppointmentList();
        editedAppointmentList.setAppointments(editedPerson.getAppointments());
        editedAppointmentList.addAll(model.getFilteredAppointmentList()
                .stream()
                .filter(appointment -> !(personToEdit.getAppointments().contains(appointment)))
                .collect(Collectors.toList()));

        if (editedAppointmentList.isOverlapping()) {
            throw new CommandException(MESSAGE_OVERLAPPING_APPOINTMENT);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // If there are near duplicate names
        if (!duplicateNames.isEmpty()) {
            // remove the old name as it will be detected
            duplicateNames.remove(personToEdit.getName().toString());
            return new CommandResult(String.format(MESSAGE_NEAR_DUPLICATES,
                    Messages.format(editedPerson),
                    String.join(", ", duplicateNames)));
        }

        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Note updatedNote = editPersonDescriptor.getNote().orElse(personToEdit.getNote());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());
        AppointmentList updatedAppointments = editPersonDescriptor
                .getAppointments().orElse(personToEdit.getAppointments());
        Set<Subject> updatedSubjects = editPersonDescriptor.getSubjects().orElse(personToEdit.getSubjects());
        Level updatedLevel = editPersonDescriptor.getLevel().orElse(personToEdit.getLevel());

        return new Person(
            updatedName, updatedPhone, updatedEmail, updatedAddress, updatedNote, updatedTags,
            updatedAppointments, updatedSubjects, updatedLevel
            );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Note note;
        private Set<Tag> tags;
        private AppointmentList appointments;
        private Set<Subject> subjects;
        private Level level;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setNote(toCopy.note);
            setTags(toCopy.tags);
            setAppointments(toCopy.appointments);
            setSubjects(toCopy.subjects);
            setLevel(toCopy.level);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, note, tags, appointments, subjects, level);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setNote(Note note) {
            this.note = note;
        }

        public Optional<Note> getNote() {
            return Optional.ofNullable(note);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }
        /**
         * Sets {@code appointments} to this object's {@code appointments}.
         * A defensive copy of {@code appointments} is used internally.
         */
        public void setAppointments(AppointmentList appointments) {
            if (appointments == null) {
                this.appointments = null;
                return;
            }
            this.appointments = new AppointmentList();
            this.appointments.addAll(appointments.asUnmodifiableObservableList());
        }
        /**
         * Returns an unmodifiable appointment set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code appointments} is null.
         */
        public Optional<AppointmentList> getAppointments() {
            if (appointments == null) {
                return Optional.empty();
            }
            AppointmentList defensiveCopy = new AppointmentList();
            defensiveCopy.addAll(appointments.asUnmodifiableObservableList());
            return Optional.of(defensiveCopy);
        }

        /**
         * Sets {@code subjects} to this object's {@code subjects}.
         * A defensive copy of {@code subjects} is used internally.
         */
        public void setSubjects(Set<Subject> subjects) {
            this.subjects = (subjects != null) ? new HashSet<>(subjects) : null;
        }

        /**
         * Returns an unmodifiable subject set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code subjects} is null.
         */
        public Optional<Set<Subject>> getSubjects() {
            return (subjects != null) ? Optional.of(Collections.unmodifiableSet(subjects)) : Optional.empty();
        }

        /**
         * Sets {@code level} to this object's {@code level}.
         */
        public void setLevel(Level level) {
            this.level = level;
        }

        public Optional<Level> getLevel() {
            return Optional.ofNullable(level);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            EditPersonDescriptor otherEditPersonDescriptor = (EditPersonDescriptor) other;
            return Objects.equals(name, otherEditPersonDescriptor.name)
                    && Objects.equals(phone, otherEditPersonDescriptor.phone)
                    && Objects.equals(email, otherEditPersonDescriptor.email)
                    && Objects.equals(address, otherEditPersonDescriptor.address)
                    && Objects.equals(note, otherEditPersonDescriptor.note)
                    && Objects.equals(tags, otherEditPersonDescriptor.tags)
                    && Objects.equals(appointments, otherEditPersonDescriptor.appointments)
                    && Objects.equals(subjects, otherEditPersonDescriptor.subjects)
                    && Objects.equals(level, otherEditPersonDescriptor.level);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("note", note)
                    .add("appointments", appointments)
                    .add("tags", tags)
                    .add("subjects", subjects)
                    .add("level", level)
                    .toString();
        }
    }
}
