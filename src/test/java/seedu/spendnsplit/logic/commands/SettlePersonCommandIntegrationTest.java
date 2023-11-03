package seedu.spendnsplit.logic.commands;

import static seedu.spendnsplit.logic.commands.CommandTestUtil.VALID_TIMESTAMP;
import static seedu.spendnsplit.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.spendnsplit.logic.commands.CommandTestUtil.assertTransactionCommandSuccess;
import static seedu.spendnsplit.testutil.TypicalSpendNSplitBook.getTypicalSpendNSplitBook;
import static seedu.spendnsplit.testutil.TypicalIndexes.INDEX_FIFTH_ELEMENT;
import static seedu.spendnsplit.testutil.TypicalIndexes.INDEX_FIRST_ELEMENT;
import static seedu.spendnsplit.testutil.TypicalIndexes.INDEX_SEVENTH_ELEMENT;
import static seedu.spendnsplit.testutil.TypicalPersons.ALICE;
import static seedu.spendnsplit.testutil.TypicalPersons.BENSON;
import static seedu.spendnsplit.testutil.TypicalPersons.FIONA;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.spendnsplit.commons.core.index.Index;
import seedu.spendnsplit.logic.Messages;
import seedu.spendnsplit.model.Model;
import seedu.spendnsplit.model.ModelManager;
import seedu.spendnsplit.model.UserPrefs;
import seedu.spendnsplit.model.person.Name;
import seedu.spendnsplit.model.person.Person;
import seedu.spendnsplit.model.transaction.Timestamp;
import seedu.spendnsplit.model.transaction.Transaction;
import seedu.spendnsplit.testutil.PersonBuilder;
import seedu.spendnsplit.testutil.PortionBuilder;
import seedu.spendnsplit.testutil.TransactionBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code SettlePersonCommand}.
 */
public class SettlePersonCommandIntegrationTest {

    private static final Timestamp TIME = new Timestamp(new Timestamp(VALID_TIMESTAMP).value.plusDays(1));

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalSpendNSplitBook(), new UserPrefs());
    }
    @Test
    public void execute_settlePositiveBalance_success() {
        Person personToSettle = new PersonBuilder(ALICE).build();
        Name personToSettleName = personToSettle.getName();

        Model expectedModel = new ModelManager(model.getSpendNSplitBook(), new UserPrefs());
        Transaction transaction = new TransactionBuilder().withPayeeName(personToSettleName.fullName).withDescription(
                String.format(SettlePersonCommand.SETTLE_TRANSACTION_DESCRIPTION, personToSettleName.fullName))
                .withAmount(expectedModel.getBalance(personToSettleName).toString())
                .withPortions(Set.of(new PortionBuilder().withName(Name.SELF.fullName)
                .withWeight("1").build())).withTimestamp(TIME.toString()).build();
        expectedModel.addTransaction(transaction);

        assertTransactionCommandSuccess(new SettlePersonCommand(INDEX_FIRST_ELEMENT, TIME), model,
                String.format(SettlePersonCommand.MESSAGE_SETTLE_PERSON_SUCCESS, personToSettle.getName()),
                expectedModel);
    }

    @Test
    public void execute_settleNegativeBalance_success() {
        Person personToSettle = new PersonBuilder(BENSON).build();
        Name personToSettleName = personToSettle.getName();

        Model expectedModel = new ModelManager(model.getSpendNSplitBook(), new UserPrefs());
        Transaction transaction = new TransactionBuilder().withPayeeName(Name.SELF.fullName).withDescription(
                String.format(SettlePersonCommand.SETTLE_TRANSACTION_DESCRIPTION, personToSettleName.fullName))
                .withAmount(expectedModel.getBalance(personToSettleName).abs().toString())
                .withPortions(Set.of(new PortionBuilder().withName(personToSettleName.fullName)
                .withWeight("1").build())).withTimestamp(TIME.toString()).build();
        expectedModel.addTransaction(transaction);

        assertTransactionCommandSuccess(new SettlePersonCommand(INDEX_SEVENTH_ELEMENT, TIME), model,
                String.format(SettlePersonCommand.MESSAGE_SETTLE_PERSON_SUCCESS, personToSettle.getName()),
                expectedModel);
    }

    @Test
    public void execute_noOutstandingBalance_throwsCommandException() {
        Person personToSettle = new PersonBuilder(FIONA).build();
        Name personToSettleName = personToSettle.getName();

        assertCommandFailure(new SettlePersonCommand(INDEX_FIFTH_ELEMENT, TIME),
                model, String.format(SettlePersonCommand.MESSAGE_NO_OUTSTANDING_BALANCE, personToSettleName, TIME));
    }
    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        SettlePersonCommand settlePersonCommand = new SettlePersonCommand(outOfBoundIndex, TIME);

        assertCommandFailure(settlePersonCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
}
