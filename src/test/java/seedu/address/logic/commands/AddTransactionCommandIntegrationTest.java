package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalExpenses.ALICE_EXPENSE;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.transaction.Transaction;
import seedu.address.testutil.TransactionBuilder;

public class AddTransactionCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newTransaction_success() {
        Transaction validTransaction = new TransactionBuilder().withPayeeName(Name.SELF.fullName)
                .withExpenses(Set.of(ALICE_EXPENSE)).build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addTransaction(validTransaction);

        assertCommandSuccess(new AddTransactionCommand(validTransaction), model,
                String.format(AddTransactionCommand.MESSAGE_SUCCESS, Messages.format(validTransaction)),
                expectedModel);
    }

    @Test
    public void execute_duplicateTransaction_throwsCommandException() {
        Transaction transactionInList = model.getAddressBook().getTransactionList().get(0);
        assertCommandFailure(new AddTransactionCommand(transactionInList), model,
                AddTransactionCommand.MESSAGE_DUPLICATE_TRANSACTION);
    }

}
