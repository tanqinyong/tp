package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewAppointmentsCommand;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentIsDayOfWeekPredicate;

public class ViewAppointmentsCommandParserTest {

    private ViewAppointmentsCommandParser parser = new ViewAppointmentsCommandParser();

    @Test
    public void parse_emptyArg_returnsViewAppointmentsCommand() {
        ViewAppointmentsCommand expectedViewAppointmentsCommand =
                new ViewAppointmentsCommand(new AppointmentIsDayOfWeekPredicate(
                        new ArrayList<>(Appointment.DAY_OF_WEEK_TO_NUM.keySet())));
        // whitespace
        assertParseSuccess(parser, "       ", expectedViewAppointmentsCommand);

        // weird whitespace
        assertParseSuccess(parser, " \n   \t", expectedViewAppointmentsCommand);
    }

    @Test
    public void parse_validArgs_returnsViewAppointmentsCommand() {
        // no leading and trailing whitespaces
        ViewAppointmentsCommand expectedViewAppointmentsCommand =
                new ViewAppointmentsCommand(new AppointmentIsDayOfWeekPredicate(
                        Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY)));
        assertParseSuccess(parser, "mon Tue", expectedViewAppointmentsCommand);

        // multiple whitespaces between days
        assertParseSuccess(parser, " \n mon \n \t Tue  \t", expectedViewAppointmentsCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // not days
        assertParseFailure(parser, "mon Tues",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewAppointmentsCommand.MESSAGE_USAGE));
    }

}
