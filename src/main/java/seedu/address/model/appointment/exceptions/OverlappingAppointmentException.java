package seedu.address.model.appointment.exceptions;

/**
 * Signals that the operation is unable to find the specified appointment.
 */
public class OverlappingAppointmentException extends RuntimeException {
    public OverlappingAppointmentException() {
        super("Operation would result in overlapping appointments");
    }
}


