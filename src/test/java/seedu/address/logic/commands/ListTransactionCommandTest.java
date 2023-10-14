package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.Messages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTransactions.DINNER;
import static seedu.address.testutil.TypicalTransactions.LUNCH;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.transaction.TransactionContainsPersonNamesPredicate;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListTransactionCommand.
 */
public class ListTransactionCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        // TODO: Add these typical transactions to getTypicalAddressBook()
        model.addTransaction(LUNCH);
        model.addTransaction(DINNER);
        expectedModel.addTransaction(LUNCH);
        expectedModel.addTransaction(DINNER);
    }

    @Test
    public void equals() {
        TransactionContainsPersonNamesPredicate firstPredicate =
            new TransactionContainsPersonNamesPredicate(Collections.singletonList(new Name("first")));
        TransactionContainsPersonNamesPredicate secondPredicate =
            new TransactionContainsPersonNamesPredicate(Collections.singletonList(new Name("second")));

        ListTransactionCommand listFirstCommand = new ListTransactionCommand(firstPredicate);
        ListTransactionCommand listSecondCommand = new ListTransactionCommand(secondPredicate);

        // same object -> returns true
        assertEquals(listFirstCommand, listFirstCommand);

        // same values -> returns true
        ListTransactionCommand listFirstCommandCopy = new ListTransactionCommand(firstPredicate);
        assertEquals(listFirstCommand, listFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, listFirstCommand);

        // null -> returns false
        assertNotEquals(null, listFirstCommand);

        // different person name -> returns false
        assertNotEquals(listFirstCommand, listSecondCommand);
    }

    @Test
    public void execute_zeroNames_allTransactionsFound() {
        String expectedMessage = String.format(MESSAGE_TRANSACTIONS_LISTED_OVERVIEW, 2);
        TransactionContainsPersonNamesPredicate predicate =
            new TransactionContainsPersonNamesPredicate(Collections.emptyList());
        assertCommandSuccess(new ListTransactionCommand(predicate), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleNames_multipleTransactionsFound() {
        String expectedMessage = String.format(MESSAGE_TRANSACTIONS_LISTED_OVERVIEW, 1);
        TransactionContainsPersonNamesPredicate predicate =
            new TransactionContainsPersonNamesPredicate(Arrays.asList(new Name(VALID_NAME_BOB), new Name("Carl")));
        expectedModel.updateFilteredTransactionList(predicate);
        assertCommandSuccess(new ListTransactionCommand(predicate), model, expectedMessage, expectedModel);
    }

    @Test
    public void toStringMethod() {
        TransactionContainsPersonNamesPredicate predicate =
            new TransactionContainsPersonNamesPredicate(Arrays.asList(new Name("Bob"), new Name("Carl")));
        ListTransactionCommand listTransactionCommand = new ListTransactionCommand(predicate);
        String expected = ListTransactionCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, listTransactionCommand.toString());
    }
}
