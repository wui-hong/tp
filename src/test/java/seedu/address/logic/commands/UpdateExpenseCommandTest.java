package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEIGHT_HALF;
import static seedu.address.logic.commands.CommandTestUtil.assertTransactionCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ELEMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ELEMENT;

import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.UpdateExpenseCommand.UpdateExpenseDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.expense.Expense;
import seedu.address.testutil.ExpenseBuilder;
import seedu.address.testutil.TransactionBuilder;
import seedu.address.testutil.UpdateExpenseDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for UpdateExpenseCommand.
 */
class UpdateExpenseCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_editExistingExpenseWeightNonZeroUnfilteredList_success() {
        Transaction originalTransaction = model.getFilteredTransactionList().get(0);
        Set<Expense> originalExpenses = originalTransaction.getExpenses();
        Expense originalExpense = originalTransaction.getExpenses().stream().iterator().next();

        Expense editedExpense = new ExpenseBuilder(originalExpense).withWeight("100").build();
        Set<Expense> editedExpenses = originalExpenses.stream().map(expense ->
                expense.equals(originalExpense) ? editedExpense : expense).collect(Collectors.toSet());
        Transaction editedTransaction = new TransactionBuilder(originalTransaction)
                .withExpenses(editedExpenses).build();

        UpdateExpenseDescriptor descriptor = new UpdateExpenseDescriptorBuilder(originalExpense)
                .withWeight("100").build();
        UpdateExpenseCommand updateExpenseCommand = new UpdateExpenseCommand(INDEX_FIRST_ELEMENT, descriptor);

        String expectedMessage = String.format(UpdateExpenseCommand.MESSAGE_UPDATE_EXPENSE_SUCCESS,
                Messages.format(editedTransaction));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setTransaction(model.getFilteredTransactionList().get(0), editedTransaction);

        assertTransactionCommandSuccess(updateExpenseCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteExistingExpenseUnfilteredList_success() {
        Transaction originalTransaction = model.getFilteredTransactionList().get(1);
        Set<Expense> originalExpenses = originalTransaction.getExpenses();
        Expense originalExpense = originalTransaction.getExpenses().stream().iterator().next();

        Set<Expense> editedExpenses = originalExpenses.stream().filter(expense ->
                !expense.equals(originalExpense)).collect(Collectors.toSet());
        Transaction editedTransaction = new TransactionBuilder(originalTransaction)
                .withExpenses(editedExpenses).build();

        UpdateExpenseDescriptor descriptor = new UpdateExpenseDescriptorBuilder(originalExpense)
                .withWeight("0").build();
        UpdateExpenseCommand updateExpenseCommand = new UpdateExpenseCommand(INDEX_SECOND_ELEMENT, descriptor);

        String expectedMessage = String.format(UpdateExpenseCommand.MESSAGE_UPDATE_EXPENSE_SUCCESS,
                Messages.format(editedTransaction));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setTransaction(model.getFilteredTransactionList().get(1), editedTransaction);

        assertTransactionCommandSuccess(updateExpenseCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Delete the only expense in the transaction.
     */
    @Test
    public void execute_deleteOnlyExistingExpenseUnfilteredList_failure() {
        Transaction originalTransaction = model.getFilteredTransactionList().get(0);
        Expense originalExpense = originalTransaction.getExpenses().stream().iterator().next();
        UpdateExpenseDescriptor descriptor = new UpdateExpenseDescriptorBuilder(originalExpense)
                .withWeight("0").build();
        UpdateExpenseCommand updateExpenseCommand = new UpdateExpenseCommand(INDEX_FIRST_ELEMENT, descriptor);

        CommandTestUtil.assertCommandFailure(updateExpenseCommand, model,
                UpdateExpenseCommand.MESSAGE_DELETE_ONLY_EXPENSE_FAILURE);
    }

    @Test
    public void execute_addNewExpenseUnfilteredList_success() {
        Transaction originalTransaction = model.getFilteredTransactionList().get(0);

        Expense newExpense = new ExpenseBuilder().withName(VALID_NAME_AMY).withWeight("1").build();
        Set<Expense> editedExpenses = originalTransaction.getExpensesCopy();
        editedExpenses.add(newExpense);
        Transaction editedTransaction = new TransactionBuilder(originalTransaction)
                .withExpenses(editedExpenses).build();

        UpdateExpenseDescriptor descriptor = new UpdateExpenseDescriptorBuilder(newExpense).build();
        UpdateExpenseCommand updateExpenseCommand = new UpdateExpenseCommand(INDEX_FIRST_ELEMENT, descriptor);

        String expectedMessage = String.format(UpdateExpenseCommand.MESSAGE_UPDATE_EXPENSE_SUCCESS,
                Messages.format(editedTransaction));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setTransaction(model.getFilteredTransactionList().get(0), editedTransaction);

        assertTransactionCommandSuccess(updateExpenseCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_failure() {
        // TODO
    }

    @Test
    public void execute_oneFieldSpecifiedUnfilteredList_failure() {
        // TODO
    }

    @Test
    public void execute_filteredList_success() {
        // TODO
    }

    @Test
    public void execute_invalidTransactionIndexUnfilteredList_failure() {
        // TODO
    }

    @Test
    public void execute_invalidTransactionIndexFilteredList_failure() {
        // TODO
    }

    @Test
    public void equals() {
        final UpdateExpenseDescriptor standardDescriptor =  new UpdateExpenseDescriptorBuilder()
                .withPersonName(VALID_NAME_AMY).withWeight(VALID_WEIGHT_HALF).build();
        final UpdateExpenseCommand standardCommand = new UpdateExpenseCommand(INDEX_FIRST_ELEMENT,standardDescriptor);

        // same values -> returns true
        UpdateExpenseDescriptor copyDescriptor = new UpdateExpenseDescriptorBuilder()
                .withPersonName(VALID_NAME_AMY).withWeight(VALID_WEIGHT_HALF).build();
        UpdateExpenseCommand commandWithSameValues = new UpdateExpenseCommand(INDEX_FIRST_ELEMENT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertTrue(!standardCommand.equals(null));

        // different types -> returns false
        assertTrue(!standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertTrue(!standardCommand.equals(new UpdateExpenseCommand(INDEX_SECOND_ELEMENT, copyDescriptor)));

        // different descriptor -> returns false
        UpdateExpenseDescriptor differentDescriptor = new UpdateExpenseDescriptorBuilder()
                .withPersonName(VALID_NAME_BOB).withWeight("100").build();
        assertFalse(standardCommand.equals(new UpdateExpenseCommand(INDEX_FIRST_ELEMENT, differentDescriptor)));
    }

    @Test
    public void toStringMethod() {
        Index index = INDEX_FIRST_ELEMENT;
        UpdateExpenseDescriptor updateExpenseDescriptor = new UpdateExpenseDescriptorBuilder()
                .withPersonName(VALID_NAME_AMY).withWeight(VALID_WEIGHT_HALF).build();
        UpdateExpenseCommand updateExpenseCommand = new UpdateExpenseCommand(index, updateExpenseDescriptor);
        String expectedString = UpdateExpenseCommand.class.getCanonicalName()
                + "{index=" + index
                + ", updateExpenseDescriptor=" + updateExpenseDescriptor + "}";
        assertEquals(expectedString, updateExpenseCommand.toString());
    }

}