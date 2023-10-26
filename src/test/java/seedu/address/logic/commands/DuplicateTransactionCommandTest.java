package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertTransactionCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showTransactionAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ELEMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ELEMENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.transaction.Transaction;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DuplicateCommand}.
 */
public class DuplicateTransactionCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Transaction transaction = model.getFilteredTransactionList().get(INDEX_FIRST_ELEMENT.getZeroBased());
        Transaction transactionToDuplicate = new Transaction(transaction.getAmount(), transaction.getDescription(),
            transaction.getPayeeName(), transaction.getPortions());
        DuplicateTransactionCommand duplicateCommand = new DuplicateTransactionCommand(INDEX_FIRST_ELEMENT);

        String expectedMessage = String.format(DuplicateTransactionCommand.MESSAGE_DUPLICATE_TRANSACTION_SUCCESS,
            Messages.format(transactionToDuplicate));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addTransaction(transactionToDuplicate);

        assertTransactionCommandSuccess(duplicateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTransactionList().size() + 1);
        DuplicateTransactionCommand duplicateCommand = new DuplicateTransactionCommand(outOfBoundIndex);

        assertCommandFailure(duplicateCommand, model, Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showTransactionAtIndex(model, INDEX_FIRST_ELEMENT);

        Transaction transaction = model.getFilteredTransactionList().get(INDEX_FIRST_ELEMENT.getZeroBased());
        Transaction transactionToDuplicate = new Transaction(transaction.getAmount(), transaction.getDescription(),
            transaction.getPayeeName(), transaction.getPortions());
        DuplicateTransactionCommand duplicateCommand = new DuplicateTransactionCommand(INDEX_FIRST_ELEMENT);

        String expectedMessage = String.format(DuplicateTransactionCommand.MESSAGE_DUPLICATE_TRANSACTION_SUCCESS,
            Messages.format(transactionToDuplicate));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addTransaction(transactionToDuplicate);

        assertTransactionCommandSuccess(duplicateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showTransactionAtIndex(model, INDEX_FIRST_ELEMENT);

        Index outOfBoundIndex = INDEX_SECOND_ELEMENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getTransactionList().size());

        DuplicateTransactionCommand duplicateCommand = new DuplicateTransactionCommand(outOfBoundIndex);

        assertCommandFailure(duplicateCommand, model, Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DuplicateTransactionCommand duplicateFirstCommand = new DuplicateTransactionCommand(INDEX_FIRST_ELEMENT);
        DuplicateTransactionCommand duplicateSecondCommand = new DuplicateTransactionCommand(INDEX_SECOND_ELEMENT);

        // same object -> returns true
        assertTrue(duplicateFirstCommand.equals(duplicateFirstCommand));

        // same values -> returns true
        DuplicateTransactionCommand duplicateFirstCommandCopy = new DuplicateTransactionCommand(INDEX_FIRST_ELEMENT);
        assertTrue(duplicateFirstCommand.equals(duplicateFirstCommandCopy));

        // different types -> returns false
        assertFalse(duplicateFirstCommand.equals(1));

        // null -> returns false
        assertFalse(duplicateFirstCommand.equals(null));

        // different transaction -> returns false
        assertFalse(duplicateFirstCommand.equals(duplicateSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DuplicateTransactionCommand duplicateCommand = new DuplicateTransactionCommand(targetIndex);
        String expected = DuplicateTransactionCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, duplicateCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoTransaction(Model model) {
        model.updateFilteredTransactionList(p -> false);

        assertTrue(model.getFilteredTransactionList().isEmpty());
    }
}
