package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collection;
import java.util.Iterator;

import seedu.address.model.appointment.exceptions.AppointmentNotFoundException;
import seedu.address.model.appointment.exceptions.OverlappingAppointmentException;

/**
 * A list of appointments that enforces no overlapping between its elements and does not allow nulls.
 * Supports a minimal set of list operations.
 */
public class DisjointAppointmentList extends AppointmentList {
    public static final String MESSAGE_CONSTRAINTS =
            "This person's appointments clash with an existing appointment";

    /**
     * Returns true if the list contains an appointment overlapping wth the given argument.
     */
    public boolean overlaps(Appointment toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::overlapsWith);
    }

    @Override
    public boolean isOverlapping() {
        return false;
    }

    /**
     * Adds an appointment {@code toAdd} to the list.
     * This appointment must not overlap with existing appointments in the list.
     */
    @Override
    public void add(Appointment toAdd) {
        requireNonNull(toAdd);
        if (overlaps(toAdd)) {
            throw new OverlappingAppointmentException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the appointment {@code target} in the list with {@code editedAppointment}.
     * {@code target} must exist in the appointment list.
     * {@code editedAppointment} must not overlap with another existing appointment in the list.
     */
    @Override
    public void setAppointment(Appointment target, Appointment editedAppointment) {
        requireAllNonNull(target, editedAppointment);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new AppointmentNotFoundException();
        }

        if (overlaps(editedAppointment)) {
            throw new OverlappingAppointmentException();
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
    public void setAppointments(DisjointAppointmentList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code appointments}.
     * {@code appointments} must not contain overlapping appointments.
     */
    @Override
    public void setAppointments(Collection<Appointment> appointments) {
        requireAllNonNull(appointments);
        if (Appointment.hasOverlapping(appointments)) {
            throw new OverlappingAppointmentException();
        }

        internalList.setAll(appointments);
    }

    /**
     * The list {@code appointments} must not have any appointments that overlap with existing appointments
     * and also overlap with each other.
     */
    public void addAll(Collection<Appointment> appointments) {
        if (Appointment.hasOverlapping(appointments)) {
            throw new OverlappingAppointmentException();
        }
        for (Appointment ap : appointments) {
            if (this.overlaps(ap)) {
                throw new OverlappingAppointmentException();
            }
        }
        internalList.addAll(appointments);
    }

    public boolean isEmpty() {
        return internalList.isEmpty();
    }

    @Override
    public Iterator<Appointment> iterator() {
        return internalList.iterator();
    }
}
