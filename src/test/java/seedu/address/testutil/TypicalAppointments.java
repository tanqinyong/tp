package seedu.address.testutil;

import seedu.address.model.appointment.Appointment;

/**
 * A utility class containing a list of {@code Appointment} objects to be used in tests.
 */
public class TypicalAppointments {
    public static final Appointment SUN_APPOINTMENT_10_to_12 = new Appointment("10:00-12:00 SUN");
    public static final Appointment SUN_APPOINTMENT_11_to_13 = new Appointment("11:00-13:00 SUN");
    public static final Appointment SAT_APPOINTMENT_10_to_12 = new Appointment("10:00-12:00 SAT");
    public static final Appointment FRI_APPOINTMENT_10_to_12 = new Appointment("10:00-12:00 FRI");
}
