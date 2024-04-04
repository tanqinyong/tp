package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.util.Pair;
import seedu.address.model.Model;
import seedu.address.model.appointment.AppointmentIsDayOfWeekPredicate;
import seedu.address.model.person.Person;

/**
 * Shows all appointments in the filtered address book.
 */
public class ViewAppointmentsCommand extends Command {

    public static final String COMMAND_WORD = "appointments";

    public static final String MESSAGE_SUCCESS = "Listed all appointments";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all appointments of persons displayed.\n"
            + "Example: " + COMMAND_WORD;

    private final AppointmentIsDayOfWeekPredicate predicate;

    public ViewAppointmentsCommand(AppointmentIsDayOfWeekPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        // Get all appointments from the last shown list of persons
        List<String> appointments = lastShownList.stream()
                .flatMap(person -> person.hasAppointments()
                        ? person.getAppointments()
                        .asUnmodifiableObservableList().stream()
                        .filter(predicate)
                        .map(appointment -> new Pair<>(appointment, person.getName().toString()))
                        : Stream.empty())
                .sorted((o1, o2) -> o1.getKey().compareTo(o2.getKey())) // comparing by appointment only
                .map(pair -> pair.getValue() + ": " + pair.getKey().toString())
                .collect(Collectors.toList());

        StringBuilder sb = new StringBuilder();
        sb.append("Appointments:\n");
        for (String appointment : appointments) {
            sb.append(appointment).append("\n");
        }

        if (appointments.isEmpty()) {
            sb.append("There are no appointments to show!");
        }

        return new CommandResult(sb.toString().trim());
    }
}
