package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.DayOfWeek;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.AppointmentIsDayOfWeekPredicate;


/**
 * Contains integration tests (interaction with the Model) and unit tests for ViewAppointmentsCommand.
 */
public class ViewAppointmentsCommandTest {


    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    // Test modified to account for revised Appointment command where contact name is shown
    @Test
    public void execute_viewAppointments_success() {
        String expectedMessage = "Appointments:\n"
                + "Benson Meier: 12:00-13:00 SUN";
        assertCommandSuccess(new ViewAppointmentsCommand(
                new AppointmentIsDayOfWeekPredicate(List.of(DayOfWeek.SUNDAY))), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_viewAppointmentsWithPredicate_success() {
        String expectedMessage = "Appointments:\n"
                + "There are no appointments to show!";
        assertCommandSuccess(new ViewAppointmentsCommand(
                new AppointmentIsDayOfWeekPredicate(List.of(DayOfWeek.MONDAY))), model, expectedMessage, expectedModel);
    }

}
