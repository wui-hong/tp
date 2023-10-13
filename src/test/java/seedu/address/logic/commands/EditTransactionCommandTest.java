package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.EditTransactionCommand.EditTransactionDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.transaction.Transaction;
import seedu.address.testutil.TransactionBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditTransactionCommand.
 */
class EditTransactionCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Transaction editedTransaction = new TransactionBuilder().build();
        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder(editedTransaction).build();
    }
}