package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.person.Subject;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private FlowPane summary;
    @FXML
    private FlowPane subjects;
    @FXML
    private FlowPane level;


    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().isEmpty() ? "-" : person.getPhone().value);
        email.setText(person.getEmail().isEmpty() ? "-" : person.getEmail().value);

        String level = person.getLevel().toString();
        summary.getChildren().add(createSummaryLabel(level, level));

        person.getSubjects().stream()
                .sorted(Comparator.comparing(Subject::getSubject))
                .forEach(subject -> summary.getChildren()
                        .add(createSummaryLabel(subject.getSubject(), subject.getSubject())));

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> summary.getChildren().add(createSummaryLabel(tag.tagName, "tag")));

    }

    /**
     * Returns a Label object that is used in the summary FlowPane containing the specified text content
     * and style class.
     */
    private Label createSummaryLabel(String content, String classToAdd) {
        Label customLabel = new Label(content);
        customLabel.getStyleClass().add(classToAdd);
        return customLabel;
    }
}
