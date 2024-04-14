package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.appointment.exceptions.AppointmentNotFoundException;

/**
 * A list of appointments that does not allow nulls.
 * Supports a minimal set of list operations.
 */
public class AppointmentList implements Iterable<Appointment> {
    protected final ObservableList<Appointment> internalList = FXCollections.observableArrayList();
    protected final ObservableList<Appointment> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Adds an appointment to the list.
     * The appointment must not already exist in the list.
     */
    public void add(Appointment toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Replaces the appointment {@code target} in the list with {@code editedAppointment}.
     * {@code target} must exist in the appointment list.
     */
    public void setAppointment(Appointment target, Appointment editedAppointment) {
        requireAllNonNull(target, editedAppointment);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new AppointmentNotFoundException();
        }

        internalList.set(index, editedAppointment);
    }

    /**
     * Removes the equivalent appointment from the list.
     * The appointment must exist in the list.
     */
    public void remove(Appointment toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new AppointmentNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code appointments}.
     */
    public void setAppointments(AppointmentList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code appointments}.
     */
    public void setAppointments(Collection<Appointment> appointments) {
        requireAllNonNull(appointments);
        internalList.setAll(appointments);
    }

    /**
     * Sorts the appointment list by the appointment's natural comparator.
     */
    public void sort() {
        internalList.sort(null);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Appointment> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AppointmentList)) {
            return false;
        }

        AppointmentList otherAppointmentList = (AppointmentList) other;
        return internalList.equals(otherAppointmentList.internalList);
    }

    @Override
    public String toString() {
        List<String> appointmentStrings = internalList.stream()
                .map(Appointment::toString).collect(Collectors.toList());
        return String.join(", ", appointmentStrings);

    }

    /**
     * Returns true if the list contains an equivalent appointment as the given argument.
     */
    public boolean contains(Appointment toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Returns true if the list has overlap between appointments.
     */
    public boolean isOverlapping() {
        return Appointment.hasOverlapping(internalList);
    }

    /**
     * Add all appointments in {@code appointments} to the list.
     */
    public void addAll(Collection<Appointment> appointments) {
        internalList.addAll(appointments);
    }

    /**
     * Returns true if there are no appointments in the list.
     */
    public boolean isEmpty() {
        return internalList.isEmpty();
    }

    @Override
    public Iterator<Appointment> iterator() {
        return internalList.iterator();
    }
}
