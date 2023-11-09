package seedu.spendnsplit.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.spendnsplit.logic.commands.CommandTestUtil.assertTransactionCommandSuccess;
import static seedu.spendnsplit.testutil.TypicalSpendNSplitBook.getTypicalSpendNSplitBook;

import org.junit.jupiter.api.Test;

import seedu.spendnsplit.model.Model;
import seedu.spendnsplit.model.ModelManager;
import seedu.spendnsplit.model.SpendNSplit;
import seedu.spendnsplit.model.UserPrefs;


public class SortPersonCommandTest {

    private Model model = new ModelManager(getTypicalSpendNSplitBook(), new UserPrefs());

    @Test
    public void execute() {
        Model expectedModel = new ModelManager(new SpendNSplit(model.getSpendNSplitBook()), new UserPrefs());
        SortPersonCommand cmd = new SortPersonCommand(false);
        String expectedMessage = String.format(SortPersonCommand.MESSAGE_SORT_PERSON_SUCCESS, "ascending");
        expectedModel.sortPersonAscending();
        assertTransactionCommandSuccess(cmd, model, expectedMessage, expectedModel);
        cmd = new SortPersonCommand(true);
        expectedMessage = String.format(SortPersonCommand.MESSAGE_SORT_PERSON_SUCCESS, "descending");
        expectedModel.sortPersonDescending();
        assertTransactionCommandSuccess(cmd, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        SortPersonCommand sortDescCommand = new SortPersonCommand(true);
        SortPersonCommand sortDescCommandCopy = new SortPersonCommand(true);
        SortPersonCommand sortAscCommand = new SortPersonCommand(false);

        // same object -> returns true
        assertEquals(sortDescCommand, sortDescCommand);

        // same values -> returns true;
        assertEquals(sortDescCommand, sortDescCommandCopy);

        // different types -> returns false
        assertNotEquals(sortDescCommand, 1);

        // null -> returns false
        assertNotEquals(sortDescCommand, null);

        // different isDesc -> returns false
        assertNotEquals(sortDescCommand, sortAscCommand);
    }

    @Test
    public void toStringMethod() {
        SortPersonCommand sortPersonCommand = new SortPersonCommand(true);
        String expected = SortPersonCommand.class.getCanonicalName() + "{isDesc=true}";
        assertEquals(expected, sortPersonCommand.toString());
    }
}
