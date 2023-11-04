package seedu.spendnsplit.testutil;

import seedu.spendnsplit.model.SpendNSplit;
import seedu.spendnsplit.model.person.Person;
import seedu.spendnsplit.model.transaction.Transaction;

/**
 * A utility class to get a typical spendNSplit book containing persons and transactions.
 */
public class TypicalSpendNSplitBook {
    /**
     * Returns an {@code SpendNSplitBook} with all the typical persons and transactions.
     */
    public static SpendNSplit getTypicalSpendNSplitBook() {
        SpendNSplit ab = new SpendNSplit();
        for (Person person : TypicalPersons.getTypicalPersons()) {
            ab.addPerson(person);
        }
        for (Transaction transaction : TypicalTransactions.getTypicalTransactions()) {
            ab.addTransaction(transaction);
        }
        return ab;
    }
}
