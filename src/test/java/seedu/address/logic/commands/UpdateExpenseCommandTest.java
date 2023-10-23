package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ELEMENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UpdateExpenseCommand.UpdateExpenseDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.expense.Expense;
import seedu.address.testutil.TransactionBuilder;
import seedu.address.testutil.UpdateExpenseDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for UpdateExpenseCommand.
 */
class UpdateExpenseCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Transaction originalTransaction = model.getFilteredTransactionList().get(0);
        Expense originalExpense = originalTransaction.getExpenses().stream().iterator().next();
        UpdateExpenseDescriptor descriptor = new UpdateExpenseDescriptorBuilder(originalExpense)
                .withWeight("100").build();
        UpdateExpenseCommand updateExpenseCommand = new UpdateExpenseCommand(INDEX_FIRST_ELEMENT, descriptor);

        String expectedMessage = String.format(UpdateExpenseCommand.MESSAGE_UPDATE_EXPENSE_SUCCESS,
                originalExpense);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        // set the Transaction to a copy, with updated expenses
        expectedModel.setTransaction(model.);
    }
}