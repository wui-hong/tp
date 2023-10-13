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
            .withPayeeName(TypicalPersons.BOB.getName().fullName).withExpenses(Set.of(BOB_EXPENSE))
            .withTimestamp("2023-10-13T12:34:56.789").build();
    public static final Transaction DINNER = new TransactionBuilder().withAmount("100").withDescription("Dinner")
            .withPayeeName(TypicalPersons.ALICE.getName().fullName).withExpenses(Set.of(ALICE_EXPENSE))
            .withTimestamp("2023-10-13T12:34:56.789").build();

}
