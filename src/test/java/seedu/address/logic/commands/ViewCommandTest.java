package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code ViewCommand}.
 *
 * Code borrows heavily from DeleteCommandTest.java due to similarity in functionality between
 * DeleteCommand and ViewCommand.
 */
public class ViewCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        List<String> expected = new ArrayList<>();
        expected.add("ALICE PAULINE\n");
        expected.add("\n[P1] [friends]\n");
        expected.add(StringUtil.SEPARATOR);
        expected.add("\nDETAILS:\n");
        expected.add("94351253\n");
        expected.add("alice@example.com\n");
        expected.add("123, Jurong West Ave 6, #08-111\n");
        expected.add(StringUtil.SEPARATOR);
        expected.add("\nAPPOINTMENTS:\n");
        expected.add("-\n");
        expected.add(StringUtil.SEPARATOR);
        expected.add("\nNOTES:\nShe likes aardvarks.");

        StringBuilder sb = new StringBuilder();
        for (String str : expected) {
            sb.append(str);
        }
        String expectedResult = sb.toString().trim();

        assertCommandSuccess(new ViewCommand(INDEX_FIRST_PERSON), model, expectedResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ViewCommand viewCommand = new ViewCommand(outOfBoundIndex);

        assertCommandFailure(viewCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        showPersonAtIndex(expectedModel, INDEX_SECOND_PERSON);

        List<String> expected = new ArrayList<>();
        expected.add("BENSON MEIER\n");
        expected.add("\n[P1] [MATH] [owesMoney] [friends]\n");
        expected.add(StringUtil.SEPARATOR);
        expected.add("\nDETAILS:\n");
        expected.add("98765432\n");
        expected.add("johnd@example.com\n");
        expected.add("311, Clementi Ave 2, #02-25\n");
        expected.add(StringUtil.SEPARATOR);
        expected.add("\nAPPOINTMENTS:\n");
        expected.add("12:00-13:00 SUN\n");
        expected.add(StringUtil.SEPARATOR);
        expected.add("\nNOTES:\nHe can't take beer!");

        StringBuilder sb = new StringBuilder();
        for (String str : expected) {
            sb.append(str);
        }
        String expectedResult = sb.toString().trim();

        assertCommandSuccess(new ViewCommand(INDEX_FIRST_PERSON), model, expectedResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        ViewCommand viewCommand = new ViewCommand(outOfBoundIndex);

        assertCommandFailure(viewCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ViewCommand viewFirstCommand = new ViewCommand(INDEX_FIRST_PERSON);
        ViewCommand viewSecondCommand = new ViewCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values -> returns true
        ViewCommand viewFirstCommandCopy = new ViewCommand(INDEX_FIRST_PERSON);
        assertTrue(viewFirstCommand.equals(viewFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(viewFirstCommand.equals(viewSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        ViewCommand viewCommand = new ViewCommand(targetIndex);
        String expected = ViewCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, viewCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
