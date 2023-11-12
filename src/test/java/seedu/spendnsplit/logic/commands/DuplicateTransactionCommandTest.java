package seedu.spendnsplit.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.spendnsplit.logic.commands.CommandTestUtil.DESC_DINNER;
import static seedu.spendnsplit.logic.commands.CommandTestUtil.DESC_LUNCH;
import static seedu.spendnsplit.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.spendnsplit.logic.commands.CommandTestUtil.assertTransactionCommandSuccess;
import static seedu.spendnsplit.logic.commands.CommandTestUtil.showTransactionAtIndex;
import static seedu.spendnsplit.logic.commands.DuplicateTransactionCommand.MESSAGE_TRANSACTION_NOT_RELEVANT;
import static seedu.spendnsplit.testutil.TypicalIndexes.INDEX_FIRST_ELEMENT;
import static seedu.spendnsplit.testutil.TypicalIndexes.INDEX_SECOND_ELEMENT;
import static seedu.spendnsplit.testutil.TypicalSpendNSplitBook.getTypicalSpendNSplitBook;

import org.junit.jupiter.api.Test;

import seedu.spendnsplit.commons.core.index.Index;
import seedu.spendnsplit.logic.Messages;
import seedu.spendnsplit.logic.descriptors.TransactionDescriptor;
import seedu.spendnsplit.model.Model;
import seedu.spendnsplit.model.ModelManager;
import seedu.spendnsplit.model.SpendNSplit;
import seedu.spendnsplit.model.UserPrefs;
import seedu.spendnsplit.model.transaction.Transaction;
import seedu.spendnsplit.testutil.TransactionBuilder;
import seedu.spendnsplit.testutil.TransactionDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DuplicateCommand}.
 */
public class DuplicateTransactionCommandTest {

    private Model model = new ModelManager(getTypicalSpendNSplitBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Transaction originalTransaction = model.getFilteredTransactionList().get(0);
        Transaction duplicateTransaction = new TransactionBuilder()
            .withPayeeName(originalTransaction.getPayeeName().toString())
            .withTimestamp(originalTransaction.getTimestamp().toString())
            .withPortions(originalTransaction.getPortions()).build();
        TransactionDescriptor descriptor = new TransactionDescriptorBuilder(duplicateTransaction).build();
        DuplicateTransactionCommand duplicateTransactionCommand =
            new DuplicateTransactionCommand(INDEX_FIRST_ELEMENT, descriptor);

        String expectedMessage = String.format(
            DuplicateTransactionCommand.MESSAGE_DUPLICATE_TRANSACTION_SUCCESS, Messages.format(duplicateTransaction));
        Model expectedModel = new ModelManager(new SpendNSplit(model.getSpendNSplitBook()), new UserPrefs());

        expectedModel.addTransaction(duplicateTransaction);

        assertTransactionCommandSuccess(duplicateTransactionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_descriptionFieldSpecifiedUnfilteredList_success() {
        Transaction firstTransaction = model.getFilteredTransactionList().get(INDEX_FIRST_ELEMENT.getZeroBased());
        String descriptionString = "A New Dinner";

        TransactionBuilder transactionInList = new TransactionBuilder(firstTransaction);
        Transaction duplicateTransaction = transactionInList.withDescription(descriptionString).build();

        TransactionDescriptor descriptor = new TransactionDescriptorBuilder()
            .withDescription(descriptionString).build();
        DuplicateTransactionCommand duplicateTransactionCommand =
            new DuplicateTransactionCommand(INDEX_FIRST_ELEMENT, descriptor);

        String expectedMessage = String.format(
            DuplicateTransactionCommand.MESSAGE_DUPLICATE_TRANSACTION_SUCCESS, Messages.format(duplicateTransaction));
        Model expectedModel = new ModelManager(new SpendNSplit(model.getSpendNSplitBook()), new UserPrefs());

        expectedModel.addTransaction(duplicateTransaction);

        assertTransactionCommandSuccess(duplicateTransactionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_costFieldSpecifiedUnfilteredList_success() {
        Transaction firstTransaction = model.getFilteredTransactionList().get(INDEX_FIRST_ELEMENT.getZeroBased());
        String costString = "123.21";

        TransactionBuilder transactionInList = new TransactionBuilder(firstTransaction);
        Transaction duplicateTransaction = transactionInList.withAmount(costString).build();

        TransactionDescriptor descriptor = new TransactionDescriptorBuilder()
            .withAmount(costString).build();
        DuplicateTransactionCommand duplicateTransactionCommand =
            new DuplicateTransactionCommand(INDEX_FIRST_ELEMENT, descriptor);

        String expectedMessage = String.format(
            DuplicateTransactionCommand.MESSAGE_DUPLICATE_TRANSACTION_SUCCESS, Messages.format(duplicateTransaction));
        Model expectedModel = new ModelManager(new SpendNSplit(model.getSpendNSplitBook()), new UserPrefs());

        expectedModel.addTransaction(duplicateTransaction);

        assertTransactionCommandSuccess(duplicateTransactionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_timestampFieldSpecifiedUnfilteredList_success() {
        Transaction firstTransaction = model.getFilteredTransactionList().get(INDEX_FIRST_ELEMENT.getZeroBased());
        String timestampString = "10/10/2020 10:10";

        TransactionBuilder transactionInList = new TransactionBuilder(firstTransaction);
        Transaction duplicateTransaction = transactionInList.withTimestamp(timestampString).build();

        TransactionDescriptor descriptor = new TransactionDescriptorBuilder()
            .withTimestamp(timestampString).build();
        DuplicateTransactionCommand duplicateTransactionCommand =
            new DuplicateTransactionCommand(INDEX_FIRST_ELEMENT, descriptor);

        String expectedMessage = String.format(
            DuplicateTransactionCommand.MESSAGE_DUPLICATE_TRANSACTION_SUCCESS, Messages.format(duplicateTransaction));
        Model expectedModel = new ModelManager(new SpendNSplit(model.getSpendNSplitBook()), new UserPrefs());

        expectedModel.addTransaction(duplicateTransaction);

        assertTransactionCommandSuccess(duplicateTransactionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateTransaction_failure() {
        DuplicateTransactionCommand duplicateTransactionCommand = new DuplicateTransactionCommand(INDEX_FIRST_ELEMENT,
            new TransactionDescriptor());

        String expectedMessage = DuplicateTransactionCommand.MESSAGE_DUPLICATE_TRANSACTION;

        assertCommandFailure(duplicateTransactionCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidTransactionUnfilteredList_failure() {
        TransactionDescriptor descriptor = new TransactionDescriptorBuilder()
            .withPayeeName("Alice").build();
        DuplicateTransactionCommand duplicateTransactionCommand =
            new DuplicateTransactionCommand(INDEX_FIRST_ELEMENT, descriptor);

        assertCommandFailure(duplicateTransactionCommand, model, MESSAGE_TRANSACTION_NOT_RELEVANT);

        duplicateTransactionCommand =
            new DuplicateTransactionCommand(INDEX_SECOND_ELEMENT, descriptor);
        assertCommandFailure(duplicateTransactionCommand, model, DuplicateTransactionCommand.MESSAGE_UNKNOWN_PAYEE);
    }

    @Test
    public void execute_filteredList_success() {
        showTransactionAtIndex(model, INDEX_FIRST_ELEMENT);

        Transaction transactionInFilteredList = model.getFilteredTransactionList()
            .get(INDEX_FIRST_ELEMENT.getZeroBased());
        Transaction duplicateTransaction = new TransactionBuilder(transactionInFilteredList)
            .withAmount("123.21").build();
        DuplicateTransactionCommand duplicateTransactionCommand = new DuplicateTransactionCommand(INDEX_FIRST_ELEMENT,
            new TransactionDescriptorBuilder().withAmount("123.21").build());

        String expectedMessage = String.format(
            DuplicateTransactionCommand.MESSAGE_DUPLICATE_TRANSACTION_SUCCESS, Messages.format(duplicateTransaction));

        Model expectedModel = new ModelManager(new SpendNSplit(model.getSpendNSplitBook()), new UserPrefs());
        expectedModel.addTransaction(duplicateTransaction);

        assertTransactionCommandSuccess(duplicateTransactionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTransactionIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTransactionList().size() + 1);
        TransactionDescriptor descriptor = new TransactionDescriptorBuilder()
            .withAmount("123.21").build();
        DuplicateTransactionCommand duplicateTransactionCommand =
            new DuplicateTransactionCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(duplicateTransactionCommand, model, Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    /**
     * Duplicate filtered list where index is larger than size of filtered list,
     * but smaller than size of spendnsplit book
     */
    @Test
    public void execute_invalidTransactionIndexFilteredList_failure() {
        showTransactionAtIndex(model, INDEX_FIRST_ELEMENT);
        Index outOfBoundIndex = INDEX_SECOND_ELEMENT;
        // ensures that outOfBoundIndex is still in bounds of spendnsplit list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getSpendNSplitBook().getTransactionList().size());

        DuplicateTransactionCommand duplicateTransactionCommand = new DuplicateTransactionCommand(outOfBoundIndex,
            new TransactionDescriptorBuilder().withAmount("123.21").build());

        assertCommandFailure(duplicateTransactionCommand, model, Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final DuplicateTransactionCommand standardCommand = new DuplicateTransactionCommand(
            INDEX_FIRST_ELEMENT, DESC_LUNCH);

        // same values -> returns true
        TransactionDescriptor copyDescriptor = new TransactionDescriptor(DESC_LUNCH);
        DuplicateTransactionCommand commandWithSameValues = new DuplicateTransactionCommand(INDEX_FIRST_ELEMENT,
            copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new DuplicateTransactionCommand(INDEX_SECOND_ELEMENT, DESC_LUNCH)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new DuplicateTransactionCommand(INDEX_FIRST_ELEMENT, DESC_DINNER)));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        TransactionDescriptor duplicateTransactionDescriptor = new TransactionDescriptor();
        DuplicateTransactionCommand duplicateCommand =
            new DuplicateTransactionCommand(targetIndex, duplicateTransactionDescriptor);
        String expected = DuplicateTransactionCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + ", "
            + "transactionDescriptor=" + duplicateTransactionDescriptor + "}";
        assertEquals(expected, duplicateCommand.toString());
    }
}
