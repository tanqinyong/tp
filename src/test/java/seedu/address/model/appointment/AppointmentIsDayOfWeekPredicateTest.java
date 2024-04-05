package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class AppointmentIsDayOfWeekPredicateTest {

    @Test
    public void equals() {
        List<DayOfWeek> firstPredicateDayOfWeekList = Collections.singletonList(DayOfWeek.MONDAY);
        List<DayOfWeek> secondPredicateDayOfWeekList = Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY);

        AppointmentIsDayOfWeekPredicate firstPredicate =
                new AppointmentIsDayOfWeekPredicate(firstPredicateDayOfWeekList);
        AppointmentIsDayOfWeekPredicate secondPredicate =
                new AppointmentIsDayOfWeekPredicate(secondPredicateDayOfWeekList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AppointmentIsDayOfWeekPredicate firstPredicateCopy =
                new AppointmentIsDayOfWeekPredicate(firstPredicateDayOfWeekList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different appointment -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_dayOfWeekMatches_returnsTrue() {
        // Single day match
        AppointmentIsDayOfWeekPredicate predicate =
                new AppointmentIsDayOfWeekPredicate(Collections.singletonList(DayOfWeek.MONDAY));
        assertTrue(predicate.test(new Appointment("09:00-10:00 MON")));

        // Multiple days match
        predicate = new AppointmentIsDayOfWeekPredicate(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY));
        assertTrue(predicate.test(new Appointment("09:00-10:00 TUE")));

        // Mixed-case keywords
        predicate = new AppointmentIsDayOfWeekPredicate(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY));
        assertTrue(predicate.test(new Appointment("09:00-10:00 mon")));
    }

    @Test
    public void test_dayOfWeekDoesNotMatch_returnsFalse() {
        // No day match
        AppointmentIsDayOfWeekPredicate predicate =
                new AppointmentIsDayOfWeekPredicate(Collections.singletonList(DayOfWeek.MONDAY));
        assertFalse(predicate.test(new Appointment("09:00-10:00 TUE")));

        // Different day match
        predicate = new AppointmentIsDayOfWeekPredicate(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY));
        assertFalse(predicate.test(new Appointment("09:00-10:00 TUE")));
    }

    @Test
    public void toStringMethod() {
        List<DayOfWeek> daysOfWeek = List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY);
        AppointmentIsDayOfWeekPredicate predicate = new AppointmentIsDayOfWeekPredicate(daysOfWeek);

        String expected = AppointmentIsDayOfWeekPredicate.class.getCanonicalName() + "{days=" + daysOfWeek + "}";
        assertEquals(expected, predicate.toString());
    }
}
