package seedu.address.testutil;

import static seedu.address.testutil.TypicalExpenses.ALICE_EXPENSE;
import static seedu.address.testutil.TypicalExpenses.BOB_EXPENSE;

import java.util.Set;

import seedu.address.model.transaction.Transaction;

/**
 * A utility class containing a list of {@code Transaction} objects to be used in tests.
 */
public class TypicalTransactions {
    public static final Transaction LUNCH = new TransactionBuilder().withAmount("50").withDescription("Lunch")
            .withPayee(TypicalPersons.BOB).withExpenses(Set.of(BOB_EXPENSE)).build();
    public static final Transaction DINNER = new TransactionBuilder().withAmount("100").withDescription("Dinner")
            .withPayee(TypicalPersons.ALICE).withExpenses(Set.of(ALICE_EXPENSE)).build();

}
