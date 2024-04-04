package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.ViewAppointmentsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentIsDayOfWeekPredicate;

/**
 * Parses input arguments and creates a new ViewAppointmentsCommand object.
 * Code is borrowed from existing ViewCommandParser.java due to similarities in desired implementation.
 */
public class ViewAppointmentsCommandParser implements Parser<ViewAppointmentsCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of ViewAppointmentsCommand and returns
     * a ViewAppointmentsCommand object for execution.
     * @throws ParseException if the user input does not conform to expected format.
     */
    public ViewAppointmentsCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim().toUpperCase();
        if (trimmedArgs.isEmpty()) {

            // add all days of the week to the list
            List<DayOfWeek> defaultDayOfWeeks = new ArrayList<>(Appointment.DAY_OF_WEEK_TO_NUM.keySet());
            return new ViewAppointmentsCommand(new AppointmentIsDayOfWeekPredicate(defaultDayOfWeeks));
        }

        String[] days = trimmedArgs.split("\\s+");
        List<String> dayList = Stream.of(days).map(String::toUpperCase).collect(Collectors.toList());

        // check that all day in dayList are valid day of the week
        for (String day : dayList) {
            if (!Appointment.DAY_TO_DAY_OF_WEEK.containsKey(day)) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewAppointmentsCommand.MESSAGE_USAGE));
            }
        }

        List<DayOfWeek> dayOfWeekList = dayList.stream()
                .map(Appointment.DAY_TO_DAY_OF_WEEK::get)
                .distinct()
                .collect(Collectors.toList());

        return new ViewAppointmentsCommand(new AppointmentIsDayOfWeekPredicate(dayOfWeekList));
    }
}
