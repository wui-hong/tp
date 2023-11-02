package seedu.spendnsplit.testutil;

import seedu.spendnsplit.model.SpendNSplit;
import seedu.spendnsplit.model.person.Person;
import seedu.spendnsplit.model.transaction.Transaction;


/**
 * A utility class to help with building SpendNSplitbook objects.
 * Example usage: <br>
 *     {@code SpendNSplitBook ab = new SpendNSplitBookBuilder().withPerson("John", "Doe").build();}
 */
public class SpendNSplitBookBuilder {

    private SpendNSplit spendNSplit;

    public SpendNSplitBookBuilder() {
        spendNSplit = new SpendNSplit();
    }

    public SpendNSplitBookBuilder(SpendNSplit spendNSplit) {
        this.spendNSplit = spendNSplit;
    }

    /**
     * Adds a new {@code Person} to the {@code SpendNSplitBook} that we are building.
     */
    public SpendNSplitBookBuilder withPerson(Person person) {
        spendNSplit.addPerson(person);
        return this;
    }

    /**
     * Adds a new {@code Transaction} to the {@code SpendNSplitBook} that we are building.
     */
    public SpendNSplitBookBuilder withTransaction(Transaction transaction) {
        spendNSplit.addTransaction(transaction);
        return this;
    }

    public SpendNSplit build() {
        return spendNSplit;
    }
}
