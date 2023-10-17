package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertTransactionCommandSuccess;
import static seedu.address.testutil.TransactionBuilder.DEFAULT_TIMESTAMP;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ELEMENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditTransactionCommand.EditTransactionDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.transaction.Transaction;
import seedu.address.testutil.EditTransactionDescriptorBuilder;
import seedu.address.testutil.TransactionBuilder;



/**
 * Contains integration tests (interaction with the Model) and unit tests for EditTransactionCommand.
 */
class EditTransactionCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Transaction editedTransaction = new TransactionBuilder().withTimestamp(DEFAULT_TIMESTAMP).build();
        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder(editedTransaction).build();
        EditTransactionCommand editTransactionCommand = new EditTransactionCommand(INDEX_FIRST_ELEMENT, descriptor);

        String expectedMessage = String.format(
                EditTransactionCommand.MESSAGE_EDIT_TRANSACTION_SUCCESS, Messages.format(editedTransaction));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        // use 4th transaction in the list to test for v1.2
        expectedModel.setTransaction(model.getFilteredTransactionList().get(0), editedTransaction);

        assertTransactionCommandSuccess(editTransactionCommand, model, expectedMessage, expectedModel);
    }
}
