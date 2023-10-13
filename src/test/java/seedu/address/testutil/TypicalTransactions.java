package seedu.address.testutil;

import java.util.Set;

import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.expense.Expense;
import seedu.address.model.transaction.expense.Weight;

/**
 * A utility class containing a list of {@code Transaction} objects to be used in tests.
 */
public class TypicalTransactions {
    public static final Transaction LUNCH = new TransactionBuilder().withAmount("50").withDescription("Lunch")
            .withPayee(TypicalPersons.BOB).withExpenses(Set.of(new Expense(
                    TypicalPersons.BOB.getName(), new Weight("0.2")))).build();
    public static final Transaction DINNER = new TransactionBuilder().withAmount("100").withDescription("Dinner")
            .withPayee(TypicalPersons.ALICE).withExpenses(Set.of(new Expense(
                    TypicalPersons.ALICE.getName(), new Weight("0.5")))).build();

}
