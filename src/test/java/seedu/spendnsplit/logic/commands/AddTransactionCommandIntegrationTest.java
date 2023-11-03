package seedu.spendnsplit.logic.commands;

import static seedu.spendnsplit.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.spendnsplit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.spendnsplit.testutil.TypicalSpendNSplitBook.getTypicalSpendNSplitBook;
import static seedu.spendnsplit.testutil.TypicalPortions.ALICE_PORTION;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.spendnsplit.logic.Messages;
import seedu.spendnsplit.model.Model;
import seedu.spendnsplit.model.ModelManager;
import seedu.spendnsplit.model.UserPrefs;
import seedu.spendnsplit.model.person.Name;
import seedu.spendnsplit.model.transaction.Transaction;
import seedu.spendnsplit.testutil.TransactionBuilder;
import seedu.spendnsplit.testutil.TypicalPersons;
import seedu.spendnsplit.testutil.TypicalPortions;

public class AddTransactionCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalSpendNSplitBook(), new UserPrefs());
    }

    @Test
    public void execute_newTransaction_success() {
        Transaction validTransaction = new TransactionBuilder().withPayeeName(Name.SELF.fullName)
            .withPortions(Set.of(ALICE_PORTION)).build();

        Model expectedModel = new ModelManager(model.getSpendNSplitBook(), new UserPrefs());
        expectedModel.addTransaction(validTransaction);

        assertCommandSuccess(new AddTransactionCommand(validTransaction), model,
                String.format(AddTransactionCommand.MESSAGE_SUCCESS, Messages.format(validTransaction)),
                expectedModel);
    }

    @Test
    public void execute_irrelevantTransaction_throwsCommandException() throws Exception {
        Transaction irrelevantTransaction = new TransactionBuilder()
            .withPayeeName(TypicalPersons.ALICE.getName().fullName)
            .withPortions(Set.of(TypicalPortions.ALICE_PORTION))
            .build();
        assertCommandFailure(new AddTransactionCommand(irrelevantTransaction), model,
            AddTransactionCommand.MESSAGE_IRRELEVANT_TRANSACTION);
    }

    @Test
    public void execute_unknownTransaction_throwsCommandException() throws Exception {
        Transaction unknownTransaction = new TransactionBuilder()
            .withPayeeName("Unknown")
            .withPortions(Set.of(TypicalPortions.SELF_PORTION))
            .build();
        assertCommandFailure(new AddTransactionCommand(unknownTransaction), model,
            AddTransactionCommand.MESSAGE_UNKNOWN_PARTY);
    }

    @Test
    public void execute_duplicateTransaction_throwsCommandException() {
        Transaction transactionInList = model.getSpendNSplitBook().getTransactionList().get(0);
        assertCommandFailure(new AddTransactionCommand(transactionInList), model,
            AddTransactionCommand.MESSAGE_DUPLICATE_TRANSACTION);
    }

}
