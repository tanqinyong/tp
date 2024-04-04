package seedu.address.model.appointment;

import java.time.DayOfWeek;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class AppointmentIsDayOfWeekPredicate implements Predicate<Appointment> {
    private final List<DayOfWeek> days;

    public AppointmentIsDayOfWeekPredicate(List<DayOfWeek> dayOfWeeks) {
        this.days = dayOfWeeks;
    }

    @Override
    public boolean test(Appointment appointment) {
        return days.stream().anyMatch(day -> day.equals(appointment.getDay()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof seedu.address.model.appointment.AppointmentIsDayOfWeekPredicate)) {
            return false;
        }

        AppointmentIsDayOfWeekPredicate otherPredicate = (AppointmentIsDayOfWeekPredicate) other;
        return this.days.equals(otherPredicate.days);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("days", days).toString();
    }
}
